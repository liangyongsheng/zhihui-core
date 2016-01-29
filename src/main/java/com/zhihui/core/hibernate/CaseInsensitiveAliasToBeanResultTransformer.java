package com.zhihui.core.hibernate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;

import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.util.ReflectHelper;

@SuppressWarnings("serial")
public class CaseInsensitiveAliasToBeanResultTransformer extends AliasToBeanResultTransformer {

	@SuppressWarnings("rawtypes")
	private final Class resultClass;
	private boolean isInitialized;
	private String[] aliases;
	private Setter[] setters;

	@SuppressWarnings("rawtypes")
	// copy and modify from AliasToBeanResultTransformer
	public CaseInsensitiveAliasToBeanResultTransformer(Class resultClass) {
		super(resultClass);
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");

		isInitialized = false;
		this.resultClass = resultClass;
	}

	@Override
	// copy from AliasToBeanResultTransformer
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			if (!isInitialized)
				initialize(aliases);
			else
				check(aliases);

			result = this.resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				if (this.setters[i] != null)
					this.setters[i].set(result, tuple[i], null);
			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
		}

		return result;
	}

	// copy and modify from AliasToBeanResultTransformer
	private void initialize(String[] aliases) {
		this.aliases = new String[aliases.length];
		this.setters = new Setter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				this.aliases[i] = alias;
				this.setters[i] = this.getSetter(this.resultClass, alias);
			}
		}
		this.isInitialized = true;
	}

	// copy from AliasToBeanResultTransformer
	private void check(String[] aliases) {
		if (!Arrays.equals(aliases, this.aliases))
			throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
	}

	// add by SeanLeung
	private Setter getSetter(Class<?> theClass, String propertyName) {
		Setter setter = null;
		if (theClass != null && theClass != Object.class) {

			// iterate all classes
			Class<?> loopClass = theClass;
			while (loopClass != null && loopClass != Object.class) {
				setter = doGetSetter(loopClass, propertyName);
				if (setter != null) {
					if (!ReflectHelper.isPublic(loopClass, setter.getMethod()))
						setter.getMethod().setAccessible(true);
					break;
				}
				loopClass = loopClass.getSuperclass();
			}

			// iterate all interfaces
			loopClass = theClass;
			if (setter == null) {
				while (loopClass != null && loopClass != Object.class) {
					Class<?>[] interfaces = loopClass.getInterfaces();
					for (int i = 0; i < interfaces.length; i++) {
						setter = doGetSetter(interfaces[i], propertyName);
						if (setter != null)
							break;
					}
					if (setter != null)
						break;
					loopClass = loopClass.getSuperclass();
				}
			}

			// ignore SQL fields;
			// if (setter == null)
			// throw new PropertyNotFoundException("Could not find a setter for property " + propertyName + " in class " + theClass.getName());
		}
		return setter;
	}

	// add by SeanLeung
	private Setter doGetSetter(Class<?> theClass, String propertyName) throws PropertyNotFoundException {
		Setter rs = null;
		String fieldName = null;
		String methodName = null;
		Access access = theClass.getAnnotation(Access.class);
		Field[] fields = theClass.getDeclaredFields();
		Method[] methods = theClass.getDeclaredMethods();
		PropertyAccessor propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(theClass, null), PropertyAccessorFactory.getPropertyAccessor("field") });

		if (access != null && access.value() == AccessType.FIELD) {
			for (Field e : fields) {
				Column column = e.getAnnotation(Column.class);
				if (column != null && column.name() != null && column.name().equalsIgnoreCase(propertyName)) {
					fieldName = e.getName();
					break;
				}
				// if column.name mismatch, automatically use fieldName
				if (e.getName().equalsIgnoreCase(propertyName)) {
					fieldName = e.getName();
					break;
				}
			}
		}

		fieldName = fieldName != null ? fieldName : propertyName;
		for (Method e : methods) {
			if (e.getParameterTypes().length != 1)
				continue;
			methodName = e.getName().startsWith("set") ? e.getName().substring(3) : e.getName();
			if (methodName.equalsIgnoreCase(fieldName)) {
				rs = propertyAccessor.getSetter(theClass, methodName);
				break;
			}
		}

		return rs;
	}
}
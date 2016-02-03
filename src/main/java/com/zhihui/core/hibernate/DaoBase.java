package com.zhihui.core.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class DaoBase {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public abstract <T> T getById(long id);

	protected Connection getConnection() throws SQLException {
		Connection conn = null;
		SessionFactory sessionFactory = this.hibernateTemplate.getSessionFactory();
		ConnectionProvider cp = ((SessionFactoryImplementor) sessionFactory).getConnectionProvider();
		conn = cp.getConnection();
		return conn;
	}

	/**
	 * update the object
	 * 
	 * @param objModel
	 */
	public <T> void update(T objModel) {
		if (MyTransaction.getCurrentSession() != null)
			MyTransaction.getCurrentSession().update(objModel);
		else
			this.hibernateTemplate.update(objModel);
	}

	public <T> void add(T objModel) {
		if (MyTransaction.getCurrentSession() != null)
			MyTransaction.getCurrentSession().save(objModel);
		else
			this.hibernateTemplate.save(objModel);
	}

	/**
	 * get list by SQL using HiberNate framework, sometime it has error when you query mssql
	 * 
	 * @param sql
	 * @param transClass
	 * @return
	 */
	// do you know how to get a class from T, so I do not need to send Class<T> parameter;
	@SuppressWarnings("unchecked")
	public <T> List<T> executeFind(final String sql, final Class<T> transClass) {
		List<T> rs = this.hibernateTemplate.executeFind(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);

				if (transClass != null)
					// query.setResultTransformer(Transformers.aliasToBean(transClass));
					query.setResultTransformer(new CaseInsensitiveAliasToBeanResultTransformer(transClass));
				else
					query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<T> list = query.list();
				return list;
			}
		});
		return rs;
	}

	/**
	 * execute one result SQL like : Select count(*)
	 * 
	 * @param sql
	 * @return
	 */
	public Object executeAsObject(final String sql) {
		Object object = this.hibernateTemplate.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				List<?> list = query.list();
				Object object = list.size() == 1 ? list.get(0) : list;
				return object;
			}
		});

		return object;
	}

	/**
	 * update
	 * 
	 * @param sql
	 * @return
	 */
	public Object executeUpdate(final String sql) {
		Object object = this.hibernateTemplate.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				int rs = query.executeUpdate();
				return rs;
			}
		});
		return object;
	}

	/**
	 * execute the SQL without using HiberNate framework: Key => value
	 * 
	 * @param sql
	 * @return
	 */
	public List<Object> findWithoutHibernateFramework(final String sql) throws SQLException {
		List<Object> result = new ArrayList<Object>();
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			int count = rsmd.getColumnCount();
			for (int i = 0; i < count; i++) {
				String columnName = rsmd.getColumnLabel(i + 1);
				map.put(columnName, rs.getObject(i + 1));
			}
			result.add(map);
		}
		if (conn != null && conn.isClosed() == false)
			conn.close();
		return result;
	}
}

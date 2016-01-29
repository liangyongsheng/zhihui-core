package com.zhihui.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

import com.zhihui.core.exception.BusinessException;
import com.zhihui.core.hibernate.MyTransaction;

@Aspect
public class TransactionAspect {
	public Object Around(ProceedingJoinPoint proceedingJoinPoint) throws BusinessException {
		Object value = null;
		try {
			MyTransaction.beginTransaction();
			value = proceedingJoinPoint.proceed();
			MyTransaction.endTransaction();
		} catch (Throwable e) {
			MyTransaction.rollbackTransaction();
			throw new BusinessException(e);
		}
		return value;
	}
}

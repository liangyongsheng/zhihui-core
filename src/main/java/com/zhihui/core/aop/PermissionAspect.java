package com.zhihui.core.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zhihui.core.api.ApiBo;
import com.zhihui.core.context.MyContext;
import com.zhihui.core.exception.BusinessException;
import com.zhihui.oprtforcore.dao.OprtSecret4CoreDao;
import com.zhihui.oprtforcore.dao.Permission4CoreDao;
import com.zhihui.oprtforcore.model.OprtSecret4CoreModel;
import com.zhihui.oprtforcore.model.Permission4CoreModel;

@Aspect
public class PermissionAspect {

	public void before(JoinPoint joinPoint) throws BusinessException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long nowLong = sdf.parse(sdf.format(new Date())).getTime();

			Object target = joinPoint.getTarget();
			if (target instanceof ApiBo<?>) {
				ApiBo<?> bo = (ApiBo<?>) target;
				int oprtId = bo.getApiRequest().getOprtId();
				String secret = bo.getApiRequest().getOprtSecret();
				String method = bo.getApiRequest().getMethod();
				// 密钥
				OprtSecret4CoreModel validOne = null;
				HibernateTemplate hibernateTemplate = (HibernateTemplate) MyContext.getClassPathXmlApplicationContext("oprt4core.mysql.connection.cfg.xml").getBean("hibernateTemplate");
				OprtSecret4CoreDao oprtSecretDao = new OprtSecret4CoreDao();
				oprtSecretDao.setHibernateTemplate(hibernateTemplate);
				List<OprtSecret4CoreModel> oprtSecretModels = oprtSecretDao.getByOprtId(oprtId, secret);
				if (oprtSecretModels.size() <= 0)
					throw new BusinessException("不存在此操作员。");
				for (OprtSecret4CoreModel e : oprtSecretModels) {
					if (e.getExpiredDate() == null) {
						validOne = e;
						break;
					}
					if (e.getExpiredDate().getTime() < nowLong)
						continue;
					validOne = e;
				}
				if (validOne == null)
					throw new BusinessException("密钥不存在或过期。");
				if (!validOne.getSecret().equals(secret))
					throw new BusinessException("密钥分大小写，此密钥大小写不正确。");

				// 权限
				Permission4CoreDao permissionDao = new Permission4CoreDao();
				permissionDao.setHibernateTemplate(hibernateTemplate);
				List<Permission4CoreModel> permissions = permissionDao.getByOprtId(oprtId, method);
				if (permissions.size() < 0)
					throw new BusinessException("此操作员没有权限：" + method + "。");
			}
		} catch (Throwable e) {
			throw new BusinessException(e);
		}
	}
}

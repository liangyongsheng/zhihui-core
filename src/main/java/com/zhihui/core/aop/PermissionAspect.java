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
import com.zhihui.oprtforcore.dao.Oprt4SecretDao;
import com.zhihui.oprtforcore.model.Oprt4SecretModel;

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
				// 权限验证
				Oprt4SecretModel validOne = null;
				HibernateTemplate hibernateTemplate = (HibernateTemplate) MyContext.getClassPathXmlApplicationContext("oprt4core.mysql.connection.cfg.xml").getBean("hibernateTemplate");
				Oprt4SecretDao oprtSecretDao = new Oprt4SecretDao();
				oprtSecretDao.setHibernateTemplate(hibernateTemplate);
				List<Oprt4SecretModel> oprt4SecretModels = oprtSecretDao.getByOprtId(oprtId, secret);
				if (oprt4SecretModels.size() <= 0)
					throw new BusinessException("不存在此操作员。");
				for (Oprt4SecretModel e : oprt4SecretModels) {
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
			}
		} catch (Throwable e) {
			throw new BusinessException(e);
		}
	}
}

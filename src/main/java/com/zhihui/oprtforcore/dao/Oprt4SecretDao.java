package com.zhihui.oprtforcore.dao;

import java.util.ArrayList;
import java.util.List;

import com.zhihui.core.hibernate.DaoBase;
import com.zhihui.oprtforcore.model.Oprt4SecretModel;

public class Oprt4SecretDao extends DaoBase {
	private String selectText = "select id,oprtId,secret,expiredDate,flag,createTime,remark from oprt_secret ";

	@SuppressWarnings("unchecked")
	@Override
	public Oprt4SecretModel getById(long id) {
		String sql = this.selectText + " where id = " + id + "";
		List<Oprt4SecretModel> tmp = this.executeFind(sql, Oprt4SecretModel.class);
		if (tmp == null || tmp.size() <= 0)
			return null;
		else
			return tmp.get(0);
	}

	public List<Oprt4SecretModel> getByOprtId(int oprtId, String secret) {
		String sql = this.selectText + " where flag = 1 and oprtId = " + oprtId + " and secret = '" + secret + "'";
		List<Oprt4SecretModel> tmp = this.executeFind(sql, Oprt4SecretModel.class);
		return tmp == null ? new ArrayList<Oprt4SecretModel>() : tmp;
	}

}

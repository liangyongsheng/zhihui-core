package com.zhihui.oprtforcore.dao;

import java.util.ArrayList;
import java.util.List;

import com.zhihui.core.hibernate.DaoBase;
import com.zhihui.oprtforcore.model.OprtSecret4CoreModel;

public class OprtSecret4CoreDao extends DaoBase {
	private String selectText = "select id,oprtId,secret,expiredDate,flag,createTime,remark from oprt_secret ";

	@SuppressWarnings("unchecked")
	@Override
	public OprtSecret4CoreModel getById(long id) {
		String sql = this.selectText + " where id = " + id + "";
		List<OprtSecret4CoreModel> tmp = this.executeFind(sql, OprtSecret4CoreModel.class);
		if (tmp == null || tmp.size() <= 0)
			return null;
		else
			return tmp.get(0);
	}

	public List<OprtSecret4CoreModel> getByOprtId(int oprtId, String secret) {
		String sql = this.selectText + " where flag = 1 and oprtId = " + oprtId + " and secret = '" + secret + "'";
		List<OprtSecret4CoreModel> tmp = this.executeFind(sql, OprtSecret4CoreModel.class);
		return tmp == null ? new ArrayList<OprtSecret4CoreModel>() : tmp;
	}

}

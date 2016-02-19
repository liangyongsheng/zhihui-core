package com.zhihui.oprtforcore.dao;

import java.util.ArrayList;
import java.util.List;

import com.zhihui.core.hibernate.DaoBase;
import com.zhihui.oprtforcore.model.Permission4CoreModel;

public class Permission4CoreDao extends DaoBase {
	private String selectText = "select permissionId,code,name,codeGroup,createTime,remark from permission ";

	@SuppressWarnings("unchecked")
	@Override
	public Permission4CoreModel getById(long id) {
		String sql = this.selectText + " where permissionId = " + id + "";
		List<Permission4CoreModel> tmp = this.executeFind(sql, Permission4CoreModel.class);
		if (tmp == null || tmp.size() <= 0)
			return null;
		else
			return tmp.get(0);
	}

	public List<Permission4CoreModel> getByOprtId(int oprtId, String method) {
		String sql = this.selectText //
				+ "where (permissionId in (select permissionId from oprt_permission_group_ownership where oprtId = " + oprtId + ") "//
				+ "or permissionId in (select permissionId from oprt_permission_ownership where oprtId = " + oprtId + ")) "//
				+ "and code = '" + method + "'";
		List<Permission4CoreModel> tmp = this.executeFind(sql, Permission4CoreModel.class);
		return tmp == null ? new ArrayList<Permission4CoreModel>() : tmp;
	}
}

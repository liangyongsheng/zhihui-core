package com.zhihui.core.api;

import com.zhihui.core.exception.CoreException;

public class ApiException extends CoreException {
	private static final long serialVersionUID = 3350730684201679550L;
	private String code = "CoreException";
	private String subCode = "ApiException";

	public ApiException() {
		super();
	}

	public ApiException(String arg0) {
		super(arg0);
	}

	public ApiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApiException(Throwable arg0) {
		super(arg0);
	}

	protected ApiException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
}

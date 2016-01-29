package com.zhihui.core.exception;

public class BusinessException extends CoreException {
	private static final long serialVersionUID = 1884831021481767463L;
	private String code = "CoreException";
	private String subCode = "BusinessException";

	public BusinessException() {
		super();
	}

	public BusinessException(String arg0) {
		super(arg0);
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}

	protected BusinessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getSubCode() {
		return subCode;
	}

	@Override
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
}

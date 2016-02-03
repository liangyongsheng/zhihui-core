package com.zhihui.core.exception;

public class ServiceException extends CoreException {
	private static final long serialVersionUID = -8817010236903262160L;
	private String code = "CoreException";
	private String subCode = "ServiceException";

	public ServiceException() {
		super();
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}

	protected ServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

package com.zhihui.core.exception;

public class InitException extends CoreException {
	private static final long serialVersionUID = -1818547209232242187L;
	private String code = "coreException";
	private String subCode = "initException";

	public InitException() {
		super();
	}

	public InitException(String arg0) {
		super(arg0);
	}

	public InitException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InitException(Throwable arg0) {
		super(arg0);
	}

	protected InitException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

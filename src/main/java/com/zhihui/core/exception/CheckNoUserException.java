package com.zhihui.core.exception;

public class CheckNoUserException extends CheckException {
	private static final long serialVersionUID = -995248671429553146L;
	private String code = "checkException";
	private String subCode = "checkNoUserException";

	public CheckNoUserException() {
		super();
	}

	public CheckNoUserException(String arg0) {
		super(arg0);
	}

	public CheckNoUserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CheckNoUserException(Throwable arg0) {
		super(arg0);
	}

	protected CheckNoUserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

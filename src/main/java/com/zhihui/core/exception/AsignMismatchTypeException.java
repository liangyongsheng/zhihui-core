package com.zhihui.core.exception;

public class AsignMismatchTypeException extends AsignException {
	private static final long serialVersionUID = -6290107522447259694L;
	private String code = "asignException";
	private String subCode = "asignMismatchTypeException";

	public AsignMismatchTypeException() {
		super();
	}

	public AsignMismatchTypeException(String arg0) {
		super(arg0);
	}

	public AsignMismatchTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AsignMismatchTypeException(Throwable arg0) {
		super(arg0);
	}

	protected AsignMismatchTypeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

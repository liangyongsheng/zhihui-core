package com.zhihui.core.exception;

public class CheckIllicitValueException extends CheckException {
	private static final long serialVersionUID = 7923154213026926429L;
	private String code = "CheckException";
	private String subCode = "CheckIllicitValueException";

	public CheckIllicitValueException() {
		super();
	}

	public CheckIllicitValueException(String arg0) {
		super(arg0);
	}

	public CheckIllicitValueException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CheckIllicitValueException(Throwable arg0) {
		super(arg0);
	}

	protected CheckIllicitValueException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

package com.zhihui.core.exception;

public class CheckEmptyException extends CheckException {
	private static final long serialVersionUID = 9102016714892256848L;
	private String code = "CheckException";
	private String subCode = "CheckEmptyException";

	public CheckEmptyException() {
		super();
	}

	public CheckEmptyException(String arg0) {
		super(arg0);
	}

	public CheckEmptyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CheckEmptyException(Throwable arg0) {
		super(arg0);
	}

	protected CheckEmptyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

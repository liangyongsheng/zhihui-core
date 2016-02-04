package com.zhihui.core.exception;

public class CheckException extends CoreException {
	private static final long serialVersionUID = 6224458067278021776L;
	private String code = "coreException";
	private String subCode = "checkException";

	public CheckException() {
		super();
	}

	public CheckException(String arg0) {
		super(arg0);
	}

	public CheckException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CheckException(Throwable arg0) {
		super(arg0);
	}

	protected CheckException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

package com.zhihui.core.exception;

public class AsignException extends CoreException {
	private static final long serialVersionUID = 111956721740899706L;
	private String code = "coreException";
	private String subCode = "asignException";

	public AsignException() {
		super();
	}

	public AsignException(String arg0) {
		super(arg0);
	}

	public AsignException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AsignException(Throwable arg0) {
		super(arg0);
	}

	protected AsignException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

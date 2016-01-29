package com.zhihui.core.exception;

public class CoreException extends Exception {
	private static final long serialVersionUID = 2387471240947216134L;
	private String code = "Exception";
	private String subCode = "CoreException";

	public CoreException() {
		super();
	}

	public CoreException(String arg0) {
		super(arg0);
	}

	public CoreException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CoreException(Throwable arg0) {
		super(arg0);
	}

	protected CoreException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

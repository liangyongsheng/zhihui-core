package com.zhihui.core.api;

public class ApiNoMethodException extends ApiException {
	private static final long serialVersionUID = -6181970245569122752L;
	private String code = "apiException";
	private String subCode = "apiNoMethodException";

	public ApiNoMethodException() {
		super();
	}

	public ApiNoMethodException(String arg0) {
		super(arg0);
	}

	public ApiNoMethodException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApiNoMethodException(Throwable arg0) {
		super(arg0);
	}

	protected ApiNoMethodException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
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

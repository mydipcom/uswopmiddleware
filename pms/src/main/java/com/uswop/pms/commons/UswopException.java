package com.uswop.pms.commons;

public class UswopException extends RuntimeException {
	private static final long serialVersionUID = 2741768848973601742L;
	
	protected String errID;

	protected String msg = "";

	protected String str = "";

	public UswopException() {
		super();
	}
	
	public UswopException(Throwable ex) {
		super(ex);
		this.msg = ex.getMessage();
		this.str = "com.uswop.commons.UswopException:"
				+ "\n    nested exception:" + ex.toString();
	}

	public UswopException(String ID, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = ex.getMessage();
		this.str = "com.uswop.commons.UswopException:" + ex.getMessage()
				+ "\n    nested exception:" + ex.toString();
	}

	public UswopException(String ID, String message, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.uswop.commons.UswopException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public UswopException(int ID, String message, Throwable ex) {
		super(ex);
		this.errID = String.valueOf(ID);
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.uswop.commons.UswopException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public UswopException(String ID, String message) {
		this.errID = ID;
		this.msg = message;
		this.str = "com.uswop.commons.UswopException:" + message;
	}

	public UswopException(int ID, String message) {
		this.errID = String.valueOf(ID);
		this.msg = message;
		this.str = "com.uswop.commons.UswopException:" + message;
	}

	public UswopException(String ID) {
		this.errID = ID;
	}

	public UswopException(int ID) {
		this.errID = String.valueOf(ID);
	}

	public String getErrorID() {
		return this.errID;
	}

	public String getMessage() {
		return this.msg;
	}
	
	public String toString() {
		return this.str;
	}

}

package com.perficient.hr.exception;

@SuppressWarnings("serial")
public class PrftException extends GenericException{

	public PrftException() {
		super();
	}
	
	public PrftException(String msg) {
		super(msg);
	}
	
	public PrftException(Throwable ex) {
		super(ex);
	}
	
	public PrftException(String msg, Throwable ex) {
		super(msg, ex);
	}
}

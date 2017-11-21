package it.losko.hotel.model.conf;

public class InvalidParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidParameterException(final String msg) {
		super(msg);
	}
	
	public InvalidParameterException(final String msg, final Exception e) {
		super(msg);
		initCause(e);
	}
}

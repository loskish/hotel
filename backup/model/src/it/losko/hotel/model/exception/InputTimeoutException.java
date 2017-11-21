package it.losko.hotel.model.exception;

public class InputTimeoutException extends HotelException {

	private static final long serialVersionUID = 1L;
	
	public InputTimeoutException(final String msg) {
		super(msg);
	}
}

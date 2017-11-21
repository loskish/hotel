package it.losko.hotel.model.exception;

public class InvalidInputTypeException extends HotelException {

	private static final long serialVersionUID = 1L;

	public InvalidInputTypeException(final String msg) {
		super(msg);
	}
}

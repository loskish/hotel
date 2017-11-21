package it.losko.hotel.model.exception;

/**
 * This is the main father exception
 * 
 * @author losko
 */
public class HotelException extends Exception {

	public HotelException(final String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}

package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to buy an hotel
 * that is already owned
 *  
 * @author losko
 */
public class HotelAlreadyBoughtException extends HotelException {

	public HotelAlreadyBoughtException(final String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}

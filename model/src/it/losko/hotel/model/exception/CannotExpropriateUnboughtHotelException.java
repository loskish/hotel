package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to expropriate an hotel
 * that is owned by none.
 *  
 * @author losko
 */
public class CannotExpropriateUnboughtHotelException extends HotelException {

	public CannotExpropriateUnboughtHotelException(final String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;
}

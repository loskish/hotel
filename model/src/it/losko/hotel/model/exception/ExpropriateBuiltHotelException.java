package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to expropriate an hotel
 * where there is at least one building already built
 *  
 * @author losko
 */
public class ExpropriateBuiltHotelException extends HotelException {

	private static final long serialVersionUID = 1L;

	public ExpropriateBuiltHotelException(final String msg) {
		super(msg);
	}

}

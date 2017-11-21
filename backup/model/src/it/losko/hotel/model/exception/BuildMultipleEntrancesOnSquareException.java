package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to build more
 * than one entrance on a single square
 *  
 * @author losko
 */
public class BuildMultipleEntrancesOnSquareException extends HotelException {

	private static final long serialVersionUID = 1L;
	public BuildMultipleEntrancesOnSquareException(final String msg) {
		super(msg);
	}

}

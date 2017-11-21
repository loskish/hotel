package it.losko.hotel.model.exception;


/**
 * Thrown if someone tries to build an entrance to
 * an hotel that still has no owner
 *  
 * @author losko
 */
public class BuildEntranceOnAnUnboughtHotelException extends HotelException {

	private static final long serialVersionUID = 1L;

	public BuildEntranceOnAnUnboughtHotelException(final String msg) {
		super(msg);
	}

}

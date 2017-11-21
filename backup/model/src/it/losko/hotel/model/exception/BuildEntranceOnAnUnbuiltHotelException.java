package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to build an entrance to
 * an hotel where there isn't at least one building built
 *  
 * @author losko
 */
public class BuildEntranceOnAnUnbuiltHotelException extends HotelException {
	private static final long serialVersionUID = 1L;

	public BuildEntranceOnAnUnbuiltHotelException(final String msg) {
		super(msg);
	}
}

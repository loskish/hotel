package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to build an hotel
 * that has no more buildings to be built
 * 
 * @author losko
 */
public class NoMoreBuildingsToBuildException extends HotelException {

	public NoMoreBuildingsToBuildException(final String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}

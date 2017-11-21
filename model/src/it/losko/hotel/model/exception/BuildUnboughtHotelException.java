package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to build a building
 * when the correspondent hotel is not owned
 *  
 * @author losko
 */
public class BuildUnboughtHotelException extends HotelException {
	private static final long serialVersionUID = 1L; 

	public BuildUnboughtHotelException(final String msg) {
		super(msg);
	}
}

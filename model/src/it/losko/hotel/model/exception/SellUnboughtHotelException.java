package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to build a building
 * when the correspondent hotel is not owned
 *  
 * @author losko
 */
public class SellUnboughtHotelException extends HotelException {
	private static final long serialVersionUID = 1L; 

	public SellUnboughtHotelException(final String msg) {
		super(msg);
	}
}

package it.losko.hotel.model.exception;

/**
 * Thrown if build permission has'nt been granted
 *  
 * @author losko
 */
public class BuildPermissionNotGrantedException extends HotelException {

	private static final long serialVersionUID = 1L;

	public BuildPermissionNotGrantedException(final String msg) {
		super(msg);
	}

}

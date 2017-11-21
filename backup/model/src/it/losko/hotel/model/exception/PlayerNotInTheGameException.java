package it.losko.hotel.model.exception;

/**
 * Thrown if the requested player is not participating in the game
 * 
 * @author losko
 */
public class PlayerNotInTheGameException extends HotelException {

	private static final long serialVersionUID = 1L;

	public PlayerNotInTheGameException(final String msg) {
		super(msg);
	}

}

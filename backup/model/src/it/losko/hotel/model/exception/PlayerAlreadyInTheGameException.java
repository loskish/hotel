package it.losko.hotel.model.exception;

/**
 * Thrown if a player tries to join twice the game
 * 
 * @author losko
 */
public class PlayerAlreadyInTheGameException extends HotelException {

	private static final long serialVersionUID = 1L;

	public PlayerAlreadyInTheGameException(final String msg) {
		super(msg);
	}
}

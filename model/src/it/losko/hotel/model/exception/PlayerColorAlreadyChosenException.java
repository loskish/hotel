package it.losko.hotel.model.exception;

/**
 * Thrown if the same color is chosen by more than one player
 * 
 * @author losko
 */
public class PlayerColorAlreadyChosenException extends HotelException {

	private static final long serialVersionUID = 1L;

	public PlayerColorAlreadyChosenException(final String msg) {
		super(msg);
	}
}

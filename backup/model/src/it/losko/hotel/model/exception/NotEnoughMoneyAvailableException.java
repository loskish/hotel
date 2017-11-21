package it.losko.hotel.model.exception;

/**
 * Thrown if someone tries to perform an action
 * that involves a money transfer but has not
 * enough money to do the requested transfer
 * 
 * @author losko
 */
public class NotEnoughMoneyAvailableException extends HotelException {

	public NotEnoughMoneyAvailableException(final String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}

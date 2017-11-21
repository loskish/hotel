package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * Turn started. A player begins its turn
 * 
 * @author losko
 */
public class TurnStartAction extends Action {
	private static final long serialVersionUID = 1380446751831554533L;

	private final Player current;
	private final Player previous;
	
	public TurnStartAction(final Player current, final Player previous) {
		super();
		this.current = current;
		this.previous = previous;
	}

	/**
	 * Returns the player that ended the turn
	 *  
	 * @return the player that ended the turn
	 */
	public Player getPrevious() {
		return previous;
	}

	/**
	 * Returns the player that begins the turn
	 *  
	 * @return the player that begins the turn
	 */
	public Player getCurrent() {
		return current;
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return current.getName() + " begins the new turn";
	}
}

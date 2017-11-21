package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * Turn ended. A player ended its turn
 * 
 * @author losko
 */
public class TurnEndAction extends Action {
	private static final long serialVersionUID = 5344301727893601860L;

	private final Player current;
	private final Player next;
	
	public TurnEndAction(final Player current, final Player next) {
		super();
		this.current = current;
		this.next = next;
	}

	/**
	 * Returns the player that ends the turn
	 *  
	 * @return the player that ends the turn
	 */
	public Player getCurrent() {
		return current;
	}

	/**
	 * Returns the player that will begin the next turn
	 *  
	 * @return the player that will begin the next turn
	 */
	public Player getNext() {
		return next;
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return current.getName() + " terminates the turn";
	}
}

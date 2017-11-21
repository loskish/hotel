package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * The interface implemented by expropriable objects
 * 
 * @author losko
 */
public interface Expropriable {
	
	/**
	 * Indicates whether the implenting entity is expropriable by the specified player
	 * 
	 * @param doingPlayer	The expropriating player
	 * @return				True if the implenting entity is expropriable by the specified player, false otherwise
	 */
	public boolean isExpropriable(final Player doingPlayer) throws HotelException;
	
	/**
	 * Allows to expropriate the implementing entity
	 */
	public void expropriate(final Player doingPlayer) throws HotelException;
}

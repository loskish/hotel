package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * The interface implemented by buildable objects
 * 
 * @author losko
 */
public interface Buildable {

	/**
	 * Indicates whether the implenting entity is buildable by the specified player
	 * 
	 * @param doingPlayer	The building player
	 * @param forFree		If the player has the right to build for free, this parameter must be true
	 * @return				True if the implenting entity is buildable by the specified player, false otherwise
	 */
	public boolean isBuildable(final Player doingPlayer, final boolean forFree) throws HotelException;
	
	/**
	 * Allows to build the implementing entity
	 */
	public Integer build() throws HotelException;
	
	/**
	 * Allows to build the implementing entity for free
	 */
	public Integer build(final boolean free) throws HotelException;
}

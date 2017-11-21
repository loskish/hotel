package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * The interface implemented by purchasable objects
 * 
 * @author losko
 */
public interface Buyable {

	/**
	 * Indicates whether the implenting entity is purchasable by the specified player
	 * 
	 * @param doingPlayer	The purchasing player
	 * @return				True if the implenting entity is buyable by the specified player, false otherwise
	 */
	public boolean isBuyable(final Player doingPlayer) throws HotelException;
	
	/**
	 * Allows to buy the implementing entity
	 */
	public void buy(Player owner) throws HotelException;
}

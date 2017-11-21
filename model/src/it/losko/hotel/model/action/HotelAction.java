package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.player.Player;

/**
 * An abstract action that involves an hotel
 * 
 * @author losko
 */
public abstract class HotelAction extends PropertyAction {
	private static final long serialVersionUID = 8418080809108521844L;

	public HotelAction(final Player doingPlayer, final Hotel hotel) {
		super(doingPlayer, hotel);
	}
	
	/**
	 * Returns the hotel involved in the action
	 * 
	 * @return the hotel involved in the action
	 */
	public Hotel getHotel() {
		return (Hotel) getProperty();
	}
}

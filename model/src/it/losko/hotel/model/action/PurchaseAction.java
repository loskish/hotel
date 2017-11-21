package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.CannotExpropriateUnboughtHotelException;
import it.losko.hotel.model.exception.HotelAlreadyBoughtException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Some hotel has been bought. This happens
 * when a player lands on a buying square
 * and decides to buy the hotel land
 * 
 * @author losko
 */
public class PurchaseAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = 6114871077450320631L;

	public PurchaseAction(final Player doingPlayer, final Hotel hotel) {
		super(doingPlayer, hotel);
	}

	@Override
	public void doAction() throws HotelAlreadyBoughtException, NotEnoughMoneyAvailableException, CannotExpropriateUnboughtHotelException {
		getHotel().buy(getDoingPlayer());
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " buys " + getHotel().getName();
	}

	@Override
	public String getChoiceDescription() {
		return "Buy " + getHotel().getName() + " for " + getHotel().getLandPrice();
	}
}

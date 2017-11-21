package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.PurchaseSquare;
import it.losko.hotel.model.exception.CannotExpropriateUnboughtHotelException;
import it.losko.hotel.model.exception.ExpropriateBuiltHotelException;
import it.losko.hotel.model.exception.HotelAlreadyBoughtException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * An hotel has been expropriated. This happens
 * when a player lands on a {@link PurchaseSquare} adjacent
 * to an hotel that has been already bought from
 * another user but that hasn't been built yet
 * 
 * @author losko
 */
public class ExpropriateAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = -5346203200942850175L;

	public ExpropriateAction(final Player doingPlayer, final Hotel hotel) {
		super(doingPlayer, hotel);
	}

	@Override
	public void doAction() throws CannotExpropriateUnboughtHotelException, NotEnoughMoneyAvailableException, ExpropriateBuiltHotelException, HotelAlreadyBoughtException {
		getHotel().expropriate(getDoingPlayer());
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " expropriates " + getHotel().getName();
	}

	@Override
	public String getChoiceDescription() {
		return "Expropriate " + getHotel().getName() + " for " + getHotel().getExpropriationPrice();
	}
}

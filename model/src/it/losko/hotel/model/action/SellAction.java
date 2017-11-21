package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.CannotExpropriateUnboughtHotelException;
import it.losko.hotel.model.exception.HotelAlreadyBoughtException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.exception.SellUnboughtHotelException;
import it.losko.hotel.model.player.Player;

/**
 * Some building is being sold. This happens
 * when a player has no more money and wants
 * to continue playing. 
 * 
 * @author losko
 */
public class SellAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = 1781753937712516956L;

	private final String choice;
	private final Double price;

	public SellAction(final Player doingPlayer, final Hotel hotel) throws NoMoreBuildingsToBuildException {
		super(doingPlayer, hotel);
		if(hotel.isAtLeastOneBuildingBuilt()) {
			choice = hotel.getLastBuiltBuilding().getName();
			price = hotel.getLastBuiltBuilding().getSellPrice();
		} else {
			choice = "Land";
			price = hotel.getLandSellPrice();
		}
	}

	@Override
	public void doAction() throws NotEnoughMoneyAvailableException, CannotExpropriateUnboughtHotelException, HotelAlreadyBoughtException, SellUnboughtHotelException {
		getHotel().sell();
	}
	
	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " sells " + getHotel().getName() + "'s " + choice + " for " + price;
	}

	public String getChoiceDescription() {
		return "Sell " + getHotel().getName() + "'s " + choice + " for " + price;
	}
}

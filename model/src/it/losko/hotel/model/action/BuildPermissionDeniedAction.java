package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Build permission has been denied. This happens
 * when a player rolls the construction dice and
 * the result tells that the permission is denied
 * 
 * @author losko
 */
public class BuildPermissionDeniedAction extends HotelAction {
	private static final long serialVersionUID = -4646643323290378353L;

	private final Building building;

	public BuildPermissionDeniedAction(final Player doingPlayer, final Hotel hotel) throws NoMoreBuildingsToBuildException {
		super(doingPlayer, hotel);
		building = getHotel().getNextBuilding();
	}
	
	/**
	 * Returns the building that is being built
	 * 
	 * @return the building that is being built
	 */
	public Building getBuilding() {
		return building;
	}
	
	@Override
	public void doAction() throws NoMoreBuildingsToBuildException, BuildUnboughtHotelException, NotEnoughMoneyAvailableException, BuildPermissionNotGrantedException {
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " isn't allowed to build " + getHotel().getName() + "'s " + building.getName();
	}
}

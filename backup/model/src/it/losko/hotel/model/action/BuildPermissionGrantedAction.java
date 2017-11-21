package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Player is allowed to build. This happens when
 * a player rolls the construction dice and the
 * result tells that the permission is granted
 * 
 * @author losko
 */
public class BuildPermissionGrantedAction extends HotelAction {
	private static final long serialVersionUID = -2337246743621562061L;

	private final Building building;
	
	public BuildPermissionGrantedAction(final Player doingPlayer, final Hotel hotel) {
		super(doingPlayer, hotel);
		building = getHotel().getLastBuiltBuilding();
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
		return getDoingPlayer().getName() + " is allowed to build " + getHotel().getName() + "'s " + building.getName();
	}
}

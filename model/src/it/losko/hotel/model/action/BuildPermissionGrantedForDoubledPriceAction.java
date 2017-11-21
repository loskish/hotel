package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Build permission has been granted for doubled
 * price. This happens when a player rolls the
 * construction dice and the result tells that
 * the permission is allowed but doubling the
 * regular building price
 * 
 * @author losko
 */
public class BuildPermissionGrantedForDoubledPriceAction extends HotelAction {
	private static final long serialVersionUID = -3565385105185058753L;

	private final Building building;
	
	public BuildPermissionGrantedForDoubledPriceAction(final Player doingPlayer, final Hotel hotel) {
		this(doingPlayer, hotel, hotel.getLastBuiltBuilding());
	}

	public BuildPermissionGrantedForDoubledPriceAction(final Player doingPlayer, final Hotel hotel, final Building building) {
		super(doingPlayer, hotel);
		this.building = building;
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
		return getDoingPlayer().getName() + " is allowed to build " + getHotel().getName() + "'s " + building.getName() + " but at doubled price";
	}
}

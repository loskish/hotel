package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.FreeBuildingSquare;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * A building is built for free. This happens
 * when a player lands on a {@link FreeBuildingSquare}
 * or when a player rolls the construction dice
 * and the result tells that building permission
 * is granted for free
 * 
 * @author losko
 */
public class BuildForFreeAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = -3690855681558529491L;

	private final Building building;
	
	public BuildForFreeAction(final Player doingPlayer, final Hotel hotel) throws NoMoreBuildingsToBuildException {
		super(doingPlayer, hotel);
		building = hotel.getNextBuilding();
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
		getHotel().build(true);
	}
	
	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " builds " + getHotel().getName() + "'s " + building.getName() + " for free";
	}

	@Override
	public String getChoiceDescription() {
		return "Build " + getHotel().getName() + "'s " + building.getName() + " for free";
	}
}

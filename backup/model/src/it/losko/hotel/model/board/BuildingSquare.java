package it.losko.hotel.model.board;

import it.losko.hotel.model.action.BuildAction;
import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.player.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A square where it is possible to build an hotel
 * 
 * @author losko
 */
public class BuildingSquare extends Square {
	private static final long serialVersionUID = -8188083374657221804L;

	protected BuildingSquare(final List<Property> adjacentProperties) {
		super(adjacentProperties);
	}
	
	@Override
	public Set<PlayerAction> getAvailableActions(final Player doingPlayer) throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		final Set<PlayerAction> availableActions = new LinkedHashSet<PlayerAction>();
		availableActions.addAll(super.getAvailableActions(doingPlayer));
		
		if(doingPlayer.hasRolledDice()) {
			for(final Hotel hotel : getBuildableHotels(doingPlayer)) {
				availableActions.add(new BuildAction(doingPlayer, hotel));
			}
		}
		
		return availableActions;
	}

	/**
	 * Returns the list of hotels buildable on the square
	 * 
	 * @param doingPlayer	Player involved in the action
	 * @return the list of hotels buildable on the square
	 */
	public List<Hotel> getBuildableHotels(final Player doingPlayer) {
		final List<Hotel> buildableHotels = new ArrayList<Hotel>();
		for(final Hotel hotel : doingPlayer.getOwnedHotels()) {
			if(hotel.isBuildable(doingPlayer, false))
				buildableHotels.add(hotel);
		}
		return buildableHotels;
	}
}

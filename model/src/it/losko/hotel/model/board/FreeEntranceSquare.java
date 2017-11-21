package it.losko.hotel.model.board;

import it.losko.hotel.model.action.BuildEntranceForFreeAction;
import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.player.Player;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A square where it is possible to build an entrance to an hotel for free
 * 
 * @author losko
 */
public class FreeEntranceSquare extends Square {
	private static final long serialVersionUID = -5365049266492937683L;

	protected FreeEntranceSquare(final List<Property> adjacentProperties) {
		super(adjacentProperties);
	}
	
	@Override
	public Set<PlayerAction> getAvailableActions(final Player doingPlayer) throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		final Set<PlayerAction> availableActions = new LinkedHashSet<PlayerAction>();
		availableActions.addAll(super.getAvailableActions(doingPlayer));
		
		if(doingPlayer.hasRolledDice()
			&& !doingPlayer.hasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs()
			&& !doingPlayer.hasRefusedAnymoreActionsOnTheSquareHeIs()) {
			
			for(final Hotel hotel : doingPlayer.getOwnedHotels()) {
				for(final Square square : hotel.getAdjacentSquaresSuitableForBuildingEntrance()) {
					availableActions.add(new BuildEntranceForFreeAction(doingPlayer, hotel, square));
				}
			}
		}
		
		return availableActions;
	}
}

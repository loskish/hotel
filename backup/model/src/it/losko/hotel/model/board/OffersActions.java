package it.losko.hotel.model.board;

import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.player.Player;

import java.util.Set;

/**
 * Interface implemented by squares that
 * offer the possibility to make some action 
 * 
 * @author losko
 */
public interface OffersActions {
	
	/**
	 * Defines the set of actions that are available on the square
	 * 
	 * @param doingPlayer	Player involved in the action
	 * @return the set of actions that are available on the square
	 */
	public Set<PlayerAction> getAvailableActions(final Player doingPlayer) throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException;
}

package it.losko.hotel.model.board;

import it.losko.hotel.model.action.BuildEntranceAction;
import it.losko.hotel.model.action.PayForStayAction;
import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.action.RollDiceAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The class defining the square on which a player can land on
 * 
 * @author losko
 */
public class Square implements OffersActions, Serializable {
	private static final long serialVersionUID = -5505046892921894445L;

	private final List<Property> adjacentProperties;
	private final List<Player> standingPlayers;
	private Entrance entrance;
	
	protected Square(final List<Property> adjacentProperties) {
		standingPlayers = new ArrayList<Player>();
		this.adjacentProperties = adjacentProperties;
		
		for(final Hotel hotel : getAdjacentHotels()) {
			hotel.addAdjacentSquare(this);
		}
	}

	/**
	 * Returns the list of adjacent properties
	 * 
	 * @return the list of adjacent properties
	 */
	public List<Property> getAdjacentProperties() {
		return adjacentProperties;
	}
	
	/**
	 * Returns the list of players standing still on the square
	 * 
	 * @return the list of players standing still on the square
	 */
	public List<Player> getStandingPlayers() {
		return standingPlayers;
	}
	
	/**
	 * Sets an entrance on the square 
	 * 
	 * @param entrance The entrance to set
	 */
	public void setEntrance(final Entrance entrance) {
		this.entrance = entrance;
	}
	
	/**
	 * Returns the entrance on the square, if one
	 * 
	 * @return the entrance on the square, if one
	 */
	public Entrance getEntrance() {
		return entrance;
	}
	
	/**
	 * Indicates whatever the square has an entrance 
	 * 
	 * @return true if the square has an entrance, false otherwise
	 */
	public boolean hasEntrance() {
		return getEntrance() != null;
	}
	
	/**
	 * Returns the list of adjacent Hotels
	 * 
	 * @return the list of adjacent Hotels
	 */
	public List<Hotel> getAdjacentHotels() {
		final List<Hotel> adjacentHotels = new ArrayList<Hotel>();
		for(final Property property : getAdjacentProperties()) {
			if(property instanceof Hotel) {
				adjacentHotels.add((Hotel) property);
			}
		}
		return adjacentHotels;
	}
	
	/**
	 * Returns the list of adjacent Hotels
	 * suitable for builing an entrance
	 * 
	 * @return the list of adjacent Hotels suitable for builing an entrance
	 */
	public List<Hotel> getAdjacentHotelsSuitableForBuildingEntrance() {
		final List<Hotel> adjacentHotelsSuitable = new ArrayList<Hotel>();
		
		if(!hasEntrance()) {
			for(final Hotel hotel : getAdjacentHotels()) {
				if(hotel.isAtLeastOneBuildingBuilt()) {
					adjacentHotelsSuitable.add(hotel);
				}
			}
		}
		
		return adjacentHotelsSuitable;
	}
	
	public Set<PlayerAction> getAvailableActions(final Player doingPlayer) throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		final Set<PlayerAction> availableActions = new LinkedHashSet<PlayerAction>();
		
		// AUTOMATIC ACTIONS START
		if(doingPlayer.hasRolledDice()) {
			// Pay for stay action
			if(hasEntrance()
				&& !doingPlayer.equals(getEntrance().getHotel().getOwner())
				&& !doingPlayer.hasAlreadyMadeAPayForStayActionOnTheSquareHeIs()) {
				availableActions.add(new PayForStayAction(doingPlayer, getEntrance().getHotel()));
			}
			
			// Build entrance action
			if(doingPlayer.hasSteppedThrough(Game.getSingleton().getBoard().getMunicipePosition())
					&& !doingPlayer.hasRefusedAnymoreActionsOnTheSquareHeIs()) {
				for(final Hotel hotel : doingPlayer.getOwnedHotels()) {
					if(!doingPlayer.hasAlreadyMadeABuildEntranceActionForThisHotelOnTheSquareHeIs(hotel)
							&& doingPlayer.getAvailableMoney() >= hotel.getEntrancePrice()) {
						for(final Square square : hotel.getAdjacentSquaresSuitableForBuildingEntrance()) {
							availableActions.add(new BuildEntranceAction(doingPlayer, hotel, square));
						}
					}
				}
			}
		}

		if(doingPlayer.mustRollDice()) {
			final PlayerAction action = new RollDiceAction(doingPlayer);
			action.setDelayable(doingPlayer.hasRolledDice());
			availableActions.add(action);
		}
		// AUTOMATIC ACTIONS END
		
		return availableActions;
	}
}

package it.losko.hotel.model.board;

import it.losko.hotel.model.action.ExpropriateAction;
import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.action.PurchaseAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.player.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A square where it is possible to buy an hotel
 * 
 * @author losko
 */
public class PurchaseSquare extends Square {
	private static final long serialVersionUID = 4966960121329280060L;

	protected PurchaseSquare(final List<Property> adjacentProperties) {
		super(adjacentProperties);
	}
	
	@Override
	public Set<PlayerAction> getAvailableActions(final Player doingPlayer) throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		final Set<PlayerAction> availableActions = new LinkedHashSet<PlayerAction>();
		availableActions.addAll(super.getAvailableActions(doingPlayer));
		
		if(doingPlayer.hasRolledDice()) {
			for(final Hotel hotel : getBuyableHotels(doingPlayer)) {
				availableActions.add(new PurchaseAction(doingPlayer, hotel));
			}
			for(final Hotel hotel : getExpropriableHotels(doingPlayer)) {
				availableActions.add(new ExpropriateAction(doingPlayer, hotel));
			}
		}
		
		return availableActions;
	}

	/**
	 * Returns the list of buyable hotels
	 * 
	 * @param doingPlayer The player doing the action
	 * @return the list of buyable hotels
	 */
	public List<Hotel> getBuyableHotels(final Player doingPlayer) {
		final List<Hotel> buyableHotels = new ArrayList<Hotel>();
		for(final Hotel hotel : getAdjacentHotels()) {
			if(hotel.isBuyable(doingPlayer))
				buyableHotels.add(hotel);
		}

		return buyableHotels;
	}
	
	/**
	 * Returns the list of expropriable hotels
	 * 
	 * @param doingPlayer The player doing the action
	 * @return the list of expropriable hotels
	 */
	public List<Hotel> getExpropriableHotels(final Player doingPlayer) {
		final List<Hotel> expropriableHotels = new ArrayList<Hotel>();
		if(!doingPlayer.hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs()
			&& !doingPlayer.hasAlreadyMadeAPurchaseActionOnThisSquare()
			&& !doingPlayer.hasRefusedAnymoreActionsOnTheSquareHeIs()) {
			for(final Hotel hotel : getAdjacentHotels()) {
				if(hotel.isExpropriable(doingPlayer))
					expropriableHotels.add(hotel);
			}
		}
		return expropriableHotels;
	}
}

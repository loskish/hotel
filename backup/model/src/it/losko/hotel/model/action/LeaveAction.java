package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

/**
 * A player leaves the game. This generally
 * happens when a player has no more money
 * 
 * @author losko
 */
public class LeaveAction extends PlayerAction {
	private static final long serialVersionUID = -5327197813199245785L;
	
	private final int playerIndex;
	
	public LeaveAction(final Player doingPlayer) {
		super(doingPlayer);
		
		playerIndex = Game.getSingleton().getPlayers().getAll().indexOf(doingPlayer);
	}

	public int getPlayerIndex() {
		return playerIndex;
	}
	
	@Override
	protected void doAction() throws HotelException {
		Game.getSingleton().getPlayers().remove(getDoingPlayer());
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " leaves the game";
	}
}

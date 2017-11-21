package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Bank;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

/**
 * A player earn interests from the {@link Bank}. This happens
 * when a player steps through the {@link Bank} square
 * 
 * @author losko
 */
public class EarnInterestsAction extends PlayerAction {
	private static final long serialVersionUID = -4832230500679035870L;

	public EarnInterestsAction(final Player doingPlayer) {
		super(doingPlayer);
	}

	@Override
	protected void doAction() throws HotelException {
		try {
			Game.getSingleton().getBoard().getBank().giveMoneyToPlayer(getDoingPlayer(), Game.getSingleton().getBoard().getBank().getInterestAmount());
		} catch (final NotEnoughMoneyAvailableException e) {
			Game.getSingleton().end();
		}
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " earns " + Game.getSingleton().getBoard().getBank().getInterestAmount() + " from " + Game.getSingleton().getBoard().getBank().getName();
	}
}

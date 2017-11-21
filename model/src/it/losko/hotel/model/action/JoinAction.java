package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * A player joins the game. This happens when
 * the game isn't started yet, and users are
 * registering for their participation
 * 
 * @author losko
 */
public class JoinAction extends PlayerAction {
	private static final long serialVersionUID = 1619386544247440104L;

	public JoinAction(final Player doingPlayer) {
		super(doingPlayer);
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " joins the game";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

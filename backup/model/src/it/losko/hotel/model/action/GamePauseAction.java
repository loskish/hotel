package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;

/**
 * Game paused. This happens when 
 * the game is temporarily suspended
 * 
 * @author losko
 */
public class GamePauseAction extends Action {
	private static final long serialVersionUID = 8212037470688258601L;

	public GamePauseAction() {
		super();
	}

	@Override
	protected void doAction() throws HotelException {
		Game.getSingleton().pause();
	}

	@Override
	public String getNotifiableDescription() {
		return "Pause of the game";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

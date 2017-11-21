package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;

/**
 * Game resumed. This happens when 
 * a temporarly suspended game is resumed
 * 
 * @author losko
 */
public class GameResumeAction extends Action {
	private static final long serialVersionUID = 5526691074454212981L;

	public GameResumeAction() {
		super();
	}

	@Override
	protected void doAction() throws HotelException {
		Game.getSingleton().resume();
	}

	@Override
	public String getNotifiableDescription() {
		return "Game resume";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

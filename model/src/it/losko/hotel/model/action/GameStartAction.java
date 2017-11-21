package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;

/**
 * Game started. This happens when all players
 * join the game and they're ready to begin
 * 
 * @author losko
 */
public class GameStartAction extends Action implements Choosable {
	private static final long serialVersionUID = 2907349305034086980L;

	public GameStartAction() {
		super();
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return "Start of the game";
	}

	@Override
	public String getChoiceDescription() {
		return "Start game";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

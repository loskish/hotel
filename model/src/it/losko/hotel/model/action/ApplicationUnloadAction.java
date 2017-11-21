package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;

/**
 * Application unloaded. This happens when program's
 * main routines are terminated and the game is over
 * 
 * @author losko
 */
public class ApplicationUnloadAction extends Action implements Choosable {
	private static final long serialVersionUID = -1902033312652891080L;

	public ApplicationUnloadAction() {
		super();
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return "Application unloaded";
	}

	@Override
	public String getChoiceDescription() {
		return "Quit";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.player.Player;

/**
 * A mistyped input has been received. This happens
 * when a {@link PlayerActionSelectionAction} has been triggered
 * but the received response isn't formally correct.
 * User is asked again to make his choice.
 * 
 * @author losko
 */
public class WrongInputAction extends PlayerAction {
	private static final long serialVersionUID = 3978460073646902467L;

	public WrongInputAction(final Player doingPlayer) {
		super(doingPlayer);
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return "Input from " + getDoingPlayer().getName() + " is not clear. Please choose again";
	}
}

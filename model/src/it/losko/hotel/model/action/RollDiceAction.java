package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

import java.util.Random;

/**
 * The dice has been rolled. This happens
 * when player turn has changed.
 * 
 * @author losko
 */
public class RollDiceAction extends PlayerAction implements Choosable {
	private static final long serialVersionUID = -4160218926947143584L;

	private int result = -1;

	public RollDiceAction(final Player doingPlayer) {
		super(doingPlayer);
		setMandatory(true);
	}
	
	/**
	 * Returns the result given by the dice
	 * 
	 * @return the result given by the dice
	 */
	public int getResult() {
		return result;
	}
	
	/**
	 * Sets the result given by the dice. This method
	 * is private because only the class itself must
	 * have the right to modify the result
	 * 
	 * @param result The result given by the dice
	 */
	private void setResult(final int result) {
		this.result = result;
	}

	@Override
	public void doAction() throws HotelException {
		final Random random = new Random();
		setResult(random.nextInt(6) + 1);
		
		// Externally setted flags. This is done because of
		// the possibility of moving forward without
		// rolling dice (eg: player rolled 6)
		getDoingPlayer().setHasRolledDice(true);
		getDoingPlayer().setMustRollDice(getResult() == 6);
		
		getDoingPlayer().moveForward(getResult());
	}

	@Override
	protected void postDoAction() throws HotelException {
		super.postDoAction();
		
		if(getDoingPlayer().hasSteppedThrough(Game.getSingleton().getBoard().getBankPosition())) {
			final EarnInterestsAction earnInterestsAction = new EarnInterestsAction(getDoingPlayer());
			earnInterestsAction.executeAction();
		}
	}
	
	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " rolls dice" + (getResult() != -1 ? " and makes " + getResult() : "");
	}

	public String getChoiceDescription() {
		return "Roll dice";
	}
}

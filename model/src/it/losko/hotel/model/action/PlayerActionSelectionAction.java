package it.losko.hotel.model.action;

import it.losko.hotel.model.player.Player;

import java.util.List;

/**
 * User is requested to input. This happens when
 * a player is requested to make a choice between
 * a set of actions. This action expects to receive
 * some input from the user interface implementation
 * 
 * @author losko
 */
public class PlayerActionSelectionAction extends ActionSelectionAction implements PlayerActionInterface {
	private static final long serialVersionUID = 7099457610544610723L;

	private final Player doingPlayer;
	
	public PlayerActionSelectionAction(final Player doingPlayer, final List<Choosable> actionsList) {
		super(actionsList);
		this.doingPlayer = doingPlayer;
	}
	
	/**
	 * Returns the player involved in the action
	 * 
	 * @return the player involved in the action
	 */
	public Player getDoingPlayer() {
		return doingPlayer;
	}
	
	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " is requested for input";
	}
}

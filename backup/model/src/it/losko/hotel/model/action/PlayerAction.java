package it.losko.hotel.model.action;

import it.losko.hotel.model.player.Player;

/**
 * An abstract action involving a player
 * 
 * @author losko
 */
public abstract class PlayerAction extends Action implements PlayerActionInterface {
	private static final long serialVersionUID = 5443417911357631959L;

	private final Player doingPlayer;
	
	// The next two ones are made as fields
	// because of the needed runtime mutability
	protected boolean mandatory;
	protected boolean delayable;
	
	public PlayerAction(final Player doingPlayer) {
		super();
		this.doingPlayer = doingPlayer;
		setMandatory(false);
		setDelayable(false);
	}
	
	/**
	 * Returns the player involved in the action
	 * 
	 * @return the player involved in the action
	 */
	public Player getDoingPlayer() {
		return doingPlayer;
	}
	
	/**
	 * Indicates if the action is mandatory
	 * 
	 * @return true if the action is mandatory, false otherwise
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * Sets the mandatoriety of the action
	 * 
	 * @param mandatory Indicates if the action is mandatory
	 */
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * Indicates if the action is delayable
	 * 
	 * @return true if the action is delayable, false otherwise
	 */
	public boolean isDelayable() {
		return delayable;
	}

	/**
	 * Sets the delayability of the action
	 * 
	 * @param delayable Indicates if the action is delayable
	 */
	public void setDelayable(final boolean delayable) {
		this.delayable = delayable;
	}
}

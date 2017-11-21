package it.losko.hotel.model.action;

import it.losko.hotel.model.player.Player;

public interface PlayerActionInterface {
	
	/**
	 * Returns the player involved in the action
	 * 
	 * @return the player involved in the action
	 */
	public abstract Player getDoingPlayer();
}

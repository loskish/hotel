package it.losko.hotel.model.action;

/**
 * Interface implemented by actions that can be
 * chosen by a player when he's requested for input 
 * 
 * @author losko
 */
public interface Choosable {
	/**
	 * Returns the description of the action for a player choice
	 * 
	 * @return The description of the action for a player choice
	 */
	public String getChoiceDescription();
}

package it.losko.hotel.model.history;

import it.losko.hotel.model.action.Action;

import java.io.Serializable;

/**
 * An element of the {@link History} of the game
 * This class contains the {@link Action} that has
 * been made plus eventually additional data that are
 * not properly part of the {@link Action} itself
 * 
 * @author losko
 */
public class HistoryElement implements Serializable {
	private static final long serialVersionUID = 2070782107372706041L;

	private final Action action;
	
	protected HistoryElement(final Action action) {
		this.action = action;
	}

	/**
	 * Returns the action contained in the history entry.
	 * This is the action thas has been made during the game
	 * 
	 * @return the action contained in the history entry.
	 */
	public Action getAction() {
		return action;
	}
}

package it.losko.hotel.model.history;

import it.losko.hotel.model.action.Action;

import java.io.Serializable;
import java.util.Stack;

/**
 * This class keeps track of every {@link Action}
 * that has been made during the game. Every history
 * entry is an {@link HistoryElement} object
 * 
 * @author losko
 */
public class History implements Serializable {
	private static final long serialVersionUID = 4556847884603693659L;

	private final Stack<HistoryElement> history;
	
	public History() {
		history = new Stack<HistoryElement>();
	}
	
	/**
	 * Pushes an action on the history's stack
	 * 
	 * @param action	The action to add to history
	 * @return			The pushed action
	 */
	public HistoryElement push(final Action action) {
		final HistoryElement he = new HistoryElement(action);
		return history.push(he);
	}
}

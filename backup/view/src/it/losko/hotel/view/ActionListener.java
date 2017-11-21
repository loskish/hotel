package it.losko.hotel.view;

import it.losko.hotel.model.action.Action;

public abstract class ActionListener {
	public abstract void actionNotified(final UserInterface ui, final Action uncastedAction);
}

package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.ExceptionAction;
import it.losko.hotel.view.tui.TUI;

public class ExceptionActionListener extends TUIActionListener {
	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final ExceptionAction action = (ExceptionAction) uncastedAction;
		
		action.setInputAsBoolean(ui.handleException(action.getException()));
	}
}

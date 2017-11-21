package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.view.tui.TUI;

public class ApplicationUnloadActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		ui.getInputObserver().unregisterAll();
	}
}
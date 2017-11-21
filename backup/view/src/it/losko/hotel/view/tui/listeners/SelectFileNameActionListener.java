package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.SelectFileNameAction;
import it.losko.hotel.view.tui.TUI;

public class SelectFileNameActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final SelectFileNameAction action = (SelectFileNameAction) uncastedAction;
		
		final StringBuffer sb = new StringBuffer("Enter file's name or press Enter to cancel: ");
		action.setInputAsString(ui.askForInput(sb.toString()));
	}
}

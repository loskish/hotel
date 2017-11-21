package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.view.ActionListener;
import it.losko.hotel.view.UserInterface;
import it.losko.hotel.view.tui.TUI;

public class TUIActionListener extends ActionListener {

	@Override
	public void actionNotified(final UserInterface ui, final Action uncastedAction) {
		actionNotified((TUI) ui, uncastedAction);
	}
	
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		ui.showMessage(uncastedAction.getNotifiableDescription());
	}
}

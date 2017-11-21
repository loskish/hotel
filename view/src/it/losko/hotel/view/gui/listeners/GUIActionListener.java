package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.view.ActionListener;
import it.losko.hotel.view.UserInterface;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIMainWindow;

public class GUIActionListener extends ActionListener {

	@Override
	public void actionNotified(final UserInterface ui, final Action uncastedAction) {
		actionNotified((GUI) ui, uncastedAction);
	}
	
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		final GUIMainWindow guiMainWindow = ui.getMainWindow();
		guiMainWindow.updateValues(uncastedAction);
	}
}

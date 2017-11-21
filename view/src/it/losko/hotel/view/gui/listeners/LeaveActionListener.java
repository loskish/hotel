package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.LeaveAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIMainWindow;

import javax.swing.JTabbedPane;

public class LeaveActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final LeaveAction action = (LeaveAction) uncastedAction;
		final GUIMainWindow guiMainWindow = ui.getMainWindow();
		final JTabbedPane playersTabbedPane = guiMainWindow.getPlayersTabbedPane();

		final int tabIndex = playersTabbedPane.indexOfTab(action.getDoingPlayer().getName());
		if(tabIndex > 0) {
			playersTabbedPane.removeTabAt(tabIndex);
		}
	}
}

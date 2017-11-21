package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.TurnStartAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIMainWindow;

import javax.swing.JTabbedPane;

public class TurnStartActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final TurnStartAction action = (TurnStartAction) uncastedAction;
		final GUIMainWindow guiMainWindow = ui.getMainWindow();
		final JTabbedPane playersTabbedPane = guiMainWindow.getPlayersTabbedPane();

		playersTabbedPane.setSelectedIndex(playersTabbedPane.indexOfTab(action.getCurrent().getName()));
	}
}

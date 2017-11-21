package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.PlayerActionSelectionAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIMainWindow;

public class PlayerActionSelectionActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final PlayerActionSelectionAction action = (PlayerActionSelectionAction) uncastedAction;

		final GUIMainWindow guiMainWindow = ui.getMainWindow();
		guiMainWindow.askAction(action);
	}
}

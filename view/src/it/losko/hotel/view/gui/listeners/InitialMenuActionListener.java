package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.InitialMenuAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIMainWindow;

public class InitialMenuActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final InitialMenuAction action = (InitialMenuAction) uncastedAction;

        final GUIMainWindow gUIMainWindow = ui.getMainWindow();
		gUIMainWindow.askAction(action);
	}
}

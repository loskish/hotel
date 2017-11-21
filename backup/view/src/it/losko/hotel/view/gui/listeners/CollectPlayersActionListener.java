package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.CollectPlayersAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIPlayersDialog;

public class CollectPlayersActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final CollectPlayersAction action = (CollectPlayersAction) uncastedAction;

		ui.getMainWindow().clear();

		final GUIPlayersDialog dialog = new GUIPlayersDialog(ui, ui.getMainWindow(), true, action);
		dialog.setVisible(true);
	}
}

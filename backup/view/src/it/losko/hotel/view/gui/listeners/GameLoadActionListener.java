package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIPlayerTab;

import javax.swing.JTabbedPane;

public class GameLoadActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		ui.getMainWindow().clear();
		
		final JTabbedPane playersTabbedPane = ui.getMainWindow().getPlayersTabbedPane();
		for(final Player player : Game.getSingleton().getPlayers().getAll()) {
			final GUIPlayerTab guiPlayerTab = new GUIPlayerTab(ui, player);
			playersTabbedPane.addTab(player.getName(), guiPlayerTab);
		}
	}
}

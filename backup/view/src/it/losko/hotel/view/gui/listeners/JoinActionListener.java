package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.JoinAction;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIPlayerTab;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class JoinActionListener extends GUIActionListener {

	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final JoinAction action = (JoinAction) uncastedAction;
		final Player player = action.getDoingPlayer();
		
		final JTabbedPane playersTabbedPane = ui.getMainWindow().getPlayersTabbedPane();
		final GUIPlayerTab guiPlayerTab = new GUIPlayerTab(ui, player);
		
		Image car = ui.getImages().getCarByPlayer(player);
		Image scaledcar = car.getScaledInstance(10, 10, Image.SCALE_AREA_AVERAGING);
		
		playersTabbedPane.addTab(player.getName(), new ImageIcon(scaledcar), guiPlayerTab);
		ui.getMainWindow().getBoard().repaint();
	}
}

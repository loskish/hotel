package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.CollectPlayersAction;
import it.losko.hotel.model.exception.PlayerColorAlreadyChosenException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.PlayersBuilder;
import it.losko.hotel.view.tui.TUI;

public class CollectPlayersActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final CollectPlayersAction action = (CollectPlayersAction) uncastedAction;
		
		// Asks for total number of players
		Integer nPlayers = null;
		while(nPlayers == null) {
			try {
				nPlayers = Integer.valueOf(ui.askForInput("How many players? [" + Game.getSingleton().getPlayers().getMinimumNumber() + "-" + Game.getSingleton().getPlayers().getMaximumNumber() + "]: "));
				if(nPlayers < Game.getSingleton().getPlayers().getMinimumNumber() || nPlayers > Game.getSingleton().getPlayers().getMaximumNumber())
					nPlayers = null;
			} catch (final NumberFormatException e) {
			}
		}
		
		// Asks for number of human players
		Integer hPlayers = null;
		while(hPlayers == null) {
			try {
				hPlayers = Integer.valueOf(ui.askForInput("How many human players? [0-"+ nPlayers +"]: "));
				if(hPlayers < 0 || hPlayers > nPlayers) {
					hPlayers = null;
				}
			} catch (final NumberFormatException e) {
			}
		}
		
		final PlayersBuilder pb = new PlayersBuilder();
		
		// Asks for human players' name
		for(int i = 0; i < hPlayers; i++) {
			String name = ui.askForInput("Name of player #" + (i + 1) + ": ");
			while(name == null || name.trim().isEmpty()) {
				name = ui.askForInput("Invalid name, please type again.\nName of player #" + (i + 1) + ": ");
			}
			// Color is automatic in TUI
			try {
				pb.add(name, null, false);
			} catch (PlayerColorAlreadyChosenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ui.showMessage("");
		
		// Adds remaining AI players
		for(int i = hPlayers; i < nPlayers; i++) {
			try {
				pb.add("Computer" + i, null, true);
			} catch (PlayerColorAlreadyChosenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		action.setInputAsPlayersBuilder(pb);
	}
}

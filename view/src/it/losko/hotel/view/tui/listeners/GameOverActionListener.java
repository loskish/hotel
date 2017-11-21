package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.tui.TUI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class GameOverActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		String msg = "\nGame over! Players ranking:";
		
		final DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
		final DecimalFormat myFormatter = new DecimalFormat("00", formatSymbols);
		final List<Player> players = Game.getSingleton().getPlayers().rank();
		for(int i = 0; i < players.size(); i++) {
			final Player player = players.get(i);
			msg = msg.concat("\n#");
			msg = msg.concat(myFormatter.format((i + 1)));
			msg = msg.concat(": ");
			msg = msg.concat(player.getName());
			msg = msg.concat(", with a total patrimony of ");
			msg = msg.concat(String.valueOf(player.getPatrimony()));
		}

		ui.showMessage(msg + "\n");
		
		ui.getInputObserver().unregisterAll();
	}
}

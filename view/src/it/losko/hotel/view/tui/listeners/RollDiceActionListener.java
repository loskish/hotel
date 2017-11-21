package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.RollDiceAction;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.tui.TUI;

public class RollDiceActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final RollDiceAction action = (RollDiceAction) uncastedAction;
		final Player doingPlayer = action.getDoingPlayer();
		final StringBuffer sb = new StringBuffer(action.getNotifiableDescription());
		sb.append(" (position: " + doingPlayer.getPositionOnBoard() + ")");
		ui.showMessage(sb.toString());
	}
}

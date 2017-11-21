package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.PurchaseAction;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.tui.TUI;

public class PurchaseActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final PurchaseAction action = (PurchaseAction) uncastedAction;
		
		final Player doingPlayer = action.getDoingPlayer();
		final StringBuffer sb = new StringBuffer(action.getNotifiableDescription());
		sb.append(" (money: " + doingPlayer.getAvailableMoney() + ")");
		ui.showMessage(sb.toString());
	}
}

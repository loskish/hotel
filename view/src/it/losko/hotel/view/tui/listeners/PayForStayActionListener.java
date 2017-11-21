package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.PayForStayAction;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.tui.TUI;

public class PayForStayActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final PayForStayAction action = (PayForStayAction) uncastedAction;
		
		final Player doingPlayer = action.getDoingPlayer();
		final StringBuffer sb = new StringBuffer(action.getNotifiableDescription());
		sb.append(" (money: " + doingPlayer.getAvailableMoney() + ")");
		ui.showMessage(sb.toString());
	}
}

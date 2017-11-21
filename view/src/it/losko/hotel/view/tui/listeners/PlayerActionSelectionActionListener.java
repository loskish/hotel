package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.PlayerActionSelectionAction;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.view.tui.TUI;

public class PlayerActionSelectionActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final PlayerActionSelectionAction action = (PlayerActionSelectionAction) uncastedAction;
		
		final StringBuffer sb = new StringBuffer(action.getDoingPlayer().getName() + " can choose one of the following actions:\n");
		for (int i = 0; i < action.getActionsList().size(); i++) {
			final Choosable actionChoice = action.getActionsList().get(i);
			sb.append("\t" + (i + 1) + ": " + actionChoice.getChoiceDescription() + "\n");
		}
		sb.setLength(sb.length() - 1);
		
		if(action.isSkippable()) {
			sb.append("\n\tEnter: Skip ");
		}

		String result = ui.askForInput(sb.toString());
		Integer choice = null;

		while(choice == null && !Game.getSingleton().isTerminating()) {
			if(result == null && action.isSkippable()) {
				break;
			}
			
			try {
				choice = Integer.valueOf(result);
			} catch (final NumberFormatException e) {
				ui.showMessage("Invalid input. Please type again");
				result = ui.askForInput(sb.toString());
			}
		}
		
		action.setInputAsInteger(choice);
	}
}

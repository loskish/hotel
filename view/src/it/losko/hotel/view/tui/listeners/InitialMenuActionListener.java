package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.InitialMenuAction;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.view.tui.TUI;

public class InitialMenuActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final InitialMenuAction action = (InitialMenuAction) uncastedAction;
		
		final StringBuffer sb = new StringBuffer("Choose one of the following actions:\n");
		for (int i = 0; i < action.getActionsList().size(); i++) {
			final Choosable actionChoice = action.getActionsList().get(i);
			sb.append("\t" + (i + 1) + ": " + actionChoice.getChoiceDescription() + "\n");
		}

		String result = ui.askForInput(sb.toString());
		Integer choice = null;

		while(result != null && choice == null && !Game.getSingleton().isTerminating()) {
			NumberFormatException ex = null;
			try {
				choice = Integer.valueOf(result);
			} catch (final NumberFormatException e) {
				ex = e;
			} finally {
				if(ex != null || choice < 1 || choice > action.getActionsList().size()) {
					choice = null;
					ui.showMessage("Invalid input, please type again.");
					result = ui.askForInput(sb.toString());
				}
			}
		}
		
		action.setInputAsInteger(choice);
	}
}

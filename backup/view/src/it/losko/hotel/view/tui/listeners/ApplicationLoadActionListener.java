package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.info.Info;
import it.losko.hotel.view.tui.InputObserver;
import it.losko.hotel.view.tui.TUI;
import it.losko.hotel.view.tui.Utilities;

public class ApplicationLoadActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final String summary = buildSummary();
		ui.showMessage(summary);
		
		final String commands = buildCommands();
		ui.showMessage(commands + "\n");
	}
	
	private String buildSummary() {
		final StringBuffer summary = new StringBuffer();
		summary.append(Utilities.getSingleton().fillLine(Info.getSingleton().getName() + " ", " ", "#"));
		summary.append("\n" + Utilities.getSingleton().fillLine("Author: " + Info.getSingleton().getAuthor(), " "));
		summary.append("\n" + Utilities.getSingleton().fillLine("Version: " + Info.getSingleton().getVersion(), " "));
		return summary.toString();
	}
	
	private String buildCommands() {
		final StringBuffer commands = new StringBuffer();
		commands.append(Utilities.getSingleton().fillLine("Commands: ", " ", "#"));
		commands.append("\n" + Utilities.getSingleton().fillLine("Load: " + InputObserver.CMD_LOAD + "     Save: " + InputObserver.CMD_SAVE, " "));
		commands.append("\n" + Utilities.getSingleton().fillLine("Pause: " + InputObserver.CMD_PAUSE + "   Resume: " + InputObserver.CMD_RESUME, " "));
		commands.append("\n" + Utilities.getSingleton().fillLine("End: " + InputObserver.CMD_END + "       Quit: " + InputObserver.CMD_QUIT, " "));
		commands.append("\n" + Utilities.getSingleton().fillLine("#", "#"));
		
		return commands.toString();
	}
}
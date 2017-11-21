package it.losko.hotel.view.tui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.TurnStartAction;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.view.tui.TUI;
import it.losko.hotel.view.tui.Utilities;

import java.util.List;

public class TurnStartActionListener extends TUIActionListener {

	@Override
	public void actionNotified(final TUI ui, final Action uncastedAction) {
		final TurnStartAction action = (TurnStartAction) uncastedAction;
		
		final String nextSummary = buildPlayerSummary(action.getCurrent());
		ui.showMessage("\n" + nextSummary);
	}
	
	private String buildPlayerSummary(final Player player) {
		final StringBuffer summary = new StringBuffer();
		summary.append(Utilities.getSingleton().fillLine(player.getName() + "'s turn ", " ", "#"));
		summary.append("\n" + Utilities.getSingleton().fillLine("Position: " + player.getPositionOnBoard() + "   Money: " + player.getAvailableMoney(), " "));
		summary.append("\n" + Utilities.getSingleton().fillLine("Hotels: " + getHotels(player), " "));
		summary.append("\n" + Utilities.getSingleton().fillLine("Entrances: " + getEntrances(player), " "));
		summary.append("\n" + Utilities.getSingleton().fillLine("#", "#"));
		
		return summary.toString();
	}
	
	private String getHotels(final Player player) {
		final StringBuffer sb = new StringBuffer();
		
		for(final Hotel hotel : player.getOwnedHotels()) {
			final String lastBuilding = hotel.isAtLeastOneBuildingBuilt() ? hotel.getLastBuiltBuilding().getName() : "Land";
			sb.append(hotel.getName() + " (" + lastBuilding + ")" + ", ");
		}
		if(sb.length() > 0)
			sb.replace(sb.lastIndexOf(", "), sb.length(), "");
		else
			sb.append("None");
		
		return sb.toString();
	}
	
	private String getEntrances(final Player player) {
		final StringBuffer sb = new StringBuffer();
		
		for(final Hotel hotel : player.getOwnedHotels()) {
			final StringBuffer sben = new StringBuffer();
			final List<Square> squares = hotel.getAdjacentSquaresWithABuiltEntrance();
			if(squares != null && squares.size() > 0) {
				for(final Square square : hotel.getAdjacentSquaresWithABuiltEntrance()) {
					sben.append(Game.getSingleton().getBoard().getSquarePosition(square) + ", ");
				}
				if(sben.length() > 0)
					sben.replace(sben.lastIndexOf(", "), sben.length(), "");
				
				sb.append(hotel.getName() + ": " + sben + "; ");
			}
		}
		if(sb.length() > 0)
			sb.replace(sb.lastIndexOf("; "), sb.length(), "");
		else
			sb.append("None");
		
		return sb.toString();
	}
}

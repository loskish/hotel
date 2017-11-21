package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Entrance;
import it.losko.hotel.model.board.FreeEntranceSquare;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.exception.BuildMultipleEntrancesOnSquareException;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

/**
 * An entrance has been built for free. This happens
 * when a player lands on a {@link FreeEntranceSquare}
 * 
 * @author losko
 */
public class BuildEntranceForFreeAction extends BuildEntranceAction {
	private static final long serialVersionUID = 8265598094375887838L;

	public BuildEntranceForFreeAction(final Player doingPlayer, final Hotel hotel, final Square square) {
		super(doingPlayer, hotel, square);
	}

	@Override
	public void doAction() throws HotelException {
		if(getSquare().hasEntrance()) {
			throw new BuildMultipleEntrancesOnSquareException("Cannot build more than one entrance on square");
		}
		
		getSquare().setEntrance(new Entrance(getHotel(), true));
		
		getDoingPlayer().setHasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs(true);
	}
	
	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " builds an entrance for free for " + getHotel().getName() + " at square " + Game.getSingleton().getBoard().getSquares().indexOf(getSquare());
	}
	
	@Override
	public String getChoiceDescription() {
		return "Build an entrance to " + getHotel().getName() + " at square " + Game.getSingleton().getBoard().getSquares().indexOf(getSquare()) + " for free";
	}
}

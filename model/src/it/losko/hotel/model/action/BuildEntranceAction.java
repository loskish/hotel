package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Entrance;
import it.losko.hotel.model.board.FreeEntranceSquare;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Municipe;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.exception.BuildMultipleEntrancesOnSquareException;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

/**
 * An entrance is being built. This happens
 * when a player lands on a {@link FreeEntranceSquare}
 * or steps through the {@link Municipe}
 * 
 * @author losko
 */
public class BuildEntranceAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = -4628050677034429630L;
	
	private final Square square;

	public BuildEntranceAction(final Player doingPlayer, final Hotel hotel, final Square square) {
		super(doingPlayer, hotel);
		this.square = square;
	}

	/**
	 * Returns the square on whitch the entrance is being built
	 * 
	 * @return the square on whitch the entrance is being built
	 */
	public Square getSquare() {
		return square;
	}

	@Override
	public void doAction() throws HotelException {
		if(square.hasEntrance()) {
			throw new BuildMultipleEntrancesOnSquareException("Cannot build more than one entrance on square");
		}
		
		square.setEntrance(new Entrance(getHotel()));
		
		getDoingPlayer().setHasAlreadyMadeABuildEntranceActionForThisHotelOnTheSquareHeIs(getHotel());
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " builds an entrance to " + getHotel().getName() + " at square " + Game.getSingleton().getBoard().getSquares().indexOf(square) + " for " + getHotel().getEntrancePrice();
	}

	@Override
	public String getChoiceDescription() {
		return "Build an entrance to " + getHotel().getName() + " at square " + Game.getSingleton().getBoard().getSquares().indexOf(square) + " for " + getHotel().getEntrancePrice();
	}
}
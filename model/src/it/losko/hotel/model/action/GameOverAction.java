package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Bank;

/**
 * The game ends. This happens when the {@link Bank}
 * terminates it's money fund or when there are no
 * more enough players in the game
 * 
 * @author losko
 */
public class GameOverAction extends Action {
	private static final long serialVersionUID = 4075998173778683136L;

	public GameOverAction() {
		super();
	}

	@Override
	protected void doAction() {
	}
	
	@Override
	public String getNotifiableDescription() {
		return "Game over";
	}
}

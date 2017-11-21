package it.losko.hotel.view;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;

public class Main {
	
	/**
	 * Sample main method for testing purposes (maybe not only?!)
	 * 
	 * @param args	Main parameters
	 */
	public static void main(final String[] args) throws HotelException {
		final Game game = Game.getSingleton();
		
		// Tells the game who is the observer to keep informed
		game.setActionObserver(new ActionObserver());
		
		// Starts the game
		game.start();
	}
}

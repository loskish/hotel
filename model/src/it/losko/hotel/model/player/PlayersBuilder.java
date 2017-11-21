package it.losko.hotel.model.player;

import it.losko.hotel.model.exception.PlayerColorAlreadyChosenException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayersBuilder implements Serializable {
	private static final long serialVersionUID = 3971421476990227188L;

	private final List<Player> players;
	
	public PlayersBuilder() {
		players = new ArrayList<Player>();
	}
	
	protected List<Player> getPlayers() {
		return players;
	}
	
	public void add(final String name, final String color, final Boolean isArtificialIntelligence) throws PlayerColorAlreadyChosenException {
		for(final Player player : players) {
			if(color != null && color.equals(player.getColor())) {
				throw new PlayerColorAlreadyChosenException(player.getName() + " has already chosen the " + color + " color"); 
			}
		}
		
		players.add(new Player(name, color, isArtificialIntelligence));
	}
}

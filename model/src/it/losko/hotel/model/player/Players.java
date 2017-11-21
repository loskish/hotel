package it.losko.hotel.model.player;

import it.losko.hotel.model.action.CollectPlayersAction;
import it.losko.hotel.model.action.JoinAction;
import it.losko.hotel.model.action.TurnEndAction;
import it.losko.hotel.model.action.TurnStartAction;
import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.exception.ExceptionHandler;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.PlayerAlreadyInTheGameException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Players implements Serializable {
	private static final long serialVersionUID = 6392048184981703248L;

	private final List<Player> players;
	private final List<Player> bankruptcyPlayers;
	private int currentPlayerIndex;
	
	public Players() {
		players = new ArrayList<Player>();
		bankruptcyPlayers = new LinkedList<Player>();
		currentPlayerIndex = -1;
	}
	
	/**
	 * Returns the list of players involved in the game
	 * 
	 * @return the list of players involved in the game
	 */
	public List<Player> getAll() {
		return players;
	}
	
	/**
	 * Returns the player identified by the specified index
	 * 
	 * @return the player identified by the specified index
	 */
	public Player get(final int index) {
		return players.get(index);
	}
	
	/**
	 * Adds a player to the game
	 * 
	 * @param player The player to add
	 */
	public void add(final Player player) throws HotelException {
		if(getAll().contains(player)) {
			throw new PlayerAlreadyInTheGameException(player.getName() + "is already playing");
		}
		
		getAll().add(player);
		
		(new JoinAction(player)).executeAction();
	}
	
	/**
	 * Removes a player from the game
	 * 
	 * @param player Player to remove
	 */
	public void remove(final Player player) throws HotelException {
		if(!getAll().contains(player)) {
			throw new PlayerNotInTheGameException(player.getName() + "is not playing");
		}
		
		if(getAll().indexOf(player) <= getCurrentPlayerIndex()) {
			getAll().remove(player);
			previous();
		} else {
			getAll().remove(player);
		}
		
		((LinkedList<Player>) bankruptcyPlayers).addFirst(player);
		
		if(getAll().size() <= 1 && !Game.getSingleton().isTerminating()) {
			Game.getSingleton().end();
		}
	}
	
	/**
	 * Returns the next player and increments the index of the current player
	 * 
	 * @return the next player
	 */
	public Player next() throws PlayerNotInTheGameException {
		setCurrentPlayerIndex(getNextPlayerIndex());
		initializeCurrentPlayerFlags();
		
		return current();
	}
	
	/**
	 * Returns the previous player and decrements the index of the current player
	 * 
	 * @return the previous player
	 */
	public Player previous() throws PlayerNotInTheGameException {
		setCurrentPlayerIndex(getPreviousPlayerIndex());
		
		return current();
	}
	
	/**
	 * Returns the current player
	 * 
	 * @return the current player
	 */
	public Player current() throws PlayerNotInTheGameException {
		if(getAll().isEmpty()) {
			throw new PlayerNotInTheGameException("None is playing now");
		}
		
		return getAll().get(getCurrentPlayerIndex());
	}
	
	/**
	 * Collects player willing to participate in the game
	 */
	public void collect() throws HotelException {
		clear();
		
		final CollectPlayersAction cpa = new CollectPlayersAction();
		cpa.executeAction();
		
		if(cpa.getInputAsPlayersBuilder() != null) {
			final PlayersBuilder playersBuilder = cpa.getInputAsPlayersBuilder();
			final Integer money = playersBuilder.getPlayers().size() <= getMinimumNumber() ? 25000 : 12000;
			for(final Player player : playersBuilder.getPlayers()) {
				player.setAvailableMoney(money);
				add(player);
			}
		}
	}
	
	public void clear() {
		players.clear();
		bankruptcyPlayers.clear();
	}
	
	/**
	 * Tells the players to play
	 */
	public void play() throws HotelException {
		final Player player = next();
		
		// Turn doesn't change when game is over
		if(!Game.getSingleton().isTerminating()) {
			new TurnStartAction(player, getCurrentPlayerIndex() == -1 ? null: get(getPreviousPlayerIndex())).executeAction();
		}
		
		try {
			player.play();
		} catch (Exception e) {
			// This is the more general exception catch-point.
			// Should an exception occurr INSIDE Player.play() method,
			// user will be notified and prompted for a choice. Otherwise,
			// should an exception occurr OUTSIDE will be immediately fatal.
			ExceptionHandler.getSingleton().handle(e);
		}
		
		// Turn doesn't change when game is over
		if(!Game.getSingleton().isTerminating()) {
			new TurnEndAction(player, get(getNextPlayerIndex())).executeAction();
		}
		
		if(!Game.getSingleton().isTerminating()) {
			// Handles turn's delay
			try {
				Thread.sleep(Config.getSingleton().getParameterValueAsLong(ConfigConstants.PARAM_TURN_DELAY));
			} catch (final InterruptedException e) {
				// Ignored
			}
		}
	}
	
	/**
	 * Returns the list of player ordered by player rank
	 * Order is by greater to lower patrimony.
	 * 
	 * @return the list of player ordered by player rank
	 */
	public List<Player> rank() {
		final List<Player> players = new ArrayList<Player>(getAll());
		
		Collections.sort(players, new Comparator<Player>() {
			public int compare(final Player player1, final Player player2) {
				final Double patrimony1 = player1.getPatrimony();
				final Double patrimony2 = player2.getPatrimony();
				
				if(patrimony2.compareTo(patrimony1) != 0)
					return patrimony2.compareTo(patrimony1);
				
				return player1.getName().compareToIgnoreCase(player2.getName());
			};
		});
		
		players.addAll(bankruptcyPlayers);
		
		return players;
	}
	
	/**
	 * Returns the defined minimum number of players
	 * 
	 * @return The defined minimum number of players
	 */
	public int getMinimumNumber() {
		return 2;
	}
	
	/**
	 * Returns the defined maximum number of players
	 * 
	 * @return The defined maximum number of players
	 */
	public int getMaximumNumber() {
		return 4;
	}
	
	/**
	 * Returns the defined default number of players
	 * 
	 * @return The defined default number of players
	 */
	public int getDefaultNumber() {
		return 4;
	}
	
	private int getPreviousPlayerIndex() {
		return getCurrentPlayerIndex() == 0 ? getAll().size() - 1 : getCurrentPlayerIndex() - 1;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	
	private int getNextPlayerIndex() {
		return getCurrentPlayerIndex() == getAll().size() - 1 ? 0 : getCurrentPlayerIndex() + 1;
	}
	
	private void setCurrentPlayerIndex(final int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
	private void initializeCurrentPlayerFlags() throws PlayerNotInTheGameException {
		current().setHasRolledDice(false);
		current().setMustRollDice(true);
		current().setHasAlreadyMadeABuildActionOnTheSquareHeIs(false);
		current().setHasAlreadyMadeAPurchaseActionOnTheSquareHeIs(false);
		current().setHasAlreadyMadeAnExpropriateActionOnTheSquareHeIs(false);
		current().setHasRefusedAnymoreActionsOnTheSquareHeIs(false);
		current().setHasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs(false);
		current().resetHotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs();
		current().setHasAlreadyMadeAPayForStayActionOnTheSquareHeIs(false);
	}
}

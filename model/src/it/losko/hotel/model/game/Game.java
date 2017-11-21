package it.losko.hotel.model.game;

import it.losko.hotel.model.action.ApplicationLoadAction;
import it.losko.hotel.model.action.ApplicationUnloadAction;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.GameLoadAction;
import it.losko.hotel.model.action.GameOverAction;
import it.losko.hotel.model.action.GameSaveAction;
import it.losko.hotel.model.action.GameStartAction;
import it.losko.hotel.model.action.InitialMenuAction;
import it.losko.hotel.model.action.SelectFileNameAction;
import it.losko.hotel.model.board.Board;
import it.losko.hotel.model.exception.ExceptionHandler;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.history.History;
import it.losko.hotel.model.player.Players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Observer;

/**
 * Class defining the game main interface.
 * It mainly allows to start and stop the game
 * 
 * @author losko
 */
public class Game implements Serializable, GameConstants {
	private static final long serialVersionUID = 6103404983157084450L;

	// Serializable fields
	private Players players;
	private Board board;
	private History history;
	
	// Transient non-final fields
	transient private boolean isTerminating = false;
	transient private boolean isPaused = false;
	transient private boolean isQuitting = false;
	transient private Thread gameThread;
	transient private Observer actionObserver;
	
	// Transient final fields
	transient private final Thread mainThread;
	transient private final SynchronizationMonitor pauseSynchronizationMonitor;
	
	private Game() {
		players = new Players();
		board = new Board();
		history = new History();
		mainThread = Thread.currentThread();
		mainThread.setName("MainThread");
		pauseSynchronizationMonitor = new SynchronizationMonitor();
	}
	
	/**
	 * This inner-class holds the singleton instance
	 * This allows to implement the Singleton pattern
	 * without use of special constructs like synchronized
	 * 
	 * @author losko
	 */
	private static class SingletonHolder {
		private static final Game singleton = new Game();
	}

	/**
	 * Returns a unique instance of the class
	 * 
	 * @return a unique instance of the class
	 */
	public static Game getSingleton() {
		return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Creates a new game
	 */
	public void create() throws HotelException {
		// Reasons:
		// 1. A game could be currently running
		end();
		//
		
		// Reasons:
		// 1. The previous end() call resets these values
		// 2. The next actions need these values to execute
		setTerminating(false);
		setPaused(false);
		//
		
		// Instance variable initialization
		// Reasons:
		// 1. Goes before any action because action must only start
		// 	when the game initialization is complete
		board = new Board();
		history = new History();
		//

		// Players are collected
		players.collect();
		
		// Players could have been not collected if the user
		// decides to cancel the player collection phase. In
		// this case the game doesn't start.
		if(!players.getAll().isEmpty()) {
			// Game start notification
			final GameStartAction startGameAction = new GameStartAction();
			startGameAction.executeAction();

			// The game runs in a separated thread, called the "GameThread".
			// Reasons:
			// 1. The "GameThread" is only intended to run a game session.
			// 2. The game session must be controllable by another thread
			// 	that will typically be the user-interface thread.
			// 3. Once a game session terminates the program will not end
			//	giving the opportunity to start a new game session
			gameThread = new Thread(new GameSession());
			gameThread.start();
			//
		}
	}
	
	/**
	 * Starts the game
	 */
	public void start() throws HotelException {
		// Notifies the application load
		final ApplicationLoadAction applicationLoadAction = new ApplicationLoadAction();
		applicationLoadAction.executeAction();
		
		// Prepares the initial choice request that is the basic function the user wants to do
		// Example: Create a new game session, load an existing game, etc...
		final Choosable[] choosableActions = {
				new GameStartAction(), 
				new GameLoadAction(), 
				new ApplicationUnloadAction()
		};
		
		// Continues asking for the initial choice between each game session
		// and until the user decides to quit, that is the application unload.
		while(!isQuitting()) {
			final InitialMenuAction askForInputAction = new InitialMenuAction(Arrays.asList(choosableActions));
			askForInputAction.executeAction();

			// Retrieves the choice of the user and executes the corresponding action
			final Integer choice = askForInputAction.getInputAsInteger();
			if(choice != null) {
				switch(choice) {
				case 1:
					// Creates a new game and runs it in the "GameThread"
					create();
					break;
				case 2:
					// Loads an existing game and runs it in the "GameThread"
					load();
					break;
				case 3:
					// Quits the game, that is application unload
					quit();
					return;
				}
			}

			// Waits for the game thread to terminate, if any
			// Reasons:
			// 1. Requires any active game to terminate before requesting a new initial choice
			if(!isTerminating() && gameThread != null) {
				try {
					gameThread.join();
				} catch (final InterruptedException e) {
					// Ignored
				}
			}
			//
			
			// Reasons:
			// 1. This flag has been reset during the "GameThread" termination
			// 2. This flag is needed to be so set to request a new initial choice
			setTerminating(false);
			//
		}
	}
	
	/**
	 * Terminates the game
	 */
	public void end() {
		// Reasons:
		// 1. Tells the running game to return as soon as possible
		// 2. Goes BEFORE gameThread.interrupt() as any waiting operation once interrupted
		// 	must know to return as soon as possible
		// 3. Goes OUTSIDE the following if construct because these values are useful not
		// 	only to terminate a game session, but for quitting too.
		setTerminating(true);
		setPaused(false);
		//
		
		// Reasons:
		// 1. The game is running only if the gameThread is alive
		if(gameThread != null && gameThread.isAlive()) {
			// Reasons:
			// 1. Wakes up any inputAction waiting for input
			// 2. Goes AFTER flags initialization as any waiting operation once interrupted
			// 	must know to return as soon as possible
			gameThread.interrupt();
			//
		}
	}
	
	/**
	 * Pauses the game
	 */
	public void pause() {
		setPaused(true);
	}
	
	/**
	 * Resumes the game
	 */
	public void resume() {
		synchronized (getPauseSynchronizationMonitor()) {
			setPaused(false);
			getPauseSynchronizationMonitor().notify();
		}
	}
	
	/**
	 * Quits the game
	 */
	public void quit() throws HotelException {
		// Reasons
		// 1. Indicates that the application needs to shutdown
		// 2. Goes before the end() call, as when the game will return it already needs to know to quit
		setQuitting(true);

		// Terminates any existing game
		end();
		
		// Notifies the application unloading. This action doesn't need any game flag
		final ApplicationUnloadAction applicationUnloadAction = new ApplicationUnloadAction(); 
		applicationUnloadAction.executeAction();
		
		// Reasons:
		// 1. The application is running only if the mainThread is alive
		if(mainThread != null && mainThread.isAlive()) {
			// Reasons:
			// 1. Not studied enough. Added only because the mainThread was hanging
			//  if the application was closed by the file/quit menu.
			mainThread.interrupt();
		}
	}
	
	/**
	 * Saves the game
	 */
	public void save() throws HotelException, IOException {
		final SelectFileNameAction sfna = new SelectFileNameAction();
		sfna.executeAction();
		
		save(sfna.getInputAsString());
	}
	
	/**
	 * Saves the gave in the given filename
	 * 
	 * @param fileName			Where to save the game
	 */
	public void save(String fileName) throws IOException, HotelException {
		// Fixes the fileName given in input
		fileName = fixFileName(fileName);
		
		if(fileName != null) {
			// Pauses the current game being unloaded
			pause();
			
			final File dir = new File(SAVEGAMES_FOLDER);
			if(!dir.isDirectory()) {
				if(!dir.mkdir()) {
					throw new IOException("Cannot create savegames folder: " + SAVEGAMES_FOLDER);
				}
			}
			
			final File file = new File(SAVEGAMES_FOLDER + "/" + fileName);
			final FileOutputStream fs = new FileOutputStream(file);
			final ObjectOutputStream os = new ObjectOutputStream(fs);
			
			// Serializes the object
			os.writeObject(this);
			
			// Closes the stream
			os.close();
			fs.close();
			
			// At this point the old game instance has been transformed
			// in the loaded one. Resumes the game on the loaded instance
			resume();
			
			final GameSaveAction gameSaveAction = new GameSaveAction();
			gameSaveAction.executeAction();
		}
	}
	
	/**
	 * Loads the game
	 */
	public void load() throws HotelException {
		final SelectFileNameAction sfna = new SelectFileNameAction();
		sfna.executeAction();
		
		load(sfna.getInputAsString());
	}
	
	/**
	 * Loads the game from the given filename
	 * 
	 * @param fileName			Where to read the game to load
	 */
	public void load(String fileName) throws HotelException {
		// Fixes the fileName given in input
		fileName = fixFileName(fileName);
		
		if(fileName != null) {
			// Ends the game, if active
			end();
			
			Game loaded;
			
			try {
				final File file = new File(SAVEGAMES_FOLDER + "/" + fileName);
				final FileInputStream fs = new FileInputStream(file);
				final ObjectInputStream os = new ObjectInputStream(fs);
				
				// De-Serializes the object
				loaded = (Game) os.readObject();
				
				// Closes the stream
				os.close();
				fs.close();
				
			} catch (final Exception e) {
				ExceptionHandler.getSingleton().handle(e);
				return;
			}
			
			// Overwrites the values of instance variables
			players = loaded.players;
			board = loaded.board;
			history = loaded.history;
			
			// Notifies that a new game has been loaded
			final GameLoadAction gameloadAction = new GameLoadAction();
			gameloadAction.executeAction();
			
			// Frees the memory occupied by old game instance variable
			loaded = null;
			
			// Reasons:
			// 1. The previous end() call resets these values
			// 2. The next actions need these values to execute
			setTerminating(false);
			setPaused(false);
			
			gameThread = new Thread(new GameSession());
			gameThread.start();
		}
	}

	/**
	 * Starts effectively a game session
	 */
	public void play() throws HotelException {
		while(!isTerminating()) {
			players.play();
		}
		
		// Reasons:
		// 1. Memory consumption
		gameThread = null;
		
		// At this point the game is really terminated, so notifies the event
		// This action does not need the game flags to be modified
		final GameOverAction gameOverAction = new GameOverAction(); 
		try {
			gameOverAction.executeAction();
		} catch (HotelException e) {
			ExceptionHandler.getSingleton().handle(e);
		}
	}
	
	/**
	 * Indicates whether the game is terminating or not
	 * 
	 * @return whether the game is terminating or not
	 */
	public boolean isTerminating() {
		return isTerminating;
	}
	
	/**
	 * Indicates whether the game is paused or not
	 * 
	 * @return whether the game is paused or not
	 */
	public boolean isPaused() {
		return isPaused;
	}
	
	/**
	 * Indicates whether the game is quitting or not
	 * 
	 * @return whether the game is quitting or not
	 */
	public boolean isQuitting() {
		return isQuitting;
	}

	/**
	 * Sets whether the game is terminating or not
	 * 
	 * @param value The value to set
	 */
	private void setTerminating(final boolean value) {
		isTerminating = value;
	}
	
	/**
	 * Sets whether the game is paused or not
	 * without waking up any sleeping thread.
	 * <br/>
	 * To wake up any sleeping thread please
	 * use the {@link #resume()} method.
	 * 
	 * @param value The value to set
	 */
	private void setPaused(final boolean value) {
		isPaused = value;
	}
	
	/**
	 * Sets whether the game is quitting or not
	 * 
	 * @param value The value to set
	 */
	private void setQuitting(final boolean value) {
		isQuitting = value;
	}
	
	/**
	 * Returns the players involved in the game
	 * 
	 * @return the players involved in the game
	 */
	public Players getPlayers() {
		return players;
	}
	
	/**
	 * Returns the game's board
	 * 
	 * @return the game's board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the game's history
	 * 
	 * @return the game's history
	 */
	public History getHistory() {
		return history;
	}
	
	/**
	 * Returns the actions' observer
	 * 
	 * @return the actions' observer
	 */
	public Observer getActionObserver() {
		return actionObserver;
	}
	
	/**
	 * Sets the actions' observer
	 * 
	 * @param actionObserver the actions' observer
	 */
	public void setActionObserver(final Observer actionObserver) {
		this.actionObserver = actionObserver;
	}
	
	/**
	 * Returns the synchronization Monitor that is the semaphore for handling pauses
	 * 
	 * @return the synchronization Monitor
	 */
	public SynchronizationMonitor getPauseSynchronizationMonitor() {
		return pauseSynchronizationMonitor;
	}

	private String fixFileName(String fileName) {
		if(fileName != null) {
			if(!fileName.endsWith(SAVEGAMES_EXTENSION)) {
				fileName = fileName.concat(SAVEGAMES_EXTENSION);
			}
			
			fileName = fileName.trim();
		}
		
		return fileName;
	}
	
	/**
	 * The class that allows to run the game thread
	 * 
	 * @author losko
	 */
	private class GameSession implements Runnable {
		public void run() {
			try {
				Thread.currentThread().setName("GameThread");
				play();
			} catch (final HotelException e) {
				ExceptionHandler.getSingleton().handle(e);
			}
		}
	}
}

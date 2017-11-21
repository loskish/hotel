package it.losko.hotel.model.action;

import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.log.Log;

import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;

/**
 * Action class is the abstract representation
 * of some operation that has to be executed.
 * Action class is also the unique communication
 * bus between the model and the view. The view
 * is notified every time that an action is made
 * 
 * @author losko
 */
public abstract class Action extends Observable implements Serializable {
	private static final long serialVersionUID = 1444804577436785000L;
	
	private boolean ignoresPauses = false;

	/**
	 * Constructs a new Action object
	 */
	protected Action() {
		addObserver(Game.getSingleton().getActionObserver());
	}
	
	/**
	 * Musts be implemented in subclasses. Returns the description of the implementing action
	 * meant as the description that will be notified to observers once it has been executed
	 * 
	 * @return The description that will be notified to observers once it has been executed
	 */
	public abstract String getNotifiableDescription();
	
	/**
	 * Musts be implemented in subclasses. Defines the operations that will be performed
	 * 
	 * @throws HotelException Added to the method sign for extensions 
	 */
	protected abstract void doAction() throws HotelException;
	
	/**
	 * Public entry-point to execution of actions. Execution is made in three steps:
	 * <ul>
	 * 	<li>Execution of preliminary operations</li>
	 * 	<li>Execution of the action</li>
	 * 	<li>Execution of terminal operations</li>
	 * </ul>
	 * 
	 * @throws HotelException Added to the method sign for extensions
	 */
	public final void executeAction() throws HotelException {
		internalPreDoAction();
		doAction();
		internalPostDoAction();
	}
	
	/**
	 * Defines the preliminary operations that will be performed before the execution of the action
	 * These operations are mandatory, so they're hard-coded and cannot be skipped
	 * 
	 * @throws HotelException Added to the method sign for extensions 
	 */
	private final void internalPreDoAction() throws HotelException {
		try {
			Thread.sleep(Config.getSingleton().getParameterValueAsLong(ConfigConstants.PARAM_ACTION_DELAY));
		} catch (final InterruptedException e) {
			// Ignored
		}
		
		// Handles game's pauses
		synchronized (Game.getSingleton().getPauseSynchronizationMonitor()) {
			while (Game.getSingleton().isPaused() && !Game.getSingleton().isTerminating() && !ignoresPauses()) {
				try {
					Game.getSingleton().getPauseSynchronizationMonitor().wait();
				} catch (final Exception e) {
					// Ignored
				}
			}
		}
		
		preDoAction();
	}
	
	/**
	 * Defines the operations that will be performed after the execution of the action
	 * These operations are mandatory, so they're hard-coded and cannot be skipped
	 * 
	 * @throws HotelException Added to the method sign for extensions 
	 */
	private final void internalPostDoAction() throws HotelException {
		Log.getSingleton().getActionLogger().logp(Level.FINEST, getClass().getName(), "internalPostDoAction", getNotifiableDescription());
		
		// Pushes the action on history's stack
		Game.getSingleton().getHistory().push(this);
		
		// Notifies observers
		setChanged();
		notifyObservers();
		
		postDoAction();
	}
	
	/**
	 * To be eventually implemented in subclasses. Defines any additional operation that will
	 * be performed before the execution of the action
	 * 
	 * @throws HotelException Added to the method sign for extensions 
	 */
	protected void preDoAction() throws HotelException {
	}
	
	/**
	 * To be eventually implemented in subclasses. Defines any additional operation that will
	 * be performed after the execution of the action.
	 * 
	 * @throws HotelException Added to the method sign for extensions 
	 */
	protected void postDoAction() throws HotelException {
	}
	
	/**
	 * Allows to specify whether the action should stop when the game is paused
	 * 
	 * @param ignoresPauses true if the action should stop in game is paused
	 */
	public void setIgnoresPauses(final boolean ignoresPauses) {
		this.ignoresPauses = ignoresPauses;
	}

	/**
	 * Indicates whether the action will stop if the game is paused
	 * 
	 * @return true if the action will stop if the game is paused, false otherwise
	 */
	protected boolean ignoresPauses() {
		return ignoresPauses;
	}
}

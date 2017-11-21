package it.losko.hotel.view;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.ApplicationUnloadAction;
import it.losko.hotel.model.action.GameOverAction;
import it.losko.hotel.model.action.InputAction;
import it.losko.hotel.model.action.PlayerActionSelectionAction;
import it.losko.hotel.model.ai.ArtificialIntelligence;
import it.losko.hotel.model.exception.ExceptionHandler;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.log.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * The observer of the actions. This class
 * is notified every time that an action is done
 * 
 * @author losko
 */
public class ActionObserver implements Observer {
	private final UserInterface userInterface;
	private final ExecutorService executorService;
	private final ExecutorService cancelExecutorService;
	
	public ActionObserver() {
		userInterface = UserInterface.getNewInstance();
		executorService = Executors.newSingleThreadExecutor();
		cancelExecutorService = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * Receives the notifications every time an
	 * action has been done and informs in turn
	 * the user interface class
	 * 
	 * @param   o     the observable object
     * @param   arg   the argument passed to the <code>notifyObservers</code> method invoked on the Observed object
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		if(o instanceof Action) {
			
			final Action action = ((Action) o);
			// If the application is unloading terminates
			// the Thread Pool Executor used for actions.
			if(action instanceof ApplicationUnloadAction || action instanceof GameOverAction) {
				cancelExecutorService.execute(new Canceler(action));
			} else {
				// Notifies the action in dedicated thread
				executorService.execute(new Notifier(action));
			}
		} else {
			throw new RuntimeException("Non Action Objects can't be notified to ActionObserver");
		}
	}
	
	private void internalUpdate(final Action action) {
		// If user requested for input is an artificial
		// intelligence then the AI module is prompted
		if(action instanceof PlayerActionSelectionAction && ((PlayerActionSelectionAction) action).getDoingPlayer().isArtificialIntelligence()) {
			final PlayerActionSelectionAction inputRequest = (PlayerActionSelectionAction) action;
			Integer choice;
			try {
				choice = ArtificialIntelligence.getSingleton().chooseAction(inputRequest.getActionsList());
			} catch (final NoMoreBuildingsToBuildException e) {
				throw new RuntimeException(e);
			}
			inputRequest.setInputAsInteger(choice);

			return;
		}

		// Notifies action to listeners
		final String listenerName = action.getClass().getSimpleName() + "Listener";
		final String listenerCompleteName = userInterface.getClass().getPackage().getName() + ".listeners." + listenerName;
		try {
			final Class<?> clazz = Class.forName(listenerCompleteName);
			final ActionListener listener = (ActionListener) clazz.newInstance();
			Log.getSingleton().getListenerLogger().entering(listener.getClass().getName(), "actionNotified");
			listener.actionNotified(userInterface, action);
		} catch (final ClassNotFoundException e) {
			// A listener for an action that requires to collect input has not been found
			// This is a fatal error, so program logs with SEVERE level and terminates
			if(action instanceof InputAction) {
				Log.getSingleton().getListenerLogger().logp(Level.SEVERE, getClass().getName(), "update", listenerName + " must be defined and must call method " + action.getClass().getName() + ".setReponse(Integer response)");
				Game.getSingleton().end();
				return;
			}
			
			// No specific listener has been defined. Logs and defaults to base listener.
			Log.getSingleton().getListenerLogger().logp(Level.FINE, getClass().getName(), "update", "No listener found for action " + action.getClass().getName());
			
			// Creates a new instance of base listener
			final String baseListenerName = userInterface.getClass().getPackage().getName() + ".listeners." + userInterface.getClass().getSimpleName() + "ActionListener";
			try {
				final Class<?> clazz = Class.forName(baseListenerName);
				final ActionListener listener = (ActionListener) clazz.newInstance();
				Log.getSingleton().getListenerLogger().entering(listener.getClass().getName(), "actionNotified");
				listener.actionNotified(userInterface, action);
			} catch (final ClassNotFoundException e1) {
				// The base listener hasn't been found
				// This is a fatal error, so program logs with SEVERE level and terminates
				Log.getSingleton().getListenerLogger().logp(Level.SEVERE, getClass().getName(), "update", baseListenerName + " must be defined");
				Game.getSingleton().end();
			} catch (final Exception e2) {
				ExceptionHandler.getSingleton().handle(e2);
			}
		} catch (final Exception e) {
			ExceptionHandler.getSingleton().handle(e);
		}
	}
	
	/**
	 * Runnable class whose aim is to notify actions in a separate Thread
	 */
	private class Notifier implements Runnable {
		
		final Action action;
		
		public Notifier(final Action action) {
			this.action = action;
		}
		
		@Override
		public void run() {
			internalUpdate(action);
		}
	}
	
	/**
	 * Runnable class whose aim is to cancel existing pending actions in separate Threads
	 */
	private class Canceler implements Runnable {
		
		final Action action;
		
		public Canceler(final Action action) {
			this.action = action;
		}
		
		@Override
		public void run() {
			internalUpdate(action);
			
			if(action instanceof ApplicationUnloadAction) {
				// Terminates Thread Pool Executors used for actions
				executorService.shutdownNow();
				cancelExecutorService.shutdownNow();
			}
		}
	}
}

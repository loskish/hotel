package it.losko.hotel.model.exception;

import it.losko.hotel.model.action.ExceptionAction;
import it.losko.hotel.model.log.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 * This class handles those exception
 * whom cannot be declared by methods.
 * Exceptions are notified to the user interface
 * through an {@link ExceptionAction}.
 * Should the {@link ExceptionAction} itself throw an
 * Exception, then a RuntimeException will be thrown.
 * 
 * @author losko
 */
public class ExceptionHandler {
	private ExceptionHandler() {
	}
	
	/**
	 * This inner-class holds the singleton instance
	 * This allows to implement the Singleton pattern
	 * without use of special constructs like synchronized
	 */
	private static class SingletonHolder { 
		private static final ExceptionHandler singleton = new ExceptionHandler();
	}
	
	/**
	 * Returns a unique instance of the class
	 * 
	 * @return a unique instance of the class
	 */
	public static ExceptionHandler getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Handles the exceptions that cannot be declared
	 * Exceptions are notified to the user interface
	 * through an {@link ExceptionAction}.
	 * Should the {@link ExceptionAction} itself throw an
	 * exception, then a RuntimeException will be thrown.
	 * 
	 * @param e The exception to handle and notify to UI
	 */
	public void handle(final Exception e) {
		final String stackTrace = getStrackTrace(e);
		
		// Protection over recursive exceptions
		if(!stackTrace.contains(getClass().getName())) {
			Log.getSingleton().getExceptionLogger().logp(Level.SEVERE, getClass().getName(), "handle", getStrackTrace(e));
			final ExceptionAction ea = new ExceptionAction(e);
			try {
				ea.executeAction();
			} catch (final HotelException e1) {
				throw new RuntimeException(e1);
			}
		}
	}
	
	/**
	 * Returns the stacktrace of the given exception
	 * as a unique concatenated String
	 * 
	 * @param e	the exception
	 * @return	the stacktrace of the given exception
	 */
	public String getStrackTrace(final Exception e) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		return sw.toString();
	}
}

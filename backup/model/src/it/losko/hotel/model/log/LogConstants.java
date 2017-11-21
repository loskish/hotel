package it.losko.hotel.model.log;

/**
 * Provides all the constants for the logs module
 * 
 * @author losko
 */
public interface LogConstants {
	public final static String LOG_EXTENSION = ".log";
	public final static String LOG_LEVEL_PARAMETER_SUFFIX = "LogLevel";
	
	// Loggers' names
	public final static String ACTION_LOGGER_NAME = "action";
	public final static String LISTENER_LOGGER_NAME = "listener";
	public final static String EXCEPTION_LOGGER_NAME = "exception";
}

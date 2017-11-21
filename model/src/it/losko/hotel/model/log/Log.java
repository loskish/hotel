package it.losko.hotel.model.log;

import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.exception.ExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides access to the defined application loggers.
 * 
 * @author losko
 */
public class Log implements LogConstants {
	final Logger actionLogger;
	final Logger listenerLogger;
	final Logger exceptionLogger;
	
	private Log() {
		actionLogger = createLogger(ACTION_LOGGER_NAME);
		listenerLogger = createLogger(LISTENER_LOGGER_NAME);
		exceptionLogger = createLogger(EXCEPTION_LOGGER_NAME);
	}
	
	private static class SingletonHolder { 
		private static final Log singleton = new Log();
	}
	
	public static Log getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public Logger getActionLogger() {
		return actionLogger;
	}
	
	public Logger getListenerLogger() {
		return listenerLogger;
	}
	
	public Logger getExceptionLogger() {
		return exceptionLogger;
	}
	
	private Logger createLogger(final String loggerName) {
		final Level loggerLevel = Level.parse(Config.getSingleton().getParameterValueAsString(loggerName + LOG_LEVEL_PARAMETER_SUFFIX));
		final Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(loggerLevel);
		if(loggerLevel != Level.OFF) {
			try {
				logger.addHandler(createFileHandler(loggerName));
			} catch (final Exception e) {
				ExceptionHandler.getSingleton().handle(e);
			}
		}
		return logger;
	}
	
	private Handler createFileHandler(final String loggerName) throws SecurityException, IOException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		final String logTimeStamp = dateFormat.format(new Date());
		final String fileName = Config.getSingleton().getParameterValueAsString(ConfigConstants.PARAM_LOG_FOLDER);
		final File file = new File(fileName);
		if(!file.isDirectory()) {
			if(!file.mkdir()) {
				throw new IOException("Cannot create log folder: " + fileName);
			}
		}
		return new FileHandler(fileName + "/" + loggerName + logTimeStamp + LOG_EXTENSION);
	}
}

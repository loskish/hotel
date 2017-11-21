package it.losko.hotel.model.conf;

import it.losko.hotel.model.log.LogConstants;

/**
 * Provides all the constants for the configuration module
 * 
 * @author losko
 */
public interface ConfigConstants {
	// Configuration file
	public static final String CONFIG_FILE_NAME = "conf/conf.xml";
	public static final String XML_ROOTNODE = "conf";
	public static final String XML_PATH_SEPARATOR = "/";
	public static final String XML_DTD = "conf.dtd";
	
	// Names
	public static final String PARAM_NUMBER_OF_LAP_FORECASTING = "numberOfLapForecasting";
	public static final String PARAM_ACTION_DELAY = "actionDelay";
	public static final String PARAM_TURN_DELAY = "turnDelay";
	public static final String PARAM_INPUT_TIMEOUT = "inputTimeout";
	public static final String PARAM_USER_INTERFACE_CLASS_NAME = "userInterfaceClassName";
	public static final String PARAM_LOG_FOLDER = "logFolder";
	public static final String PARAM_ACTION_LOG_LEVEL = LogConstants.ACTION_LOGGER_NAME + LogConstants.LOG_LEVEL_PARAMETER_SUFFIX;
	public static final String PARAM_LISTENER_LOG_LEVEL = LogConstants.LISTENER_LOGGER_NAME + LogConstants.LOG_LEVEL_PARAMETER_SUFFIX;
	public static final String PARAM_EXCEPTION_LOG_LEVEL = LogConstants.EXCEPTION_LOGGER_NAME + LogConstants.LOG_LEVEL_PARAMETER_SUFFIX;
}

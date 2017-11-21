package it.losko.hotel.model.conf;

import it.losko.hotel.model.exception.ExceptionHandler;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the class offering the configuration's API
 * Through the methods of this class it's possible to
 * configure the application
 * 
 * @author losko
 */
public class Config implements ConfigConstants {
	private final Map<String, ConfigParameter> parameterMap;
	
	private final String[][] parameters = new String[][] {
			// Name								// Description																				// Default						
			 {PARAM_NUMBER_OF_LAP_FORECASTING,	"Sets the number of laps to whom the artificial intelligence extimes will be referred to",	"5",							Integer.class.getName()}
			,{PARAM_ACTION_DELAY,				"Sets the time that should elapse between every action (millisecs)",						"500",							Integer.class.getName()}
			,{PARAM_TURN_DELAY, 				"Sets the time that should elapse between every player turn (millisecs)",					"3000",							Integer.class.getName()}
			,{PARAM_INPUT_TIMEOUT, 				"Sets the maximum time to wait for user input (millisecs)",									"60000",						Integer.class.getName()}
			,{PARAM_LOG_FOLDER,		 			"Sets the logs' folder",																	"log",						 	String.class.getName()}
			,{PARAM_ACTION_LOG_LEVEL, 			"Sets logs' level (OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL)",			"OFF",						 	String.class.getName()}
			,{PARAM_LISTENER_LOG_LEVEL, 		"Sets logs' level (OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL)",			"OFF",						 	String.class.getName()}
			,{PARAM_EXCEPTION_LOG_LEVEL, 		"Sets logs' level (OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL)",			"OFF",						 	String.class.getName()}
			,{PARAM_USER_INTERFACE_CLASS_NAME, 	"Sets the name of the user interface's class", 												"it.losko.hotel.view.gui.GUI", 	String.class.getName()}
	};
	
	private Config() {
		parameterMap = new LinkedHashMap<String, ConfigParameter>();
		
		try {
			load();
		} catch (final InvalidParameterException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * This inner-class holds the singleton instance
	 * This allows to implement the Singleton pattern
	 * without use of special constructs like synchronized
	 */
	private static class SingletonHolder { 
		private static final Config singleton = new Config();
	}
	
	/**
	 * Returns a unique instance of the class
	 * 
	 * @return a unique instance of the class
	 */
	public static Config getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Returns all the configuration's parameters
	 * 
	 * @return all the configuration's parameters
	 */
	public ConfigParameter[] getParameters() {
		return parameterMap.values().toArray(new ConfigParameter[0]);
	}
	
	/**
	 * Returns the specified parameter
	 * 
	 * @param name The parameter's name
	 * @return the specified parameter
	 */
	public ConfigParameter getParameter(final String name) {
		return parameterMap.get(name);
	}
	
	/**
	 * Returns the value of specified parameter expressed as a String
	 * 
	 * @param name The parameter's name
	 * @return the value of specified parameter expressed as a String
	 */
	public String getParameterValueAsString(final String name) {
		return parameterMap.get(name).getValue();
	}
	
	/**
	 * Returns the value of specified parameter expressed as a Boolean
	 * 
	 * @param name The parameter's name
	 * @return the value of specified parameter expressed as a Boolean
	 */
	public Boolean getParameterValueAsBoolean(final String name) {
		return Boolean.valueOf(parameterMap.get(name).getValue());
	}
	
	/**
	 * Returns the value of specified parameter expressed as a Integer
	 * 
	 * @param name The parameter's name
	 * @return the value of specified parameter expressed as a Integer
	 */
	public Integer getParameterValueAsInteger(final String name) {
		return Integer.valueOf(parameterMap.get(name).getValue());
	}
	
	/**
	 * Returns the value of specified parameter expressed as a Long
	 * 
	 * @param name The parameter's name
	 * @return the value of specified parameter expressed as a Long
	 */
	public Long getParameterValueAsLong(final String name) {
		return Long.valueOf(parameterMap.get(name).getValue());
	}
	
	/**
	 * Returns the value of specified parameter expressed as a Double
	 * 
	 * @param name The parameter's name
	 * @return the value of specified parameter expressed as a Double
	 */
	public Double getParameterValueAsDouble(final String name) {
		return Double.valueOf(parameterMap.get(name).getValue());
	}
	
	/**
	 * Updates the value of the specified parameter
	 * 
	 * @param name 	The parameter's name
	 * @param value	The value to set
	 */
	public void updateParameterValue(final String name, final String value) throws InvalidParameterException {
		final ConfigParameter cp = parameterMap.get(name);
		if(cp != null) {
			cp.setValue(value);
		}
	}
	
	/**
	 * Resets the specified parameter to its default value
	 * 
	 * @param name The parameter's name
	 */
	public void resetParameterValue(final String name) {
		final ConfigParameter cp = parameterMap.get(name);
		if(cp != null) {
			try {
				cp.setValue(cp.getDefaultValue());
			} catch (final InvalidParameterException e) {
				// This should never happen as the validity of the default value
				// is controlled at creation time. Anyway, if something unexpected
				// happens, then an ExceptionAction is executed
				ExceptionHandler.getSingleton().handle(e);
			}
		}
	}
	
	/**
	 * Resets all the parameters to their default value
	 */
	public void reset() {
		for(final ConfigParameter cp : parameterMap.values()) {
			try {
				cp.setValue(cp.getDefaultValue());
			} catch (final InvalidParameterException e) {
				// This should never happen as the validity of the default value
				// is controlled at creation time. Anyway, if something unexpected
				// happens, then an ExceptionAction is executed
				ExceptionHandler.getSingleton().handle(e);
			}
		}
	}
	
	/**
	 * Makes persistence the configuration saving it to the config file
	 */
	public void save() {
		try {
			// Reads the original conf file into a document object
			final File confFile = new File(CONFIG_FILE_NAME);
			final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(confFile);
			
			// Updates parameters value in the document object
			for(final ConfigParameter cp : getParameters()) {
				writeParameter(doc, cp);
			}
			
			// Prepares the DOM document for writing
			final Source source = new DOMSource(doc);
			
			// Prepares the output file
			final Result result = new StreamResult(confFile);
			
			// Writes the DOM document to the file
			final Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, XML_DTD);
			xformer.transform(source, result);
		} catch (final Exception e) {
			System.err.println("Exception saving config file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the parameters
	 */
	private void load() throws InvalidParameterException {
		Document doc = null;
		try {
			final File confFile = new File(CONFIG_FILE_NAME);
			final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(confFile);
		} catch (final Exception e) {
			System.err.println("Exception reading config file. Switching to default values");
			e.printStackTrace(System.err);
		}
		
		// Reads parameters from xml
		for(final String[] p : parameters) {
			readParameter(doc, p[0], p[1], p[2], p[3]);
		}
	}
	
	/**
	 * Reads the specified parameter from the specified XML document
	 * 
	 * @param doc			The XML document
	 * @param name			The name of the parameter to be red
	 * @param description	The description of the parameter
	 * @param defaultValue	The default value of the parameter
	 * @return				The value of the specified parameter
	 * @throws NoSuchMethodException 
	 * @throws InvalidParameterException 
	 */
	private String readParameter(final Document doc, final String name, final String description, final String defaultValue, final String type) throws InvalidParameterException {
		Element el = null;
		String value = null;
		String path = null;
		
		try {			
			el = doc.getElementById(name);
			value = el.getFirstChild().getNodeValue();
			path = getElementPath(el);
		} catch (final Exception e) {
			System.err.println("Exception reading " + name + " parameter. Switching to default value");
			e.printStackTrace(System.err);
		} finally {
			addParameter(name, path, description, value, defaultValue, type);
		}

		return value;
	}
	
	/**
	 * Retrieves the path of the specified XML node
	 * 
	 * @param n	The node
	 * @return the path of the specified XML node
	 */
	private String getElementPath(Node n) {
		final StringBuffer sb = new StringBuffer();
		
		while(n.getParentNode() != null && !n.getParentNode().getNodeName().equals(XML_ROOTNODE)) {
			n = n.getParentNode();
		
			sb.append(n.getNodeName() + XML_PATH_SEPARATOR);
		}
		
		if(sb.lastIndexOf(XML_PATH_SEPARATOR) != -1) {
			sb.setLength(sb.lastIndexOf(XML_PATH_SEPARATOR));
		}
		
		return sb.toString();
	}
	
	/**
	 * Creates a new parameter and adds it to the parameter's map
	 * 
	 * @param name			The name of the parameter to be created
	 * @param path			The path of the parameter to be created
	 * @param description	The description of the parameter to be created
	 * @param value			The value of the parameter to be created
	 * @param defaultValue	The default value of the parameter to be created
	 */
	private void addParameter(final String name, final String path, final String description, final String value, final String defaultValue, final String type) throws InvalidParameterException {
		parameterMap.put(name, new ConfigParameter(name, path, description, value, defaultValue, type));
	}
	
	/**
	 * Writes the specified parameter to the specified XML document
	 * 
	 * @param doc			The XML document where to write the parameter
	 * @param parameter		The parameter to be written
	 */
	private void writeParameter(final Document doc, final ConfigParameter parameter) {
		final Element el = doc.getElementById(parameter.getName());
		el.getFirstChild().setNodeValue(parameter.getValue());
	}
}

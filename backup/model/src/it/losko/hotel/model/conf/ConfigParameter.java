package it.losko.hotel.model.conf;

import java.lang.reflect.Method;

/**
 * This is the representation of a parameter.
 * Every parameter allows to specify an application's property.
 * Modifying a parameter will result in a different application's behavior
 * 
 * @author losko
 */
public class ConfigParameter {
	private final String name;
	private final String path;
	private final String description;
	private final String defaultValue;
	private final Class<?> clazz;
	private final Method verifierMethod;
	private String value;
	
	/**
	 * Creates a new parameter
	 * 
	 * @param name			The name of the parameter. This is unique
	 * @param path			The path of the parameter. Represent the logical group of appartenency
	 * @param description	The description of the parameter
	 * @param value			The parameter's value
	 * @param type			The parameter's type, meant as class' name
	 * @param defaultValue	The default parameter's value
	 */
	public ConfigParameter(final String name, final String path, final String description, final String value, final String defaultValue, final String type) throws InvalidParameterException {
		this.name = name;
		this.path = path;
		this.description = description;

		// Verifier. This must be set before parameters' values
		try {
			this.clazz = Class.forName(type);
			if(!clazz.equals(String.class)) {
				verifierMethod = clazz.getMethod("valueOf", String.class);
			} else {
				verifierMethod = null;
			}
		} catch (final ClassNotFoundException e) {
			throw new InvalidParameterException("Couldn't load the declared class for parameter " + name + "(" + type + ")", e);
		} catch (final NoSuchMethodException e) {
			throw new InvalidParameterException("Invalid class for parameter " + name + ". Parameter's classes must implement the valueOf(String value) static method", e);
		}
		
		// Default value
		checkParameterValue(defaultValue);
		this.defaultValue = defaultValue;
		
		// Value
		setValue(value != null ? value : defaultValue);
	}
	
	/**
	 * Checks if the specified value is compatible with the parameter type
	 * 
	 * @param value	The value to be checked
	 */
	private void checkParameterValue(final String value) throws InvalidParameterException {
		if(verifierMethod != null) {
			try {
				verifierMethod.invoke(null, value);
			} catch (final Exception e) {
				throw new InvalidParameterException("Error while setting value for parameter " + name, e);
			}
		}
	}
	
	/**
	 * Returns the name of the parameter
	 * 
	 * @return the name of the parameter
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the path of the parameter
	 * 
	 * @return the path of the parameter
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the description of the parameter
	 * 
	 * @return the description of the parameter
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the default value of the parameter
	 * 
	 * @return the default value of the parameter
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * Returns the value of the parameter
	 * 
	 * @return the value of the parameter
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the type of the parameter
	 * 
	 * @return the type of the parameter
	 */
	public Class<?> getType() {
		return clazz;
	}
	
	/**
	 * Sets the value of the specified parameter.
	 * The access modifier is protected because
	 * only the Config module itself should be able
	 * to modify a parameter value
	 * 
	 * @param value	The value to bet set
	 */
	protected void setValue(final String value) throws InvalidParameterException {
		checkParameterValue(value);
		
		this.value = value;
	}
}

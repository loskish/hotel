package it.losko.hotel.model.board;

import java.io.Serializable;

/**
 * The abstract representation of a property
 * 
 * @author losko
 */
public abstract class Property implements Serializable {
	private static final long serialVersionUID = -7448386860719500421L;
	
	private final String name;
	
	protected Property(final String name) {
		this.name = name;
	}

	/**
	 * Returns the property' name
	 * 
	 * @return the property' name
	 */
	public String getName() {
		return name;
	}
}

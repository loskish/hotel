package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Property;
import it.losko.hotel.model.player.Player;

/**
 * Abstract action that involves a property
 * 
 * @author losko
 */
public abstract class PropertyAction extends PlayerAction {
	private static final long serialVersionUID = 5490774690694412874L;

	private final Property property;
	
	public PropertyAction(final Player doingPlayer, final Property property) {
		super(doingPlayer);
		this.property = property;
	}

	/**
	 * Returns the property involved in the action
	 * 
	 * @return the property involved in the action
	 */
	public Property getProperty() {
		return property;
	}
}

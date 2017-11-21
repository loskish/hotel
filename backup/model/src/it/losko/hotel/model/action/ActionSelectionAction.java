package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.InvalidInputTypeException;

import java.util.List;

/**
 * Initial request for input. This happens when
 * the application starts. This action expects to receive
 * some input from the user interface implementation
 * 
 * @author losko
 */
public class ActionSelectionAction extends InputAction {
	private static final long serialVersionUID = 7439133799856607310L;
	
	private final List<Choosable> actionsList;

	protected ActionSelectionAction(final List<Choosable> actionsList) {
		super();
		this.actionsList = actionsList;
	}
	
	/**
	 * Returns the list of actions among whom
	 * the user is requested to choose
	 * 
	 * @return the list of actions among whom
	 * the user is requested to choose
	 */
	public List<Choosable> getActionsList() {
		return actionsList;
	}

	public boolean setInputAsInteger(final Integer input) {
		try {
			return setInput(input);
		} catch (final InvalidInputTypeException e) {
			// This exception will not ever actually thrown if this
			// method is called, anyway it formally returns false.
			return false;
		}
	}
	
	@Deprecated
	@Override
	protected boolean setInput(final Object input) throws InvalidInputTypeException {
		if(input == null || input instanceof Integer) {
			return super.setInput(input);
		} else {
			throw new InvalidInputTypeException("Input must be of type " + Integer.class.getName());
		}
	}
	
	public Integer getInputAsInteger() {
		return (Integer) getInput();
	}
	
	@Deprecated
	@Override
	protected Object getInput() {
		return super.getInput();
	}
	
	@Override
	protected void doAction() throws HotelException {
	}
	
	@Override
	public String getNotifiableDescription() {
		return "Initial input is requested";
	}
}

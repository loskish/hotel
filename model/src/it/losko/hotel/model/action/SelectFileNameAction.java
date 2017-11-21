package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.InvalidInputTypeException;

/**
 * File name selection. This typically happens
 * when saving or loading the game
 * 
 * @author losko
 */
public class SelectFileNameAction extends InputAction {
	private static final long serialVersionUID = -1217918833221354714L;

	public SelectFileNameAction() {
		super();
	}
	
	public boolean setInputAsString(final String input) {
		try {
			return setInput(input);
		} catch (final InvalidInputTypeException e) {
			// This exception will not ever actually thrown if this
			// method is called, anyway it formally returns false.
			return false;
		}
	}
	
	@Override
	@Deprecated
	protected boolean setInput(final Object input) throws InvalidInputTypeException {
		if(input == null || input instanceof String) {
			return super.setInput(input);
		} else {
			throw new InvalidInputTypeException("Input must be of type " + String.class.getName());
		}
	}
	
	public String getInputAsString() {
		return (String) getInput();
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
		return getInputAsString() + " has been selected";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
	
	@Override
	protected int getInputTimeout() {
		return 0;
	}
}

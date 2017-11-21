package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.InvalidInputTypeException;
import it.losko.hotel.model.game.Game;

/**
 * Exception occurred. This happens
 * when an unexpected error occurred
 * 
 * @author losko
 */
public class ExceptionAction extends InputAction {
	private static final long serialVersionUID = -2194131643942106521L;
	
	private final Exception exception;

	public ExceptionAction(final Exception exception) {
		super();
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public boolean setInputAsBoolean(final Boolean input) {
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
		if(input == null || input instanceof Boolean) {
			return super.setInput(input);
		} else {
			throw new InvalidInputTypeException("Input must be of type " + Boolean.class.getName());
		}
	}
	
	public Boolean getInputAsBoolean() {
		return (Boolean) getInput();
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
		return "Exception occurred";
	}
	
	@Override
	protected void postInputCollected() throws HotelException {
		super.postInputCollected();
		
		if(!getInputAsBoolean()) {
			Game.getSingleton().quit();
		}
	}
}

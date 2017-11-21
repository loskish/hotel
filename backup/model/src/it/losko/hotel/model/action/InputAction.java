package it.losko.hotel.model.action;

import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.InvalidInputTypeException;
import it.losko.hotel.model.game.Game;

public abstract class InputAction extends Action {
	private static final long serialVersionUID = -688215016742506776L;
	
	private Object input;
	private boolean collected = false;
	private boolean skippable = true;

	/**
	 * This method is protected because it's not intended to be called directly
	 * by any class outside this package. It is intended to be called only by a
	 * subclass method that defines the specific type of the parameter to set 
	 * 
	 * @param input
	 * @return true if the input is valid and has been accepted, false otherwise
	 */
	protected boolean setInput(final Object input) throws InvalidInputTypeException {
		this.input = input;
		
		if(input == null && !isSkippable()) {
			setCollected(false);
		} else {
			setCollected(true);
		}
		
		return isCollected();
	}

	/**
	 * This method is protected because it's not intended to be called directly
	 * by any class outside this package. It is intended to be called only by a
	 * subclass method that defines the specific type of the returned parameter 
	 * 
	 * @return
	 */
	protected Object getInput() {
		return input;
	}
	
	/**
	 * Sets the flag that indicates that the input has been collected
	 * and automatically wakes up any thread waiting for the input
	 * 
	 * @param collected The parameter that indicates that the input has been collected
	 */
	synchronized private void setCollected(final boolean collected) {
		this.collected = collected;

		if(isCollected()) {
			notify();
		}
	}

	/**
	 * Indicates if the input has been collected
	 * 
	 * @return true if the input has been collected, false otherwise
	 */
	synchronized private boolean isCollected() {
		return collected;
	}
	
    public boolean isSkippable() {
        return skippable;
    }

    public void setSkippable(final boolean skippable) {
        this.skippable = skippable;
    }
	
	protected int getInputTimeout() {
		return Config.getSingleton().getParameterValueAsInteger(ConfigConstants.PARAM_INPUT_TIMEOUT);
	}

	/**
	 * This method is declared final because defines the basic
	 * {@link InputAction} immutable behavior. Any extension has
	 * to be made through the encapsulated postInputCollected method
	 */
	@Override
	protected final void postDoAction() throws HotelException {
		super.postDoAction();

		synchronized(this) {
			while(!isCollected() && !Game.getSingleton().isTerminating()) {
				try {
					if(this instanceof PlayerActionInterface) {
						wait();
						// TODO: Handle timeouts
						/*wait(getInputTimeout());
						if(!isCollected()) {
							final Player delayingPlayer = ((PlayerActionInterface) this).getDoingPlayer();
							final LeaveAction leaveAction = new LeaveAction(delayingPlayer);
							leaveAction.executeAction();
						}*/
					} else {
						wait();
					}
				} catch (final InterruptedException ex) {   
					// Ignored
				}
			}
		}
		
		postInputCollected();
	}
	
	/**
	 * Method appointed for extensions
	 */
	protected void postInputCollected() throws HotelException {
	}
}

package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.InvalidInputTypeException;
import it.losko.hotel.model.player.PlayersBuilder;

/**
 * Game started. This happens when all players
 * join the game and they're ready to begin
 * 
 * @author losko
 */
public class CollectPlayersAction extends InputAction {
	private static final long serialVersionUID = -4103761122601266691L;

	public CollectPlayersAction() {
		super();
	}
	
	public boolean setInputAsPlayersBuilder(final PlayersBuilder input) {
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
		if(input == null || input instanceof PlayersBuilder) {
			return super.setInput(input);
		} else {
			throw new InvalidInputTypeException("Input must be of type " + PlayersBuilder.class.getName());
		}
	}
	
	public PlayersBuilder getInputAsPlayersBuilder() {
		return (PlayersBuilder) getInput();
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
		return "Players have been collected";
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

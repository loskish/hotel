package it.losko.hotel.model.action;


/**
 * A saved game has been loaded
 * 
 * @author losko
 */
public class GameLoadAction extends Action implements Choosable {
	private static final long serialVersionUID = -8081880227655221161L;

	public GameLoadAction() {
		super();
	}

	@Override
	protected void doAction() {
	}
	
	@Override
	public String getNotifiableDescription() {
		return "Game loaded";
	}

	@Override
	public String getChoiceDescription() {
		return "Load game";
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

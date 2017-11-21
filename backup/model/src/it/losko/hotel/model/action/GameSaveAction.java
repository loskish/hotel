package it.losko.hotel.model.action;


/**
 * The game has been saved
 * 
 * @author losko
 */
public class GameSaveAction extends Action {
	private static final long serialVersionUID = -5566346651143272849L;

	public GameSaveAction() {
		super();
	}

	@Override
	protected void doAction() {
	}
	
	@Override
	public String getNotifiableDescription() {
		return "Game saved";
	}
}

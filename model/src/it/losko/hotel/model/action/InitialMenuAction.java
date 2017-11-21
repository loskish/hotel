package it.losko.hotel.model.action;

import java.util.List;

public class InitialMenuAction extends ActionSelectionAction {
	private static final long serialVersionUID = 2490143155164341240L;

	public InitialMenuAction(final List<Choosable> actionsList) {
		super(actionsList);
	}
	
	@Override
	public boolean isSkippable() {
		return false;
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

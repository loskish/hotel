package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.ExceptionAction;
import it.losko.hotel.view.gui.GUI;

public class ExceptionActionListener extends GUIActionListener {
	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final ExceptionAction action = (ExceptionAction) uncastedAction;

		action.setInputAsBoolean(ui.handleException(action.getException()));
	}
}

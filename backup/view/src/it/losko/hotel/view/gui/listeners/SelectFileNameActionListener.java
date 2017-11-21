package it.losko.hotel.view.gui.listeners;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.SelectFileNameAction;
import it.losko.hotel.view.gui.GUI;
import it.losko.hotel.view.gui.GUIFileChooser;

import javax.swing.JFileChooser;

public class SelectFileNameActionListener extends GUIActionListener {
	@Override
	public void actionNotified(final GUI ui, final Action uncastedAction) {
		super.actionNotified(ui, uncastedAction);
		
		final SelectFileNameAction action = (SelectFileNameAction) uncastedAction;
		
		final GUIFileChooser fc = new GUIFileChooser(ui);
    	if(fc.showDialog(ui.getMainWindow(), "Select") == JFileChooser.APPROVE_OPTION) {
    		action.setInputAsString(fc.getSelectedFile().getName());
    	} else {
    		action.setInputAsString(null);
    	}
	}
}

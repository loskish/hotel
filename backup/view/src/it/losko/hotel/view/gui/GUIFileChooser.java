package it.losko.hotel.view.gui;

import it.losko.hotel.model.game.GameConstants;
import it.losko.hotel.model.info.Info;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUIFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	
	private final GUI gui;
	
	public GUIFileChooser(final GUI gui) {
		this.gui = gui;
		
		final File dir = new File(GameConstants.SAVEGAMES_FOLDER);
		final File sel = new File(GameConstants.QUICKSAVE + GameConstants.SAVEGAMES_EXTENSION);
		final String description = Info.getSingleton().getName() + "'s savegames (" + GameConstants.SAVEGAMES_EXTENSION + ")";
		final FileNameExtensionFilter fnef = new FileNameExtensionFilter(description, GameConstants.SAVEGAMES_EXTENSION.substring(1));
		
		setCurrentDirectory(dir);
    	setFileFilter(fnef);
    	setSelectedFile(sel);
    	setAcceptAllFileFilterUsed(false);
    	setMultiSelectionEnabled(false);
	}

	public GUI getGui() {
		return gui;
	}
}
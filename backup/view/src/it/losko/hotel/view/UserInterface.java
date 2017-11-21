package it.losko.hotel.view;

import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.view.tui.TUI;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * An abstract class defining the user interface implementation 
 * 
 * @author losko
 */
public abstract class UserInterface {
	
	protected UserInterface() {
	}
	
	protected static UserInterface getNewInstance() {
		return loadUserInterfaceClass();
	}
	
	/**
	 * Dinamically loads the UI class
	 * 
	 * @return The user interface class
	 */
	private static UserInterface loadUserInterfaceClass() {
		UserInterface ui = null;
		try {
			final Class<?> clazz = Class.forName(Config.getSingleton().getParameterValueAsString(ConfigConstants.PARAM_USER_INTERFACE_CLASS_NAME));
			ui = (UserInterface) clazz.newInstance();
		} catch (final Exception e) {
			ui = new TUI();

			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			System.err.print("Could'nt load the Graphical User Interface. Switching to Text User Interface\n\n" + sw);
		}
		
		return ui;
	}
}

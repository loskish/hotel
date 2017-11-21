package it.losko.hotel.view.gui;

import it.losko.hotel.view.UserInterface;
import it.losko.hotel.view.gui.resources.Images;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GUI extends UserInterface {
	private final GUIMainWindow mainWindow;
	private final Images images;
	
	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			handleException(e);
		}
		
		// The images initialization goes before any other
		// because it is used in other constructors
		images = new Images();
		mainWindow = new GUIMainWindow(this);
	}
	
	public GUIMainWindow getMainWindow() {
		return mainWindow;
	}
	
	public Images getImages() {
		return images;
	}
	
	public void display() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainWindow.pack();
				mainWindow.setVisible(true);
			}
		});
	}
	
	public String showInputDialog(final String message) {
		return JOptionPane.showInputDialog(mainWindow, message);
	}

	public void showMessageDialog(final String message) {
		JOptionPane.showMessageDialog(mainWindow, message);
	}
	
	public boolean showConfirmDialog(final String title, final String message) {
		return JOptionPane.showConfirmDialog(mainWindow, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ? true : false;
	}
	
	public boolean handleException(final Exception ex) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		final String st = sw.toString();
		
		final String confirm = "\nPress:\n" +
				"\tYES to ignore and continue (possible unstable behaviour)\n" +
				"\tNO to quit";
		
		return showConfirmDialog("An exception occured. What do you want to do?", st + confirm);
	}
}

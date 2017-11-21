package it.losko.hotel.view.tui;

import it.losko.hotel.model.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
 
public class Input extends Observable implements Runnable {
	
	private final TUI tui;
	private final BufferedReader br;
	private final InputStreamReader isr;

	public Input(final TUI tui) {
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		this.tui = tui;
	}
	
	@Override
	public void run() {
        try {
            while(!Game.getSingleton().isQuitting()) {
                String response = br.readLine();

                if(response != null && response.trim().isEmpty()) {
                	response = null;
                }
                
                setChanged();
                notifyObservers(response);
            }
        }
        catch (final IOException e) {
        	tui.handleException(e);
        }
    }
}


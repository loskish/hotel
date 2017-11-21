package it.losko.hotel.view.tui;

import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.game.GameConstants;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
 
public class InputObserver implements Observer {
	private final static String CMD_PREFIX = ">";
	public final static String CMD_LOAD = CMD_PREFIX + "load";
	public final static String CMD_SAVE = CMD_PREFIX + "save";
	public final static String CMD_PAUSE = CMD_PREFIX + "pause";
	public final static String CMD_RESUME = CMD_PREFIX + "resume";
	public final static String CMD_END = CMD_PREFIX + "end";
	public final static String CMD_QUIT = CMD_PREFIX + "quit";
	
    private final Stack<ResponseReceiver> listenersStack;
	
    public InputObserver() {
    	listenersStack = new Stack<ResponseReceiver>();
    }
    
	public void update(final Observable obj, final Object arg) {
    	final String input = (String) arg;
    	
		if(!processGlobalKeyBindings(input)) {
			// Notification to registered receivers
			if(!listenersStack.isEmpty()) {
				final ResponseReceiver receiver = listenersStack.pop();
				
				receiver.setResponse(input);
			}
		}
    }
    
    public void register(final ResponseReceiver receiver) {
    	listenersStack.push(receiver);
    }
    
    public void unregister(final ResponseReceiver receiver) {
    	listenersStack.remove(receiver);
    	receiver.setResponse(null);
    }
    
    public void unregisterAll() {
    	for(final ResponseReceiver receiver : listenersStack.toArray(new ResponseReceiver[0])) {
    		receiver.setResponse(null);
    	}
    	listenersStack.clear();
    }
    
    private boolean processGlobalKeyBindings(final String input) {
    	boolean found = false;
    	if(input != null) {
    		final String[] splittedInput = input.split(" ");
    		final String cmd = splittedInput[0];
    		try {
    			if(CMD_LOAD.equalsIgnoreCase(cmd)) {
    				found = true;
    				load(splittedInput);
    			} else if(CMD_SAVE.equalsIgnoreCase(cmd)) {
    				found = true;
    				save(splittedInput);
    			} else if(CMD_PAUSE.equalsIgnoreCase(cmd)) {
    				found = true;
    				Game.getSingleton().pause();
    			} else if(CMD_RESUME.equalsIgnoreCase(cmd)) {
    				found = true;
    				Game.getSingleton().resume();
    			} else if(CMD_END.equalsIgnoreCase(cmd)) {
    				found = true;
    				Game.getSingleton().end();
    			} else if(CMD_QUIT.equalsIgnoreCase(cmd)) {
    				found = true;
    				Game.getSingleton().quit();
    			}
    		} catch (final Exception e) {
    			throw new RuntimeException(e);
    		}
    	}
    	
    	return found;
    }
    
    private void save(final String[] splittedInput) throws IOException, HotelException {
    	if(splittedInput.length > 1) {
    		Game.getSingleton().save(splittedInput[2]);
    	} else {
    		Game.getSingleton().save(GameConstants.QUICKSAVE);
    	}
    }
    
    private void load(final String[] splittedInput) throws IOException, HotelException {
    	if(splittedInput.length > 1) {
    		Game.getSingleton().load(splittedInput[2]);
    	} else {
    		Game.getSingleton().load(GameConstants.QUICKSAVE);
    	}
    }
}
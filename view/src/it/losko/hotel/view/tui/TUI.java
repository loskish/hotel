package it.losko.hotel.view.tui;

import it.losko.hotel.model.game.Game;
import it.losko.hotel.view.UserInterface;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TUI extends UserInterface {
	
	public final static int LINE_SIZE = 75;
	
	private final Input input;
	private final InputObserver inputObserver;
	private Thread inputObserverThread;
	
	public TUI() {
		// Creates an event source - reads from stdin
        input = new Input(this);
 
        // Creates an observer
        inputObserver = new InputObserver();
 
        // Subscribes the observer to the event source
        input.addObserver(inputObserver);
	}

	public InputObserver getInputObserver() {
		return inputObserver;
	}

	public String askForInput(final String message) {
		// Creates and starts the event thread if not already done 
		if(inputObserverThread == null) {
			inputObserverThread = new Thread(input);
			
			// The JVM exits when the only running threads are all daemons,
			// so, setting this thread to deamon is needed as this thread
			// will be never explicitly terminated
			inputObserverThread.setDaemon(true);
			
			inputObserverThread.start();
		}
		
		System.out.print(message);
		
		// The use of the ResponseReceiver allows to call simultanously
		// the askForInput method from different threads. Synchronization
		// will be done on the receiver itself, allowing a stack of awating
		// listeners. Synchronization is not done on inputObserver object
		// because this will hang on multiple input requests.
		final ResponseReceiver receiver = new ResponseReceiver();
		inputObserver.register(receiver);

		synchronized (receiver) {
			while(!receiver.isResponseGiven()) {
				try {   
					receiver.wait();   
				} catch (final InterruptedException ex) {   
					// Ignored
				}
			}
		}
		
		return receiver.getResponse();
	}
	
	public void showMessage(final String message) {
		System.out.println(message);
	}
	
	public boolean handleException(final Exception ex) {
		final String request = "\nAn exception occured. What do you want to do?:\n" +
				"\t1. Ignore and continue (possible unstable behaviour)\n" +
				"\t2. View exception's stacktrace\n" +
				"\t3. Quit ";
		
		String result = askForInput(request);
		Integer choice = null;

		while(result != null && choice == null && !Game.getSingleton().isTerminating()) {
			try {
				choice = Integer.valueOf(result);
				if(choice <= 0 || choice > 3)
					choice = null;
			} catch (final NumberFormatException e) {
				showMessage("Invalid input. Please type again");
				result = askForInput(request);
			}
		}
		
		switch (choice) {
			case 1:
				// Ignores and continue
				return true;
			case 2:
				// Views stacktrace
				final StringWriter sw = new StringWriter();
				final PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
			default:
				// Quits
				return false;
		}
	}
}

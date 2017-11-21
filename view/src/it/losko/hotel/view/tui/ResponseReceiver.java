package it.losko.hotel.view.tui;

public class ResponseReceiver {
	private boolean responseGiven = false;
	private String response;
	
	public String getResponse() {
		return response;
	}

	public void setResponse(final String response) {
		this.response = response;
		synchronized (this) {
			responseGiven = true;
			notify();
		}
	}

	public synchronized boolean isResponseGiven() {
		return responseGiven;
	}
}

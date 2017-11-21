package it.losko.hotel.model.action;

import it.losko.hotel.model.exception.HotelException;

/**
 * Application loaded. This happens when program's
 * main routines are ingnited and the game is ready
 * to start
 * 
 * @author losko
 */
public class ApplicationLoadAction extends Action {
	private static final long serialVersionUID = 7430896538198120582L;

	private Integer response;
	private boolean responseGiven = false;

	public ApplicationLoadAction() {
		super();
	}

	@Override
	protected void doAction() throws HotelException {
	}

	@Override
	public String getNotifiableDescription() {
		return "Application loaded";
	}
	
	/**
	 * Should be called from the user interface
	 * once it has collected the requested input.
	 * For security reasons it is not possible to
	 * set directly the response Action object
	 * 
	 * @param response	The input collected from user expressed as
	 * 					index of the action choosed by the user
	 * 					(indexes are numbered starting from 1)
	 */
	public void setResponse(final Integer response) {
		this.response = response;
		setResponseGiven(true);
	}

	/**
	 * Returns the input requested to the user.
	 * This will be null until the user interface
	 * has collected some input and called setResponse
	 * For security reason it is only possibile to
	 * determine the chosen action through its index
	 * 
	 * @return 	The input collected from user expressed as
	 * 			index of the action choosed by the user
	 * 			(indexes are numbered starting from 1)
	 */
	public Integer getResponse() {
		return response;
	}

	/**
	 * Sets the flag that indicates that an answer has already been given
	 * 
	 * @param responseGiven true if the response has been given
	 */
	private void setResponseGiven(final boolean responseGiven) {
		this.responseGiven = responseGiven;
	}

	/**
	 * Indicates wether an answer has been given
	 * 
	 * @return wether an answer has been given
	 */
	public boolean isResponseGiven() {
		return responseGiven;
	}
	
	@Override
	protected boolean ignoresPauses() {
		return true;
	}
}

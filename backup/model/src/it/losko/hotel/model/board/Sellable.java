package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.HotelException;


/**
 * The interface implemented by sellable objects
 * 
 * @author losko
 */
public interface Sellable {

	/**
	 * Indicates whether the implenting entity is sellable
	 * 
	 * @return true if the implenting entity is sellable, false otherwise
	 */
	public boolean isSellable() throws HotelException;
	
	/**
	 * Allows to sell the implementing entity
	 */
	public void sell() throws HotelException;
}

package it.losko.hotel.model.board;

import java.io.Serializable;

/**
 * Represent every building piece of a hotel
 * 
 * @author losko
 */
public class Building implements Serializable {
	private static final long serialVersionUID = -8376199510651612521L;
	
	private final String name;
	private final double constructionPrice;
	private final double pricePerNight;
	
	protected Building(final String name, final double constructionPrice, final double pricePerNight) {
		this.name = name;
		this.constructionPrice = constructionPrice;
		this.pricePerNight = pricePerNight;
	}

	/**
	 * Returns the building's name
	 * 
	 * @return the building's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the building's price
	 * 
	 * @return the building's price
	 */
	public double getConstructionPrice() {
		return constructionPrice;
	}

	/**
	 * Returns the price per night of the hotel
	 * The price per night is always the one
	 * of the last building built
	 * 
	 * @return the price per night of the hotel
	 */
	public double getPricePerNight() {
		return pricePerNight;
	}
	
	/**
	 * Returns the price per stay at the hotel
	 * The referring price per night is always
	 * the one of the last building built
	 * 
	 * @return the price per stay at the hotel
	 */
	public double getPriceForStay(final int days) {
		return getPricePerNight() * days;
	}
	
	/**
	 * Returns the price that will be payed when selling the {@link Building}
	 * 
	 * @return the price that will be payed when selling the {@link Building}
	 */
	public double getSellPrice() {
		return getConstructionPrice() / 2;
	}
}

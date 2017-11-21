package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.BuildEntranceOnAnUnboughtHotelException;
import it.losko.hotel.model.exception.BuildEntranceOnAnUnbuiltHotelException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;

import java.io.Serializable;

/**
 * The representation of an entrance to an hotel
 * 
 * @author losko
 */
public class Entrance implements Serializable {
	private static final long serialVersionUID = -4919612923851500392L;

	private final Hotel hotel;
	
	public Entrance(final Hotel hotel) throws NotEnoughMoneyAvailableException, BuildEntranceOnAnUnboughtHotelException, BuildEntranceOnAnUnbuiltHotelException {
		this(hotel, false);
	}
	
	public Entrance(final Hotel hotel, final boolean free) throws NotEnoughMoneyAvailableException, BuildEntranceOnAnUnboughtHotelException, BuildEntranceOnAnUnbuiltHotelException {
		if(!hotel.isBought()) {
			throw new BuildEntranceOnAnUnboughtHotelException("Cannot build entrance on " + hotel.getName() + " since it's still unbought");
		}
		
		if(!hotel.isAtLeastOneBuildingBuilt()) {
			throw new BuildEntranceOnAnUnbuiltHotelException("Cannot build entrance on " + hotel.getName() + " since it's still unbuilt");
		}
		
		if(!free)
			hotel.getOwner().giveMoneyToBank(hotel.getEntrancePrice());
		
		this.hotel = hotel;
	}

	/**
	 * Returns the hotel on whitch the entrance is built
	 * 
	 * @return the hotel on whitch the entrance is built
	 */
	public Hotel getHotel() {
		return hotel;
	}
}

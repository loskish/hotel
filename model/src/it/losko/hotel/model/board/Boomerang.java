package it.losko.hotel.model.board;

/**
 * The Boomerang hotel
 * 
 * @author losko
 */
public class Boomerang extends Hotel {
	private static final long serialVersionUID = 8915573857823429180L;

	protected Boomerang() {
		super("Boomerang", 500, 250, 100, false);
		getAvailableBuildings().add(new Building("Main Building", 1800, 400));
		getAvailableBuildings().add(new Building("Facilities", 2250, 600));
	}
}

package it.losko.hotel.model.board;

/**
 * The Waikiki hotel
 * 
 * @author losko
 */
public class Waikiki extends Hotel {
	private static final long serialVersionUID = -9043124478748479728L;

	protected Waikiki() {
		super("Waikiki", 2500, 1250, 200, false);
		getAvailableBuildings().add(new Building("Main building", 3500, 200));
		getAvailableBuildings().add(new Building("Building 1", 2500, 350));
		getAvailableBuildings().add(new Building("Building 2", 2500, 500));
		getAvailableBuildings().add(new Building("Building 3", 1750, 500));
		getAvailableBuildings().add(new Building("Building 4", 1750, 650));
		getAvailableBuildings().add(new Building("Facilities", 2500, 1000));
	}
}

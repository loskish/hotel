package it.losko.hotel.model.board;

/**
 * The Fujihama hotel
 * 
 * @author losko
 */
public class Fujihama extends Hotel {
	private static final long serialVersionUID = 682889923809855904L;

	protected Fujihama() {
		super("Fujihama", 1000, 500, 100, true);
		getAvailableBuildings().add(new Building("Main building", 2200, 100));
		getAvailableBuildings().add(new Building("Building 1", 1400, 100));
		getAvailableBuildings().add(new Building("Building 2", 1400, 200));
		getAvailableBuildings().add(new Building("Facilities", 500, 400));
	}
}

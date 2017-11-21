package it.losko.hotel.model.board;

/**
 * The Safari hotel
 * 
 * @author losko
 */
public class Safari extends Hotel {
	private static final long serialVersionUID = 8126100332171228835L;

	protected Safari() {
		super("Safari", 2000, 1000, 150, false);
		getAvailableBuildings().add(new Building("Main building", 2600, 100));
		getAvailableBuildings().add(new Building("Building 1", 1200, 100));
		getAvailableBuildings().add(new Building("Building 2", 1200, 250));
		getAvailableBuildings().add(new Building("Facilities", 2000, 500));
	}
}

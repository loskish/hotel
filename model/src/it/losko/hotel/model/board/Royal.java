package it.losko.hotel.model.board;

/**
 * The Royal hotel
 * 
 * @author losko
 */
public class Royal extends Hotel {
	private static final long serialVersionUID = 8788069196477150562L;

	protected Royal() {
		super("Royal", 2500, 1250, 200, true);
		getAvailableBuildings().add(new Building("Main building", 3600, 150));
		getAvailableBuildings().add(new Building("Building 1", 2600, 300));
		getAvailableBuildings().add(new Building("Building 2", 1800, 300));
		getAvailableBuildings().add(new Building("Building 3", 1800, 450));
		getAvailableBuildings().add(new Building("Facilities", 3000, 600));
	}
}

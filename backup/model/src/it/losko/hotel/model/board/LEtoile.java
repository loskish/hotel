package it.losko.hotel.model.board;

/**
 * The L'Etoile hotel
 * 
 * @author losko
 */
public class LEtoile extends Hotel {
	private static final long serialVersionUID = 7408819489736662624L;

	protected LEtoile() {
		super("L'Etoile", 3000, 1500, 250, true);
		getAvailableBuildings().add(new Building("Main building", 3300, 150));
		getAvailableBuildings().add(new Building("Building 1", 2200, 300));
		getAvailableBuildings().add(new Building("Building 2", 1800, 300));
		getAvailableBuildings().add(new Building("Building 3", 1800, 300));
		getAvailableBuildings().add(new Building("Building 4", 1800, 450));
		getAvailableBuildings().add(new Building("Facilities", 4000, 750));
	}
}

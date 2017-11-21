package it.losko.hotel.model.board;

/**
 * The President hotel
 * 
 * @author losko
 */
public class President extends Hotel {
	private static final long serialVersionUID = -2812696661056600920L;

	protected President() {
		super("President", 3500, 1750, 250, false);
		getAvailableBuildings().add(new Building("Main building", 5000, 200));
		getAvailableBuildings().add(new Building("Building 1", 3000, 400));
		getAvailableBuildings().add(new Building("Building 2", 2250, 600));
		getAvailableBuildings().add(new Building("Building 3", 1750, 800));
		getAvailableBuildings().add(new Building("Facilities", 5000, 1100));
	}
}

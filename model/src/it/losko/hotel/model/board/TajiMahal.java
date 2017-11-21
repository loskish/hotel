package it.losko.hotel.model.board;

/**
 * The TajiMahal hotel
 * 
 * @author losko
 */
public class TajiMahal extends Hotel {
	private static final long serialVersionUID = -1088044417728879295L;

	protected TajiMahal() {
		super("Taji Mahal", 1500, 750, 100, false);
		getAvailableBuildings().add(new Building("Main building", 2400, 100));
		getAvailableBuildings().add(new Building("Building 1", 1000, 100));
		getAvailableBuildings().add(new Building("Building 2", 500, 200));
		getAvailableBuildings().add(new Building("Facilities", 1000, 300));
	}
}

package it.losko.hotel.model.board;


import java.util.List;

/**
 * The starting square. Players start their game here.
 * Once they move, they'll never return on this square
 * 
 * @author losko
 */
public class StartSquare extends Square {
	private static final long serialVersionUID = 5482945876116118280L;

	protected StartSquare(final List<Property> adjacentProperties) {
		super(adjacentProperties);
	}
}

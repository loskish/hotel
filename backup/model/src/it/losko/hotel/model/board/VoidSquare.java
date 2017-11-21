package it.losko.hotel.model.board;


import java.util.List;

/**
 * A square where no action is possible exception made for the automatic ones
 * 
 * @author losko
 * @see Square
 */
public class VoidSquare extends Square {
	private static final long serialVersionUID = 5223180110638828290L;

	protected VoidSquare(final List<Property> adjacentProperties) {
		super(adjacentProperties);
	}
}

package it.losko.hotel.model.board;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The board model. It defines the squares composing the track
 * and the position of the special proerties {@link Bank} and {@link Municipe}
 * 
 * @author losko
 */
public class Board implements Serializable {
	private static final long serialVersionUID = -8029970543616399476L;

	private final Bank bank;
	private final Boomerang boomerang;
	private final Fujihama fujihama;
	private final LEtoile letoile;
	private final Municipe municipe;
	private final President president;
	private final Royal royal;
	private final Safari safari;
	private final TajiMahal tajimahal;
	private final Waikiki waikiki;
	
	private final List<Square> squares;
	private final int bankPosition;
	private final int municipePosition;
	private final int numberOfPurchaseSquares;
	private final int numberOfBuildSquares;
	private final int numberOfFreeBuildSquares;
	private final int numberOfFreeEntranceSquares;
	
	public Board() {
		bank = new Bank();
		boomerang = new Boomerang();
		fujihama = new Fujihama();
		letoile = new LEtoile();
		municipe = new Municipe();
		president = new President();
		royal = new Royal();
		safari = new Safari();
		tajimahal = new TajiMahal();
		waikiki = new Waikiki();
		
		squares = new ArrayList<Square>();
		buildPath();
		bankPosition = retrieveBankPosition();
		municipePosition = retrieveMunicipePosition();
		numberOfPurchaseSquares = retrieveNumberOfPurchaseSquares();
		numberOfBuildSquares = retrieveNumberOfBuildSquares();
		numberOfFreeBuildSquares = retrieveNumberOfFreeBuildSquares();
		numberOfFreeEntranceSquares = retrieveNumberOfFreeEntranceSquares();
	}
	
	public Bank getBank() {
		return bank;
	}

	public Boomerang getBoomerang() {
		return boomerang;
	}

	public Fujihama getFujihama() {
		return fujihama;
	}

	public LEtoile getLetoile() {
		return letoile;
	}

	public Municipe getMunicipe() {
		return municipe;
	}

	public President getPresident() {
		return president;
	}

	public Royal getRoyal() {
		return royal;
	}

	public Safari getSafari() {
		return safari;
	}

	public TajiMahal getTajimahal() {
		return tajimahal;
	}

	public Waikiki getWaikiki() {
		return waikiki;
	}

	/**
	 * Returns the list of the squares defining the track
	 * 
	 * @return the list of the squares defining the track
	 */
	public List<Square> getSquares() {
		return squares;
	}
	
	/**
	 * The index of the starting square. At the beginning
	 * of the game, all players are at zero position.
	 * Once they move, they'll never be there again.
	 * 
	 * @return The index of the starting square
	 */
	public int getStartPosition() {
		return 0;
	}
	
	/**
	 * The index of the square whose if stepped through
	 * makes possibile to collect interest from the {@link Bank}
	 * 
	 * @return The index of the {@link Bank} square
	 */
	public int getBankPosition() {
		return bankPosition;
	}
	
	/**
	 * The index of the square whose if stepped through
	 * makes possibile to build entrances for player's built hotels
	 * 
	 * @return The index of the {@link Municipe} square
	 */
	public int getMunicipePosition() {
		return municipePosition;
	}
	
	/**
	 * Returns the number of {@link PurchaseSquare} in the track
	 * 
	 * @return the number of {@link PurchaseSquare} in the track
	 */
	public int getNumberOfPurchaseSquares() {
		return numberOfPurchaseSquares;
	}

	/**
	 * Returns the number of {@link BuildingSquare} in the track
	 * 
	 * @return the number of {@link BuildingSquare} in the track
	 */
	public int getNumberOfBuildSquares() {
		return numberOfBuildSquares;
	}

	/**
	 * Returns the number of {@link FreeBuildingSquare} in the track
	 * 
	 * @return the number of {@link FreeBuildingSquare} in the track
	 */
	public int getNumberOfFreeBuildSquares() {
		return numberOfFreeBuildSquares;
	}

	/**
	 * Returns the number of {@link FreeEntranceSquare} in the track
	 * 
	 * @return the number of {@link FreeEntranceSquare} in the track
	 */
	public int getNumberOfFreeEntranceSquares() {
		return numberOfFreeEntranceSquares;
	}
	
	/**
	 * Returns the position on board of the specified square
	 * @param square The square that we want to know the position
	 * @return the position on board of the specified square
	 */
	public int getSquarePosition(final Square square) {
		return getSquares().indexOf(square);
	}
	
	/**
	 * Searches the track for the {@link Bank} position
	 * 
	 * @return The index of the {@link Bank} position
	 */
	private int retrieveBankPosition() {
		for(int i = 0; i < squares.size(); i++) {
			if(squares.get(i).getAdjacentProperties().contains(bank))
				return i;
		}
		return (int) Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Searches the track for the {@link Municipe} position
	 * 
	 * @return The index of the {@link Municipe} position
	 */
	private int retrieveMunicipePosition() {
		for(int i = 0; i < squares.size(); i++) {
			if(squares.get(i).getAdjacentProperties().contains(municipe))
				return i;
		}
		return (int) Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Retrieves the number of {@link PurchaseSquare} in the track
	 * 
	 * @return the number of {@link PurchaseSquare} in the track
	 */
	private int retrieveNumberOfPurchaseSquares() {
		int res = 0;
		for(final Square square : getSquares()) {
			if(square instanceof PurchaseSquare) {
				res++;
			}
		}
		return res;
	}
	
	/**
	 * Retrieves the number of {@link BuildingSquare} in the track
	 * 
	 * @return the number of {@link BuildingSquare} in the track
	 */
	private int retrieveNumberOfBuildSquares() {
		int res = 0;
		for(final Square square : getSquares()) {
			if(square instanceof BuildingSquare) {
				res++;
			}
		}
		return res;
	}
	
	/**
	 * Retrieves the number of {@link FreeBuildingSquare} in the track
	 * 
	 * @return the number of {@link FreeBuildingSquare} in the track
	 */
	private int retrieveNumberOfFreeBuildSquares() {
		int res = 0;
		for(final Square square : getSquares()) {
			if(square instanceof FreeBuildingSquare) {
				res++;
			}
		}
		return res;
	}
	
	/**
	 * Retrieves the number of {@link FreeEntranceSquare} in the track
	 * 
	 * @return the number of {@link FreeEntranceSquare} in the track
	 */
	private int retrieveNumberOfFreeEntranceSquares() {
		int res = 0;
		for(final Square square : getSquares()) {
			if(square instanceof FreeEntranceSquare) {
				res++;
			}
		}
		return res;
	}
	
	/**
	 * Returns the square holding a specified position
	 * @param i	The position holded by the square
	 * @return 	the square holding a specified position
	 */
	public Square getSquareAtPosition(final int i) {
		return getSquares().get(i);
	}
	
	/**
	 * Returns the total number of squares present in the track
	 * Traditional board has 31 squares. The zero one won't exist
	 * once players started the game
	 * 
	 * @return the total number of squares present in the track
	 */
	public int getNumberOfSquares() {
		return getSquares().size() - 1;
	}
	
	/**
	 * Constructs the track
	 */
	private void buildPath() {
		// 00
		List<Property> adjacentProperties = new ArrayList<Property>();
		squares.add(new StartSquare(adjacentProperties));
		
		// 01
		adjacentProperties = new ArrayList<Property>();
		squares.add(new VoidSquare(adjacentProperties));
		
		// 02
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 03
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		adjacentProperties.add(boomerang);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 04
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		adjacentProperties.add(boomerang);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 05
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		adjacentProperties.add(boomerang);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 06
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		adjacentProperties.add(boomerang);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 07
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(fujihama);
		squares.add(new FreeEntranceSquare(adjacentProperties));
		
		// 08
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(bank);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 09
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 10
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(president);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 11
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(president);
		squares.add(new FreeBuildingSquare(adjacentProperties));
		
		// 12
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(president);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 13
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(president);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 14
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(president);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 15
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(president);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 16
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(president);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 17
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(waikiki);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 18
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(waikiki);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 19
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(waikiki);
		squares.add(new FreeEntranceSquare(adjacentProperties));
		
		// 20
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(waikiki);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 21
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(royal);
		adjacentProperties.add(waikiki);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 22
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(tajimahal);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 23
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(tajimahal);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 24
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(tajimahal);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 25
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(tajimahal);
		squares.add(new FreeBuildingSquare(adjacentProperties));
		
		// 26
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(tajimahal);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 27
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(municipe);
		adjacentProperties.add(safari);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 28
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(safari);
		squares.add(new BuildingSquare(adjacentProperties));
		
		// 29
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(safari);
		squares.add(new PurchaseSquare(adjacentProperties));
		
		// 30
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(letoile);
		adjacentProperties.add(safari);
		squares.add(new FreeEntranceSquare(adjacentProperties));
		
		// 31
		adjacentProperties = new ArrayList<Property>();
		adjacentProperties.add(safari);
		squares.add(new BuildingSquare(adjacentProperties));
	}
}

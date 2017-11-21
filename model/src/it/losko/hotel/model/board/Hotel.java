package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.CannotExpropriateUnboughtHotelException;
import it.losko.hotel.model.exception.ExpropriateBuiltHotelException;
import it.losko.hotel.model.exception.HotelAlreadyBoughtException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.exception.SellUnboughtHotelException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hotel extends Property implements Buildable, Buyable, Expropriable, Sellable {
	private static final long serialVersionUID = 1158491155703419360L;
	
	private final double landPrice;
	private final double expropriationPrice;
	private final double entrancePrice;
	private final List<Building> availableBuildings;
	private final List<Square> adjacentSquares;
	private final boolean inner;
	
	private int builtBuildings;
	private Player owner;
	
	protected Hotel(final String name, final double landPrice, final double expropriationPrice, final double entrancePrice, final boolean inner) {
		super(name);
		this.landPrice = landPrice;
		this.expropriationPrice = expropriationPrice;
		this.entrancePrice = entrancePrice;
		this.availableBuildings = new ArrayList<Building>();
		this.adjacentSquares = new ArrayList<Square>();
		this.inner = inner;
		
		builtBuildings = 0;
	}

	public List<Building> getAvailableBuildings() {
		return availableBuildings;
	}

	public boolean isAtLeastOneBuildingBuilt() {
		return builtBuildings > 0;
	}
	
	public boolean isCompletelyBuilt() {
		return builtBuildings == availableBuildings.size();
	}

	public double getLandPrice() {
		return landPrice;
	}
	
	public double getLandSellPrice() {
		return getLandPrice() / 2;
	}

	public double getExpropriationPrice() {
		return expropriationPrice;
	}

	public double getEntrancePrice() {
		return entrancePrice;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(final Player owner) throws HotelAlreadyBoughtException, CannotExpropriateUnboughtHotelException {
		if(getOwner() != null && !getOwner().equals(owner)) {
			getOwner().removeOwnedHotel(this);
		}
		
		this.owner = owner;

		if(owner != null) {
			owner.addOwnedHotel(this);
		}
	}
	
	public Building getNextBuilding() throws NoMoreBuildingsToBuildException {
		if(isCompletelyBuilt()) {
			throw new NoMoreBuildingsToBuildException(getName() + " has no more buildings");
		}
		
		return availableBuildings.get(builtBuildings);
	}
	
	public double getPriceForStay(final int days) {
		return getLastBuiltBuilding().getPriceForStay(days);
	}
	
	public Integer build() throws NoMoreBuildingsToBuildException, BuildUnboughtHotelException, NotEnoughMoneyAvailableException, BuildPermissionNotGrantedException {
		return build(false);
	}
	
	public Integer build(final boolean free) throws NoMoreBuildingsToBuildException, BuildUnboughtHotelException, NotEnoughMoneyAvailableException, BuildPermissionNotGrantedException {
		if(!isBought()) {
			throw new BuildUnboughtHotelException(getName() + " can't be built since it is not owned by anyone");
		}
		
		final Building next = getNextBuilding();
		double owed = 0;
		Integer coeff = Integer.valueOf(0);
			
		if(!free) {
			coeff = getRandomBuildingCoefficient();
			if(coeff == null) {
				throw new BuildPermissionNotGrantedException("Build permission for " + getName() + " wasn't granted");
			}
			owed = next.getConstructionPrice() * coeff;
			getOwner().giveMoneyToBank(owed);
		}
		
		builtBuildings++;
		
		getOwner().setHasAlreadyMadeABuildActionOnTheSquareHeIs(true);
		
		return coeff;
	}
	
	/**
	 * 0 build for free
	 * 1 build for normal price
	 * 2 build for doubled price
	 * 3 build permission not granted
	 */
	private Integer getRandomBuildingCoefficient() {
		final Random random = new Random();
		final int randomInt = random.nextInt(4);
		return randomInt != 3 ? randomInt : null;
	}

	public void buy(final Player owner) throws HotelAlreadyBoughtException, NotEnoughMoneyAvailableException, CannotExpropriateUnboughtHotelException {
		if(isBought()) {
			throw new HotelAlreadyBoughtException(getName() + " is already owned by " + owner.getName());
		}
		
		owner.giveMoneyToBank(getLandPrice());
		setBought(owner);
		
		getOwner().setHasAlreadyMadeAPurchaseActionOnTheSquareHeIs(true);
	}
	
	public boolean isBought() {
		return owner != null;
	}

	public void setBought(final Player owner) throws HotelAlreadyBoughtException, CannotExpropriateUnboughtHotelException {
		setOwner(owner);
	}

	public void expropriate(final Player doingPlayer) throws CannotExpropriateUnboughtHotelException, NotEnoughMoneyAvailableException, ExpropriateBuiltHotelException, HotelAlreadyBoughtException {
		if(!isBought()) {
			throw new CannotExpropriateUnboughtHotelException(getName() + " can't be expropriated since it's not owned by anyone"); 
		}
		
		if(isAtLeastOneBuildingBuilt()) {
			throw new ExpropriateBuiltHotelException(getName() + " can't be expropriated since it is already built"); 
		}
		
		doingPlayer.giveMoneyToPlayer(getOwner(), getExpropriationPrice());
		setOwner(doingPlayer);
		
		getOwner().setHasAlreadyMadeAnExpropriateActionOnTheSquareHeIs(true);
	}

	public double getActualValue() {
		double value = getLandPrice();
		
		// Value of buildings
		for(final Building building : getAvailableBuildings().subList(0, builtBuildings))  {
			value += building.getConstructionPrice();
		}

		// Value of entrances
		for(final Square square : getAdjacentSquares()) {
			if(square.hasEntrance() && square.getEntrance().getHotel().equals(this)) {
				value += getEntrancePrice();
			}
		}

		return value;
	}
	
	public double getActualSellValue() {
		double value = getLandSellPrice();
		
		// Value of buildings
		for(final Building building : getAvailableBuildings().subList(0, builtBuildings))  {
			value += building.getSellPrice();
		}

		return value;
	}
	
	public List<Square> getAdjacentSquares() {
		return adjacentSquares;
	}

	public void addAdjacentSquare(final Square square) {
		adjacentSquares.add(square);
	}
	
	public List<Square> getAdjacentSquaresSuitableForBuildingEntrance() {
		final List<Square> adjacentSquaresSuitable = new ArrayList<Square>();

		if(isAtLeastOneBuildingBuilt()) {
			for(final Square square : getAdjacentSquares()) {
				if(!square.hasEntrance()) {
					adjacentSquaresSuitable.add(square);
				}
			}
		}
		
		return adjacentSquaresSuitable;
	}
	
	public List<Square> getAdjacentSquaresWithABuiltEntrance() {
		final List<Square> adjacentSquaresWithABuiltEntrance = new ArrayList<Square>();

		if(isAtLeastOneBuildingBuilt()) {
			for(final Square square : getAdjacentSquares()) {
				if(square.hasEntrance() && square.getEntrance().getHotel().equals(this)) {
					adjacentSquaresWithABuiltEntrance.add(square);
				}
			}
		}
		
		return adjacentSquaresWithABuiltEntrance;
	}

	public Building getLastBuiltBuilding() {
		return availableBuildings.get(builtBuildings - 1);
	}
	
	public boolean isBuildable(final Player doingPlayer, final boolean forFree) {
		return !isCompletelyBuilt()
			&& isBought()
			&& getOwner().equals(doingPlayer)
			&& !doingPlayer.hasAlreadyMadeABuildActionOnTheSquareHeIs()
			&& !doingPlayer.hasRefusedAnymoreActionsOnTheSquareHeIs()
			&& (forFree || doingPlayer.isMoneyAvailable(availableBuildings.get(builtBuildings).getConstructionPrice()));
	}
	
	public boolean isBuyable(final Player doingPlayer) {
		return !isBought()
			&& doingPlayer.isMoneyAvailable(getLandPrice())
			&& !doingPlayer.hasAlreadyMadeAPurchaseActionOnThisSquare()
			&& !doingPlayer.hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs()
			&& !doingPlayer.hasRefusedAnymoreActionsOnTheSquareHeIs();
	}
	
	public boolean isExpropriable(final Player doingPlayer) {
		return isBought()
			&& !isAtLeastOneBuildingBuilt()
			&& !getOwner().equals(doingPlayer)
			&& doingPlayer.isMoneyAvailable(getExpropriationPrice());
	}
	
	public boolean isSellable() {
		return isBought();
	}
	
	public boolean isInner() {
		return inner;
	}

	private void removeAllEntrances() {
		for(final Square square : getAdjacentSquaresWithABuiltEntrance()) {
			square.setEntrance(null);
		}
	}
	
	public void sell() throws NotEnoughMoneyAvailableException, CannotExpropriateUnboughtHotelException, HotelAlreadyBoughtException, SellUnboughtHotelException {
		if(!isBought()) {
			throw new SellUnboughtHotelException(getName() + " can't be sold since it is not owned by anyone");
		}
		
		// Handles sell of buildings and land
		if(isAtLeastOneBuildingBuilt()) {
			Game.getSingleton().getBoard().getBank().giveMoneyToPlayer(getOwner(), getLastBuiltBuilding().getSellPrice());
			// Removes entrances if there will be no more buildings
			if(builtBuildings == 1) {
				// Must be done before removing the last building
				// because entries won't be found once removed
				removeAllEntrances();
			}
			builtBuildings--;
		} else {
			Game.getSingleton().getBoard().getBank().giveMoneyToPlayer(getOwner(), getLandSellPrice());
			setOwner(null);
		}
	}
	
	public void sellAll() throws HotelAlreadyBoughtException, CannotExpropriateUnboughtHotelException, NotEnoughMoneyAvailableException {
		Game.getSingleton().getBoard().getBank().giveMoneyToPlayer(getOwner(), getOwner().getSellPatrimony());
		
		removeAllEntrances();
		setOwner(null);
		builtBuildings = 0;
	}
}

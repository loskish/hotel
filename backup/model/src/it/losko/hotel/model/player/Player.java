package it.losko.hotel.model.player;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.PlayerAction;
import it.losko.hotel.model.action.PlayerActionSelectionAction;
import it.losko.hotel.model.action.SellAction;
import it.losko.hotel.model.action.WrongInputAction;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.exception.CannotExpropriateUnboughtHotelException;
import it.losko.hotel.model.exception.HotelAlreadyBoughtException;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.exception.PlayerNotInTheGameException;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.money.MoneyOwner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player extends MoneyOwner {
	private static final long serialVersionUID = 4095682250496983491L;

	private final String color; 
	private final boolean isArtificialIntelligence;

	private int positionOnBoard;
	private int previousPositionOnBoard;
	private boolean mustRollDice;
	private boolean hasRolledDice;
	private boolean hasAlreadyMadeABuildActionOnTheSquareHeIs;
	private boolean hasAlreadyMadeAPurchaseActionOnTheSquareHeIs;
	private boolean hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs;
	private boolean hasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs;
	private boolean hasAlreadyMadeAPayForStayActionOnTheSquareHeIs;
	private boolean hasRefusedAnymoreActionsOnTheSquareHeIs;

	private final List<Hotel> ownedHotels;
	private final Set<Hotel> hotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs;

	protected Player(final String name, final String color, final boolean isArtificialIntelligence) {
		super(name);

		this.color = color;
		this.isArtificialIntelligence = isArtificialIntelligence;
		setAvailableMoney(25000); // 12000 if more than 2 players
		setPositionOnBoard(Game.getSingleton().getBoard().getStartPosition());
		
		ownedHotels = new ArrayList<Hotel>();
		hotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs = new HashSet<Hotel>();
	}

	public boolean isArtificialIntelligence() {
		return isArtificialIntelligence;
	}

	public List<Hotel> getOwnedHotels() {
		return ownedHotels;
	}
	
	public boolean hasRolledDice() {
		return hasRolledDice;
	}

	public void setHasRolledDice(final boolean hasRolledDice) {
		this.hasRolledDice = hasRolledDice;
	}
	
	public boolean hasAlreadyMadeABuildActionOnTheSquareHeIs() {
		return hasAlreadyMadeABuildActionOnTheSquareHeIs;
	}

	public void setHasAlreadyMadeABuildActionOnTheSquareHeIs(final boolean hasAlreadyMadeABuildActionOnTheSquareHeIs) {
		this.hasAlreadyMadeABuildActionOnTheSquareHeIs = hasAlreadyMadeABuildActionOnTheSquareHeIs;
	}

	public boolean hasAlreadyMadeAPurchaseActionOnThisSquare() {
		return hasAlreadyMadeAPurchaseActionOnTheSquareHeIs;
	}

	public void setHasAlreadyMadeAPurchaseActionOnTheSquareHeIs(final boolean hasAlreadyMadeAPurchaseActionOnTheSquareHeIs) {
		this.hasAlreadyMadeAPurchaseActionOnTheSquareHeIs = hasAlreadyMadeAPurchaseActionOnTheSquareHeIs;
	}
	
	public boolean hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs() {
		return hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs;
	}

	public void setHasAlreadyMadeAnExpropriateActionOnTheSquareHeIs(final boolean hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs) {
		this.hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs = hasAlreadyMadeAnExpropriateActionOnTheSquareHeIs;
	}
	
	public boolean hasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs() {
		return hasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs;
	}

	public void setHasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs(final boolean hasAlreadyMadeABuildEntranceActionOnTheSquareHeIs) {
		this.hasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs = hasAlreadyMadeABuildEntranceActionOnTheSquareHeIs;
	}
	
	public boolean hasAlreadyMadeABuildEntranceActionForThisHotelOnTheSquareHeIs(final Hotel hotel) {
		return hotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs.contains(hotel);
	}

	public void setHasAlreadyMadeABuildEntranceActionForThisHotelOnTheSquareHeIs(final Hotel hotel) {
		hotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs.add(hotel);
	}
	
	public void resetHotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs() {
		hotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs.clear();
	}
	
	public boolean hasRefusedAnymoreActionsOnTheSquareHeIs() {
		return hasRefusedAnymoreActionsOnTheSquareHeIs;
	}

	public void setHasRefusedAnymoreActionsOnTheSquareHeIs(final boolean hasRefusedAnymoreActionsOnTheSquareHeIs) {
		this.hasRefusedAnymoreActionsOnTheSquareHeIs = hasRefusedAnymoreActionsOnTheSquareHeIs;
	}
	
	public boolean hasAlreadyMadeAPayForStayActionOnTheSquareHeIs() {
		return hasAlreadyMadeAPayForStayActionOnTheSquareHeIs;
	}
	
	public void setHasAlreadyMadeAPayForStayActionOnTheSquareHeIs(final boolean hasAlreadyMadeAPayForStayActionOnTheSquareHeIs) {
		this.hasAlreadyMadeAPayForStayActionOnTheSquareHeIs = hasAlreadyMadeAPayForStayActionOnTheSquareHeIs;
	}

	public void giveMoneyToBank(final double amount)
			throws NotEnoughMoneyAvailableException {
		Game.getSingleton().getBoard().getBank().receiveMoney(giveMoney(amount));
	}

	private int normalizePosition(int positionOnBoard) {
		if (positionOnBoard > Game.getSingleton().getBoard().getNumberOfSquares())
			positionOnBoard -= Game.getSingleton().getBoard().getNumberOfSquares();

		return positionOnBoard;
	}

	private void setPositionOnBoard(int positionOnBoard) {
		positionOnBoard = normalizePosition(positionOnBoard);

		this.positionOnBoard = positionOnBoard;
		final Square square = Game.getSingleton().getBoard().getSquareAtPosition(positionOnBoard);
		square.getStandingPlayers().add(this);
	}

	public int getPositionOnBoard() {
		return positionOnBoard;
	}
	
	private void setPreviousPositionOnBoard(int previousPositionOnBoard) {
		previousPositionOnBoard = normalizePosition(previousPositionOnBoard);
		
		this.previousPositionOnBoard = previousPositionOnBoard;
	}

	public int getPreviousPositionOnBoard() {
		return previousPositionOnBoard;
	}

	public Square getStandingSquare() {
		return Game.getSingleton().getBoard().getSquareAtPosition(getPositionOnBoard());
	}

	public void addOwnedHotel(final Hotel hotel) throws HotelAlreadyBoughtException {
		if (getOwnedHotels().contains(hotel)) {
			throw new HotelAlreadyBoughtException(hotel.getName()
					+ " is already owned by " + getName());
		}

		getOwnedHotels().add(hotel);
	}

	public void removeOwnedHotel(final Hotel hotel)
			throws CannotExpropriateUnboughtHotelException {
		if (!getOwnedHotels().contains(hotel)) {
			throw new CannotExpropriateUnboughtHotelException(hotel.getName()
					+ " is already owned by " + getName());
		}

		getOwnedHotels().remove(hotel);
	}

	public void moveForward(final int i) {
		final int newPos = normalizePosition(getPositionOnBoard() + i);

		if (Game.getSingleton().getBoard().getSquareAtPosition(newPos).getStandingPlayers().isEmpty()) {
			final Square oldSquare = Game.getSingleton().getBoard().getSquareAtPosition(getPositionOnBoard());
			oldSquare.getStandingPlayers().remove(this);
			setPreviousPositionOnBoard(getPositionOnBoard());
			setPositionOnBoard(newPos);
			setHasAlreadyMadeABuildActionOnTheSquareHeIs(false);
			setHasAlreadyMadeAPurchaseActionOnTheSquareHeIs(false);
			setHasAlreadyMadeAnExpropriateActionOnTheSquareHeIs(false);
			setHasRefusedAnymoreActionsOnTheSquareHeIs(false);
			setHasAlreadyMadeAFreeEntranceActionOnTheSquareHeIs(false);
			resetHotelsForWhomHasAlreadyMadeAnEntranceActionOnTheSquareHeIs();
		} else
			moveForward(i + 1);
	}

	public boolean mustRollDice() {
		return mustRollDice;
	}

	public void setMustRollDice(final boolean mustRollDice) {
		this.mustRollDice = mustRollDice;
	}

	public void play() throws HotelException {
		// If game has been made non-active by a previous
		// action of the same user, the method must return
		while(!Game.getSingleton().isTerminating() && existsAction() && Game.getSingleton().getPlayers().getAll().contains(this)) {
			executeMandatoryAndNonDelayableActions();
			executeNonMandatoryActions();
			executeMandatoryAndDelayableActions();
		}
	}
	
	private boolean existsAction() throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		return getStandingSquare().getAvailableActions(this).size() > 0;
	}

	private PlayerAction nextMandatoryAndNonDelayableAction()
			throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		for (final PlayerAction action : getStandingSquare().getAvailableActions(this)) {
			if (action.isMandatory() && !action.isDelayable())
				return action;
		}

		return null;
	}

	private PlayerAction nextMandatoryAndDelayableAction()
			throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		for (final PlayerAction action : getStandingSquare().getAvailableActions(this)) {
			if (action.isMandatory() && action.isDelayable())
				return action;
		}

		return null;
	}

	private void executeMandatoryAndNonDelayableActions() throws HotelException {
		for (PlayerAction action = nextMandatoryAndNonDelayableAction(); action != null && !Game.getSingleton().isTerminating() && Game.getSingleton().getPlayers().getAll().contains(this); action = nextMandatoryAndNonDelayableAction()) {
			action.executeAction();
		}
	}

	private void executeMandatoryAndDelayableActions() throws HotelException {
		for (PlayerAction action = nextMandatoryAndDelayableAction(); action != null && !Game.getSingleton().isTerminating() && Game.getSingleton().getPlayers().getAll().contains(this); action = nextMandatoryAndDelayableAction()) {
			action.executeAction();
		}
	}

	private List<PlayerAction> getNonMandatoryActions()
			throws PlayerNotInTheGameException, NoMoreBuildingsToBuildException {
		final List<PlayerAction> list = new ArrayList<PlayerAction>();

		for (final PlayerAction action : getStandingSquare().getAvailableActions(this)) {
			if (!action.isMandatory())
				list.add(action);
		}

		return list;
	}
	
	private List<Choosable> getChoosableActions(final List<PlayerAction> actions) {
		final List<Choosable> list = new ArrayList<Choosable>();

		for (final PlayerAction action : actions) {
			if (action instanceof Choosable)
				list.add((Choosable) action);
		}

		return list;
	}
	
	private void executeNonMandatoryActions() throws HotelException {
		for (List<PlayerAction> list = getNonMandatoryActions(); list.size() > 0 && !Game.getSingleton().isTerminating() && Game.getSingleton().getPlayers().getAll().contains(this); list = getNonMandatoryActions()) {
			list = getNonMandatoryActions();
			
			Action actionToBeExecuted = null;
			while(actionToBeExecuted == null && !hasRefusedAnymoreActionsOnTheSquareHeIs() && !Game.getSingleton().isTerminating()) {
				// Does not expose the original list due to security reasons			
				final List<Choosable> toChooseOriginalCopy = getChoosableActions(list);
				final List<Choosable> toChooseClientCopy = new ArrayList<Choosable>(toChooseOriginalCopy);
				
				// Asks for a choice
				final PlayerActionSelectionAction askForInputAction = new PlayerActionSelectionAction(this, toChooseClientCopy);
				askForInputAction.executeAction();
				
				final Integer choice = askForInputAction.getInputAsInteger();
				if (choice == null) {
					setHasRefusedAnymoreActionsOnTheSquareHeIs(true);
					break;
				}
				
				try {
					actionToBeExecuted = (Action) toChooseOriginalCopy.get(choice - 1);
				} catch (final IndexOutOfBoundsException e) {
					(new WrongInputAction(this)).executeAction();
				}
			}
			
			if(actionToBeExecuted != null)
				actionToBeExecuted.executeAction();
		}
	}

	public double getPatrimony() {
		double patrimony = getAvailableMoney();
		
		for(final Hotel hotel : getOwnedHotels()) {
			patrimony += hotel.getActualValue();
		}
		
		return patrimony;
	}
	
	public double getSellPatrimony() {
		double patrimony = getAvailableMoney();
		
		for(final Hotel hotel : getOwnedHotels()) {
			patrimony += hotel.getActualSellValue();
		}
		
		return patrimony;
	}

	public String getColor() {
		return color;
	}

	// Warning: This condition is buggy if track's lenght is minor than dice's size
	public boolean hasSteppedThrough(final int position) {
		
		return
		// Classic case: position is between the previous and actual player's position
		(getPreviousPositionOnBoard() < position && getPositionOnBoard() >= position) ||
		// Lap has been completed: player's actual position is lower than the previous one
		(getPositionOnBoard() < getPreviousPositionOnBoard() && 
				(getPositionOnBoard() >= position || position > getPreviousPositionOnBoard()))
		;
	}
	
	public void sellUntilCanSettleDebt(final double owed) throws HotelException {
		// Sells buildings and hotels until reaches the owed amount
		while(!Game.getSingleton().isTerminating() && getAvailableMoney() < owed) {
			final List<Choosable> sellActionList = new ArrayList<Choosable>();
			for(final Hotel hotel : getOwnedHotels()) {
				if(hotel.isSellable()) {
					sellActionList.add(new SellAction(this, hotel));
				}
			}
			final PlayerActionSelectionAction pasa = new PlayerActionSelectionAction(this, sellActionList);
            pasa.setSkippable(false);
			pasa.executeAction();
			
			final SellAction sellAction = (SellAction) sellActionList.get(pasa.getInputAsInteger() - 1);
			sellAction.executeAction();
		}
	}
	
	public void sellAll() throws HotelException {
		// Sells buildings and hotels until reaches the owed amount
		while(!Game.getSingleton().isTerminating() && !getOwnedHotels().isEmpty()) {
			// Copies the list to avoid ConcurrentModificationException
			final List<Hotel> ownedHotels = new ArrayList<Hotel>(getOwnedHotels());
			for(final Hotel hotel : ownedHotels) {
				if(hotel.isSellable()) {
					hotel.sellAll();
				}
			}
		}
	}
}

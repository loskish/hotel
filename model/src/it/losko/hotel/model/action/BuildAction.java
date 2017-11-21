package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.BuildingSquare;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.BuildPermissionNotGrantedException;
import it.losko.hotel.model.exception.BuildUnboughtHotelException;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Some building is being built. This happens
 * when a player lands on a {@link BuildingSquare} and
 * decides to roll the building dice. 
 * 
 * @author losko
 */
public class BuildAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = 5212734840725156212L;
	
	private boolean permissionDenied = false;
	private boolean notEnoughMoney = false;
	private Integer buildingCoeff;
	private final Building building;

	public BuildAction(final Player doingPlayer, final Hotel hotel) throws NoMoreBuildingsToBuildException {
		super(doingPlayer, hotel);
		building = hotel.getNextBuilding();
	}

	@Override
	public void doAction() throws NoMoreBuildingsToBuildException, BuildUnboughtHotelException, NotEnoughMoneyAvailableException, BuildPermissionNotGrantedException {
		try {
			buildingCoeff = getHotel().build();
		} catch (final BuildPermissionNotGrantedException e) {
			permissionDenied = true;
		} catch (final NotEnoughMoneyAvailableException e) {
			notEnoughMoney = true;
		} finally {
			getDoingPlayer().setHasAlreadyMadeABuildActionOnTheSquareHeIs(true);
		}
	}
	
	@Override
	protected void postDoAction() throws HotelException {
		super.postDoAction();
		
		if(permissionDenied) {
			final BuildPermissionDeniedAction bpnga = new BuildPermissionDeniedAction(getDoingPlayer(), getHotel());
			bpnga.executeAction();
		} else if(buildingCoeff != null && buildingCoeff == 0) {
			final BuildPermissionGrantedForFreeAction bpza = new BuildPermissionGrantedForFreeAction(getDoingPlayer(), getHotel());
			bpza.executeAction();
		} else if(buildingCoeff != null && buildingCoeff == 2) {
			final BuildPermissionGrantedForDoubledPriceAction bpda = new BuildPermissionGrantedForDoubledPriceAction(getDoingPlayer(), getHotel());
			bpda.executeAction();
		} else if(notEnoughMoney) {
			// The coefficient can only be 2. // The building hasn't yet been built, so the alternative constructor is used
			final BuildPermissionGrantedForDoubledPriceAction bpda = new BuildPermissionGrantedForDoubledPriceAction(getDoingPlayer(), getHotel(), building);
			bpda.executeAction();
			
			final double owed = building.getConstructionPrice() * 2;
			if(getDoingPlayer().getSellPatrimony() < owed) {
				getDoingPlayer().sellAll();
				getDoingPlayer().giveMoneyToBank(getDoingPlayer().getAvailableMoney());
				
				// Leave action
				final LeaveAction leaveAction = new LeaveAction(getDoingPlayer());
				leaveAction.executeAction();
			} else {
				getDoingPlayer().sellUntilCanSettleDebt(owed);

				// If the hotel is still owned by the player (whom could have sold it to
				// settle its debt), than the player is now allowed to build it.
				if(getDoingPlayer().equals(getHotel().getOwner())) {
					// The hotel is now buildable. Manually gives the owed
					// amount to the Bank and builds the chosen building
					getDoingPlayer().giveMoneyToBank(owed);
					getHotel().build(true);
				}
			}
		} else {
			final BuildPermissionGrantedAction bpg = new BuildPermissionGrantedAction(getDoingPlayer(), getHotel());
			bpg.executeAction();
		}
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " attempts to build " + getHotel().getName() + "'s " + building.getName() + ", regular price: " + building.getConstructionPrice();
	}

	@Override
	public String getChoiceDescription() {
		return "Build " + getHotel().getName() + "'s " + building.getName() + ", regular price: " + building.getConstructionPrice();
	}
}

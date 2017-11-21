package it.losko.hotel.model.action;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.exception.HotelException;
import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

import java.util.Random;

/**
 * A player stays some number of nights at an hotel.
 * This happens when a player lands on a square with
 * an entrance whose he is not the owner.
 * 
 * @author losko
 */
public class PayForStayAction extends HotelAction implements Choosable {
	private static final long serialVersionUID = -9176308109545837588L;

	private int numberOfNights;
	private boolean notEnoughMoney = false;

	public PayForStayAction(final Player doingPlayer, final Hotel hotel) {
		super(doingPlayer, hotel);
		setMandatory(true);
	}
	
	/**
	 * Returns the number of nights that user will spend
	 * 
	 * @return the number of nights that user will spend
	 */
	public int getNumberOfNights() {
		return numberOfNights;
	}
	
	/**
	 * Sets the number of nights that user will spend.
	 * This method is private because only the class
	 * itself must have the right to modify this parameter
	 * 
	 * @param numberOfNights the number of nights that user will spend
	 */
	private void setNumberOfNights(final int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	@Override
	public void doAction() {
		final Random random = new Random();
		setNumberOfNights(random.nextInt(6) + 1);
		
		try {
			getDoingPlayer().giveMoneyToPlayer(getHotel().getOwner(), getHotel().getPriceForStay(getNumberOfNights()));
		} catch (final NotEnoughMoneyAvailableException e) {
			notEnoughMoney = true;
		} finally {
			// This instruction must be called always, even if
			// a NotEnoughMoneyAvailableException has been thrown
			getDoingPlayer().setHasAlreadyMadeAPayForStayActionOnTheSquareHeIs(true);
		}
	}
	
	@Override
	protected void postDoAction() throws HotelException {
		super.postDoAction();
		
		if(notEnoughMoney) {
			final double owed = getHotel().getPriceForStay(getNumberOfNights());
			if(getDoingPlayer().getSellPatrimony() < owed) {
				getDoingPlayer().sellAll();
				getDoingPlayer().giveMoneyToPlayer(getHotel().getOwner(), getDoingPlayer().getAvailableMoney());
				
				// Leave action
				final LeaveAction leaveAction = new LeaveAction(getDoingPlayer());
				leaveAction.executeAction();
			} else {
				getDoingPlayer().sellUntilCanSettleDebt(owed);
				getDoingPlayer().giveMoneyToPlayer(getHotel().getOwner(), owed);
			}
		}
	}

	@Override
	public String getNotifiableDescription() {
		return getDoingPlayer().getName() + " stays at " + getHotel().getName() + " for " + getNumberOfNights() + " nights (" + getHotel().getPriceForStay(getNumberOfNights()) + ")";
	}

	public String getChoiceDescription() {
		return "Stay at " + getHotel().getName();
	}
}

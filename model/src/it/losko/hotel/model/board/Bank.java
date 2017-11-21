package it.losko.hotel.model.board;

import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.money.MoneyOwnerInterface;
import it.losko.hotel.model.player.Player;

/**
 * The Bank property. This is the entity that owns
 * and distributes all of the game resources.
 * 
 * @author losko
 */
public class Bank extends Property implements MoneyOwnerInterface {
	private static final long serialVersionUID = 5206903298382340035L;

	private double availableMoney;
	
	protected Bank() {
		super("Bank");
		
		/* Banknotes
		 *   50 x 20
		 *  100 x 20
		 *  500 x 20
		 * 1000 x 20
		 * 5000 x 20 */
		setAvailableMoney(133000);
	}

	/**
	 * Defines the interest amount earned by
	 * each player when steps through the bank square
	 * 
	 * @return the interest amount
	 */
	public double getInterestAmount() {
		return 2000;
	}
	
	/**
	 * Checks whether the specified amount of money is available
	 * 
	 * @param amount the amount to be checked
	 * @return true if the specified amount of money is available, false otherwise
	 */
	private boolean isMoneyAvailable(final double amount) {
		return availableMoney >= amount;
	}
	
	/**
	 * Withdraws the specified amount of money
	 * 
	 * @param amount The amount to be withdrawn
	 * @return The withdrawn amount
	 */
	private double giveMoney(final double amount) throws NotEnoughMoneyAvailableException {
		if(!isMoneyAvailable(amount)) {
			throw new NotEnoughMoneyAvailableException(getName() + " has not enough money");
		}
		
		availableMoney -= amount;
		return amount;
	}

	@Override
	public double getAvailableMoney() {
		return availableMoney;
	}

	@Override
	public void giveMoneyToPlayer(final Player player, final double amount) throws NotEnoughMoneyAvailableException {
		player.receiveMoney(giveMoney(amount));
	}


	@Override
	public void receiveMoney(final double amount) {
		setAvailableMoney(getAvailableMoney() + amount);
	}

	@Override
	public void setAvailableMoney(final double availableMoney) {
		this.availableMoney = availableMoney;
	}
}

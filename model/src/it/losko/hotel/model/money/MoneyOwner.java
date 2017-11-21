package it.losko.hotel.model.money;

import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

import java.io.Serializable;

/**
 * A generic entity that owns an amount of money
 * 
 * @author losko
 */
public class MoneyOwner implements MoneyOwnerInterface, Serializable {
	private static final long serialVersionUID = -6475353236004886106L;

	private final String name;
	private double availableMoney;
	
	public MoneyOwner(final String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the owner
	 * 
	 * @return the name of the owner
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Withdraws the specified amount of money from the owner
	 * 
	 * @param amount The amount to be withdrawn
	 * @return the withdrawn amount
	 */
	protected double giveMoney(final double amount) throws NotEnoughMoneyAvailableException {
		if(!isMoneyAvailable(amount)) {
			throw new NotEnoughMoneyAvailableException(getName() + " has not enough money");
		}
		
		availableMoney -= amount;
		return amount;
	}
	
	/**
	 * Checks whether the specified amount of money is available
	 * 
	 * @param amount Amount to be checked
	 * @return true if the specified amount of money is available, false otherwise
	 */
	public boolean isMoneyAvailable(final double amount) {
		return availableMoney >= amount;
	}
	
	public void setAvailableMoney(final double availableMoney) {
		this.availableMoney = availableMoney;
	}

	public double getAvailableMoney() {
		return availableMoney;
	}
	
	public void giveMoneyToPlayer(final Player player, final double amount) throws NotEnoughMoneyAvailableException {
		player.receiveMoney(giveMoney(amount));
	}
	
	public void receiveMoney(final double amount) {
		setAvailableMoney(getAvailableMoney() + amount);
	}
}

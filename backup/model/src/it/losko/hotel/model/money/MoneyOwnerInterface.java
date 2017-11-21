package it.losko.hotel.model.money;

import it.losko.hotel.model.exception.NotEnoughMoneyAvailableException;
import it.losko.hotel.model.player.Player;

/**
 * Interface that applies to every entity that owns some money
 * 
 * @author losko
 */
public interface MoneyOwnerInterface {
	
	/**
	 * Set the amount of available money
	 * @param availableMoney The amount of available money
	 */
	public void setAvailableMoney(final double availableMoney);
	
	/**
	 * Returns the amount of available money
	 * @return the amount of available money
	 */
	public double getAvailableMoney();
	
	/**
	 * Transfers a money amount from a player to another one
	 * 
	 * @param player	The player whom will the money be transferred to
	 * @param amount	The amount that has to be transferred
	 */
	public void giveMoneyToPlayer(final Player player, final double amount) throws NotEnoughMoneyAvailableException;
	
	/**
	 * Receives an amount of money
	 * 
	 * @param amount	The amount that has to be received
	 */
	public void receiveMoney(final double amount);
}

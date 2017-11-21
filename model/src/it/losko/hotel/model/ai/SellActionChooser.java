package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.HotelAction;
import it.losko.hotel.model.action.SellAction;
import it.losko.hotel.model.board.Hotel;

import java.util.Set;

public class SellActionChooser extends ActionChooser {
	private SellActionChooser() {
		getCompatible().add(SellAction.class);
	}
	
	private static class SingletonHolder { 
		private static final SellActionChooser singleton = new SellActionChooser();
	}
	
	public static SellActionChooser getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Chooses a build Action between the specified ones. The chosen action is returned
	 * 
	 * @param possibleActions	The actions among whom the choice has to be made
	 * @return					The chosen action
	 */
	public Choosable choose(final Set<Choosable> possibleActions) {
		checkActionsCompatibility(possibleActions);
		
		double higherIncome = 0;
		Choosable choice = null;
		
		// Building actions
		for(final Choosable action : possibleActions) {
			final Hotel hotel = ((HotelAction) action).getHotel();
			final double income = evaluateChoice(hotel);
			
			if(choice == null) {
				higherIncome = income;
				choice = action;
			} else {
				if(income > higherIncome) {
					higherIncome = income;
					choice = action;
				}
			}
		}
		
		return choice;
	}
	
	private double evaluateChoice(final Hotel hotel) {
		final double priceEvaluation = choicePriceEvaluation(hotel);
		final double gainEvaluation = choiceGainEvaluation(hotel);
		
		return gainEvaluation / priceEvaluation;
	}
	
	private double choicePriceEvaluation(final Hotel hotel) {
		return hotel.isAtLeastOneBuildingBuilt() ? hotel.getLastBuiltBuilding().getPricePerNight() : 1;
	}
	
	private double choiceGainEvaluation(final Hotel hotel) {
		return hotel.isAtLeastOneBuildingBuilt() ? hotel.getLastBuiltBuilding().getSellPrice() : hotel.getLandSellPrice();
	}
}

package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.ExpropriateAction;
import it.losko.hotel.model.action.HotelAction;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExpropriateActionChooser extends ActionChooser {
	private ExpropriateActionChooser() {
		getCompatible().add(ExpropriateAction.class);
	}
	
	private static class SingletonHolder { 
		private static final ExpropriateActionChooser singleton = new ExpropriateActionChooser();
	}
	
	public static ExpropriateActionChooser getSingleton() {
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
		return hotel.getExpropriationPrice();
	}
	
	private double choiceGainEvaluation(final Hotel hotel) {
		final List<Square> suitable = new ArrayList<Square>();
		for(final Square square : hotel.getAdjacentSquares()) {
			if(!square.hasEntrance()) {
				suitable.add(square);
			}
		}
		
		return suitable.size();
	}
}

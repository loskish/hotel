package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.BuildEntranceAction;
import it.losko.hotel.model.action.BuildEntranceForFreeAction;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.HotelAction;
import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.game.Game;

import java.util.Set;

public class BuildEntranceActionChooser extends ActionChooser {
	private BuildEntranceActionChooser() {
		getCompatible().add(BuildEntranceAction.class);
		getCompatible().add(BuildEntranceForFreeAction.class);
	}
	
	private static class SingletonHolder { 
		private static final BuildEntranceActionChooser singleton = new BuildEntranceActionChooser();
	}
	
	public static BuildEntranceActionChooser getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Chooses an entrance Action between the specified ones. The chosen action is returned
	 * 
	 * @param possibleActions	The actions among whom the choice has to be made
	 * @return					The chosen action
	 */
	public Choosable choose(final Set<Choosable> possibleActions) {
		checkActionsCompatibility(possibleActions);
		
		double higherIncome = 0;
		Choosable choice = null;
		double otherPlayerDamage = 0;
		
		// Building actions
		for(final Choosable action : possibleActions) {
			final Hotel hotel = ((HotelAction) action).getHotel();
			final double income = evaluateChoice(hotel, action instanceof BuildEntranceForFreeAction);
			
			if(choice == null) {
				higherIncome = income;
				choice = action;
			} else {
				if(income > higherIncome) {
					higherIncome = income;
					choice = action;
				} else if(income == higherIncome) {
					// In equality conditions the square that causes higher damage to other players is chosen
					final BuildEntranceAction bea = (BuildEntranceAction) action;
					
					// Searches between adjacent other player's built hotels
					boolean causesDamage = false;
					for(final Hotel adjHotel : bea.getSquare().getAdjacentHotelsSuitableForBuildingEntrance()) {
						if(!hotel.getOwner().equals(adjHotel.getOwner())) {
							if(adjHotel.isAtLeastOneBuildingBuilt()) {
								final Building adjBuilding = adjHotel.getLastBuiltBuilding();
								if(adjBuilding.getPricePerNight() > otherPlayerDamage) {
									choice = action;
									otherPlayerDamage = adjBuilding.getPricePerNight();
									causesDamage = true;
								}
							}
						}
					}
					
					// Searches between adjacent other player's hotels
					if(!causesDamage) {
						for(final Hotel adjHotel : bea.getSquare().getAdjacentHotels()) {
							if(!hotel.getOwner().equals(adjHotel.getOwner())) {
								if(adjHotel.isAtLeastOneBuildingBuilt()) {
									final Building adjBuilding = adjHotel.getLastBuiltBuilding();
									if(adjBuilding.getPricePerNight() > otherPlayerDamage) {
										choice = action;
										otherPlayerDamage = adjBuilding.getPricePerNight();
										causesDamage = true;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return choice;
	}
	
	private double evaluateChoice(final Hotel hotel, final boolean forFree) {
		final double priceEvaluation = choicePriceEvaluation(hotel, forFree);
		final double gainEvaluation = choiceGainEvaluation(hotel, Config.getSingleton().getParameterValueAsInteger(ConfigConstants.PARAM_NUMBER_OF_LAP_FORECASTING));
		
		return gainEvaluation / priceEvaluation;
	}
	
	private double choicePriceEvaluation(final Hotel hotel, final boolean forFree) {
		double expectedPrice = 0;
		
		if(!forFree) {
			expectedPrice = hotel.getEntrancePrice();
		}
		
		return expectedPrice;
	}
	
	private double choiceGainEvaluation(final Hotel hotel, final int numberOfLapsForecasting) {
		final Building building = hotel.getLastBuiltBuilding();
		
		final double probabilityThatNobodyLandsOnASingleEntrance = Math.pow(
				1D / Game.getSingleton().getBoard().getNumberOfSquares(),
				Game.getSingleton().getPlayers().getAll().size() - 1
		);
		
		final double probabilityInNLapsThatNobodyLandsOnASingleEntrance = Math.pow(probabilityThatNobodyLandsOnASingleEntrance, numberOfLapsForecasting);
		
		return building.getPricePerNight() 
			* ArtificialIntelligence.getSingleton().getExpectedDiceValue()
			* (1 - probabilityInNLapsThatNobodyLandsOnASingleEntrance);
	}
}

package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.BuildAction;
import it.losko.hotel.model.action.BuildForFreeAction;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.HotelAction;
import it.losko.hotel.model.board.Building;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.conf.Config;
import it.losko.hotel.model.conf.ConfigConstants;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;
import it.losko.hotel.model.game.Game;

import java.util.HashSet;
import java.util.Set;

public class BuildActionChooser extends ActionChooser {
	private BuildActionChooser() {
		getCompatible().add(BuildAction.class);
		getCompatible().add(BuildForFreeAction.class);
	}
	
	private static class SingletonHolder { 
		private static final BuildActionChooser singleton = new BuildActionChooser();
	}
	
	public static BuildActionChooser getSingleton() {
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
	public Choosable choose(final Set<Choosable> possibleActions) throws NoMoreBuildingsToBuildException {
		checkActionsCompatibility(possibleActions);
		
		double higherIncome = 0;
		Choosable choice = null;
		
		// Building actions
		for(final Choosable action : possibleActions) {
			final Hotel hotel = ((HotelAction) action).getHotel();
			final double income = evaluateChoice(hotel, action instanceof BuildForFreeAction);
			
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
	
	/**
	 * Executes a merely probabilistic extime of the income resulting from building the hotel 
	 * 
	 * @param hotel		The hotel to build
	 * @param forFree	Indicates if the hotel is being built for free
	 * @return			a merely probabilistic extime of the income resulting from building the hotel
	 */
	private double evaluateChoice(final Hotel hotel, final boolean forFree) throws NoMoreBuildingsToBuildException {
		final double priceEvaluation = buildChoicePriceEvaluation(hotel, forFree);
		final double gainEvaluation = buildChoiceGainEvaluation(hotel, Config.getSingleton().getParameterValueAsInteger(ConfigConstants.PARAM_NUMBER_OF_LAP_FORECASTING));
		
		return gainEvaluation / priceEvaluation;
	}
	
	/**
	 * Estimates the expected price for building the specified hotel
	 * 
	 * @param hotel		The hotel to build
	 * @param forFree	Indicates if the hotel is being built for free
	 * @return 			The expected price for building the specified hotel
	 */
	private double buildChoicePriceEvaluation(final Hotel hotel, final boolean forFree) throws NoMoreBuildingsToBuildException {
		final Building building = hotel.getNextBuilding();
		
		double expectedBuildingPrice = 0;
		
		if(!forFree) {
			expectedBuildingPrice = building.getConstructionPrice() * ArtificialIntelligence.getSingleton().getExpectedBuildingDiceValue();
		}
		
		return expectedBuildingPrice;
	}
	
	/**
	 * Estimates the expected gain from building the specified hotel
	 * 
	 * @param hotel		The hotel to be built
	 * @return 			The potential gain of building the specified hotel
	 */
	private double buildChoiceGainEvaluation(final Hotel hotel, final int numberOfLapsForecasting) throws NoMoreBuildingsToBuildException {
		final Building building = hotel.getNextBuilding();
		
		return building.getPricePerNight() 
			* ArtificialIntelligence.getSingleton().getExpectedDiceValue()
			* calculateProbabilityThatAtLeastOnePlayerStaysAtHotel(hotel, numberOfLapsForecasting);
	}
	
	/**
	 * Calculates the probability that at least one player lands on one
	 * of the hotel's entrances in the specified number of laps. The
	 * higher is the number of laps, the less precise is the result.
	 * 
	 * @param hotel The hotel where other players are expected to stay
	 * @param laps	The number of laps to wait for a player to stay. Minimum value is 1
	 * @return 		The probability that at least one player lands on one
	 * 				of the hotel's entrances in the specified number of laps
	 */
	private double calculateProbabilityThatAtLeastOnePlayerStaysAtHotel(final Hotel hotel, final int laps) {
		final double probabilityThatNobodyLandsOnHotelEntries = Math.pow(
				(double) (Game.getSingleton().getBoard().getNumberOfSquares() - hotel.getAdjacentSquaresWithABuiltEntrance().size()) / Game.getSingleton().getBoard().getNumberOfSquares(),
				Game.getSingleton().getPlayers().getAll().size() - 1
		);
		
		final double probabilityThatNobodyLandsOnASingleEntrance = Math.pow(
				1D / Game.getSingleton().getBoard().getNumberOfSquares(),
				Game.getSingleton().getPlayers().getAll().size() - 1
		);
		
		double probabilityInNLapsThatNobodyLandsOnHotelEntries = probabilityThatNobodyLandsOnHotelEntries;
		for(int i = 2; i <= laps; i++) {
			// This is an aproximation since is given that probability to build
			// an entrance doesn't change with time (meant as number of laps)
			probabilityInNLapsThatNobodyLandsOnHotelEntries = probabilityInNLapsThatNobodyLandsOnHotelEntries * (probabilityThatNobodyLandsOnHotelEntries - calculateProbabilityToBuildAnEntranceToTheHotel(hotel) * probabilityThatNobodyLandsOnASingleEntrance);
		}
		
		return 1 - probabilityInNLapsThatNobodyLandsOnHotelEntries;
	}
	
	/**
	 * Calculates the probability to build an entrance to the hotel
	 * 
	 * @param hotel The hotel where to build the entrance
	 * @return the probability to build an entrance to the hotel
	 */
	private double calculateProbabilityToBuildAnEntranceToTheHotel(final Hotel hotel) {
		double probabilityInOneLapForOthers = 1;
		for(final Square square : hotel.getAdjacentSquaresSuitableForBuildingEntrance()) {
			final Set<Hotel> otherOwnersHotels = new HashSet<Hotel>();
			for(final Hotel adjacentHotel : square.getAdjacentHotels()) {
				if(!hotel.getOwner().equals(adjacentHotel.getOwner()))
					otherOwnersHotels.add(adjacentHotel);
			}
			
			// This is an aproximation in the following worst-case-condition:
			// Concurrent players can always build more than 1 entrance per hotel
			probabilityInOneLapForOthers *= (double) otherOwnersHotels.size() / square.getAdjacentHotels().size();
		}
		
		final double probabilityInOneLap = 1 - probabilityInOneLapForOthers;
		
		final double probability = probabilityInOneLap;
		
		return probability;
	}
}

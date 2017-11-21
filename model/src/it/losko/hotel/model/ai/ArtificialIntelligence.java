package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.BuildAction;
import it.losko.hotel.model.action.BuildEntranceAction;
import it.losko.hotel.model.action.BuildForFreeAction;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.action.ExpropriateAction;
import it.losko.hotel.model.action.PurchaseAction;
import it.losko.hotel.model.action.SellAction;
import it.losko.hotel.model.exception.NoMoreBuildingsToBuildException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArtificialIntelligence {
	private final double expectedDiceValue;
	private final double expectedBuildingDiceValue;
	
	private ArtificialIntelligence() {
		expectedDiceValue = 3.5;
		expectedBuildingDiceValue = 1;
	}
	
	private static class SingletonHolder { 
		private static final ArtificialIntelligence singleton = new ArtificialIntelligence();
	}
	
	public static ArtificialIntelligence getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public double getExpectedDiceValue() {
		return expectedDiceValue;
	}

	public double getExpectedBuildingDiceValue() {
		return expectedBuildingDiceValue;
	}
	
	public Integer chooseAction(final List<Choosable> possibleActions) throws NoMoreBuildingsToBuildException {
		final Set<Choosable> buildActions = new LinkedHashSet<Choosable>();
		final Set<Choosable> buildEntranceActions = new LinkedHashSet<Choosable>();
		final Set<Choosable> expropriateActions = new LinkedHashSet<Choosable>();
		final Set<Choosable> purchaseActions = new LinkedHashSet<Choosable>();
		final Set<Choosable> sellActions = new LinkedHashSet<Choosable>();
		final Set<Choosable> otherActions = new LinkedHashSet<Choosable>();
		
		for(final Choosable action : possibleActions) {
			if(action instanceof BuildAction || action instanceof BuildForFreeAction) {
				buildActions.add(action);
			} else if(action instanceof BuildEntranceAction) {
				buildEntranceActions.add(action);
			} else if(action instanceof ExpropriateAction) {
				expropriateActions.add(action);
			} else if(action instanceof PurchaseAction) {
				purchaseActions.add(action);
			} else if(action instanceof SellAction) {
				sellActions.add(action);
			} else {
				otherActions.add(action);
			}
		}
		
		Choosable chosen = null;
		if(buildActions.size() > 0) {
			chosen = BuildActionChooser.getSingleton().choose(buildActions);
		} else if(buildEntranceActions.size() > 0) {
			chosen = BuildEntranceActionChooser.getSingleton().choose(buildEntranceActions);
		} else if(expropriateActions.size() > 0) {
			chosen = ExpropriateActionChooser.getSingleton().choose(expropriateActions);
		} else if(purchaseActions.size() > 0) {
			chosen = BuyActionChooser.getSingleton().choose(purchaseActions);
		} else if(sellActions.size() > 0) {
			chosen = SellActionChooser.getSingleton().choose(sellActions);
		} else {
			chosen = otherActions.iterator().next();
		}
		
		return possibleActions.indexOf(chosen) + 1;
	}
}

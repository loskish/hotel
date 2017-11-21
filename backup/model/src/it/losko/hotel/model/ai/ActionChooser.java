package it.losko.hotel.model.ai;

import it.losko.hotel.model.action.Action;
import it.losko.hotel.model.action.Choosable;
import it.losko.hotel.model.exception.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class ActionChooser {
	private final List<Class<? extends Action>> compatible;
	
	protected ActionChooser() {
		compatible = new ArrayList<Class<? extends Action>>();
	}
	
	public List<Class<? extends Action>> getCompatible() {
		return compatible;
	}
	
	protected final List<Class<? extends Action>> getCompatibilityList() {
		return getCompatible();
	}
		
	protected void checkActionsCompatibility(final Set<Choosable> possibleActions) {
		boolean found = false;
		
		for(final Choosable action : possibleActions) {
			for(final Class<? extends Action> clazz : getCompatibilityList()) {
				if(clazz.isInstance(action)) {
					found = true;
				}
			}
		}
		
		if(!found) {
			ExceptionHandler.getSingleton().handle(new IllegalArgumentException());
		}
	}
}

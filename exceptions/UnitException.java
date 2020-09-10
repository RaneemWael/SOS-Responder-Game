package exceptions;

import model.units.Unit;
import simulation.Rescuable;

public abstract class UnitException extends SimulationException{
	
	private Unit unit;
	private Rescuable target;
	
	public UnitException(Unit unit, Rescuable target) {
		super();
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public Rescuable getTarget() {
		return target;
	}
	
	public UnitException(Unit unit, Rescuable target, String message) {
		super(message);
	}
	
}

package exceptions;

import model.disasters.Disaster;

public abstract class SimulationException extends Exception{

	public SimulationException() {
		super();
	}
	
	public SimulationException(String message) {
		super(message);
	}
	
}

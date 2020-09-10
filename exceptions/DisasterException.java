package exceptions;

import model.disasters.Disaster;

public abstract class DisasterException extends SimulationException {
	
	private Disaster disaster;
	
	public Disaster getDisaster() {
			return disaster;
	}
	
	public DisasterException(Disaster disaster) {
		super();
	}
	
	public DisasterException(Disaster disaster,String message){
		super(message);
	}
	
}

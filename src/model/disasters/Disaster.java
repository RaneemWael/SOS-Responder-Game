package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	
	public String getTargetName() {
		if (getTarget() instanceof ResidentialBuilding)
			return "a building";
		else
			return ((Citizen) getTarget()).getName();
	}
	
	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		
		if(target instanceof ResidentialBuilding && ((ResidentialBuilding) target).getStructuralIntegrity()==0) 
			throw new BuildingAlreadyCollapsedException(this, "Can't strike a disaster on an already collapsed building");
		
		if(target instanceof Citizen && ((Citizen) target).getState()==CitizenState.DECEASED)
			throw new CitizenAlreadyDeadException(this, "Can't strike a disaster on an already dead citizen");
		
		target.struckBy(this);
		active=true;
				
	}
	
	public String getDisasterName() {
		if (this instanceof Collapse)
			return "Collapse";
		else if (this instanceof Fire)
			return "Fire";
		else if (this instanceof GasLeak)
			return "GasLeak";
		else if (this instanceof Injury)
			return "Injury";
		else if (this instanceof Infection)
			return "Infection";
		return null;
	}
}

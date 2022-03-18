package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;


public class Infection extends Disaster {

	public Infection(int startCycle, Citizen target) {
		super(startCycle, target);
	}
@Override
public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException 
{
	super.strike();
	Citizen target = (Citizen)getTarget();
	target.setToxicity(target.getToxicity()+25);
	
}
	@Override
	public void cycleStep() {
		Citizen target = (Citizen)getTarget();
		target.setToxicity(target.getToxicity()+15);
		
	}

}

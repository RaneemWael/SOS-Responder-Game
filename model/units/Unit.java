package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}
	
	public String getTypeName() {
		if (this instanceof Ambulance)
			return "Ambulance";
		else if (this instanceof GasControlUnit)
			return "Gascontrol unit";
		else if (this instanceof Evacuator)
			return "Evacuator";
		else if (this instanceof DiseaseControlUnit)
			return "Disease control unit";
		else if (this instanceof FireTruck)
			return "Fire truck";
		return null;
	}
	
	public String getTargetName() {
		if (this.getTarget() instanceof Citizen) {
			return "Citizen";
		}
		else
			return "Residential building";
	}
	
	public String getStateName() {
		if (this.getState() == UnitState.IDLE)
			return "Idle";
		else if (this.getState() == UnitState.RESPONDING)
			return "Responding";
		return "Treating";
	}
	
	public String getTypeAbb() {
		if (this instanceof Ambulance)
			return "A";
		else if (this instanceof GasControlUnit)
			return "GCU";
		else if (this instanceof Evacuator)
			return "E";
		else if (this instanceof DiseaseControlUnit)
			return "DCU";
		else if (this instanceof FireTruck)
			return "FT";
		return null;
	}

	@Override
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException{
		
		if(canTreat(r)==false) 
			throw new CannotTreatException(this, r, "Can't respond to a safe target");
		
		if(r instanceof Citizen && this instanceof GasControlUnit) 
			throw new IncompatibleTargetException(this,r,"Gas control unit can't respond to a citizen");
		
		if(this instanceof GasControlUnit  && !(r.getDisaster() instanceof GasLeak))
			throw new CannotTreatException(this,r,"Gas control unit can't respond to a disaster other than gas leak");
	
		if(r instanceof Citizen && this instanceof FireTruck) 
			throw new IncompatibleTargetException(this,r,"Fire truck can't respond to a citizen");
		

		if (this instanceof FireTruck && !(r.getDisaster() instanceof Fire)) 
			throw new CannotTreatException(this,r,"Fire truck can't respond to a disaster other than fire");
		
		if(r instanceof Citizen && this instanceof Evacuator)
			throw new IncompatibleTargetException(this,r,"Evacuator can't respond to a citizen");
		
		if (this instanceof Evacuator && !(r.getDisaster() instanceof Collapse)) 
			throw new CannotTreatException(this,r,"Evacuator can't respond to a disaster other than collapse");
		
		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	
	}
	

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
	public boolean canTreat(Rescuable r) {
		
		if(r instanceof Citizen) {
			
			if(((Citizen) r).getState() != CitizenState.SAFE)
				return true;
			
			/*if(((Citizen) r).getBloodLoss()>0 )
				return true;
			
			if(((Citizen) r).getToxicity()>0)
				return true;
			
			if(((Citizen) r).getHp()<100)
				return true;*/

		}
		
		else if(r instanceof ResidentialBuilding) {
			
			if(((ResidentialBuilding) r).getFireDamage()>0)
				return true;
	
			if(((ResidentialBuilding) r).getFoundationDamage()>0) 
				return true;
				
			if(((ResidentialBuilding) r).getGasLevel()>0)
				return true;
		
		}
		
		return false;
	
	}
	 
}

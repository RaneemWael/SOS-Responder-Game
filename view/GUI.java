package view;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import controller.CommandCenter;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.PoliceUnit;
import model.units.Unit;
import model.units.UnitState;


public class GUI extends JFrame implements ActionListener {
	
	private CommandCenter cc = new CommandCenter();
	private Container contentPane;
	private JPanel tips;
	private JLabel currentCycle;
	private JLabel casualties;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private JPanel rescuePanel;
	private JPanel buttons1;
	private JPanel infoPanel;
	private JPanel logPanel;
	private JPanel logBoxx;
	private JPanel activeDisastersPanel;
	private JPanel buildingDescription;
	private JPanel citizenDescription;
	private JPanel unitDescription;
	private JPanel idleUnits;
	private JPanel respondingUnits;
	private JPanel treatingUnits;
	private Box logBox;
	private JScrollPane log;
	private Box activeDisastersBox;
	private JPanel unitsPanel;
	private ResidentialBuilding buildingSOS = null;
	private Citizen citizenSOS = null;
	private Boolean cSOSclick = false;
	private Boolean bSOSclick = false;
	private ArrayList<Citizen> citizens1 = cc.getVisibleCitizens();
	private ArrayList<ResidentialBuilding> buildings1 = cc.getVisibleBuildings();
	
	public GUI () throws Exception {
			
		super();
		
		visibleBuildings = cc.getVisibleBuildings();
		visibleCitizens = cc.getVisibleCitizens();
		emergencyUnits = cc.getEmergencyUnits();
		
		setSize(1900,1000);
		WindowDestroyer myListener = new WindowDestroyer();
		addWindowListener(myListener);
		contentPane = getContentPane();
		setLocationRelativeTo(null);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout());
		
		tips = new JPanel();
		tips.setBorder(BorderFactory.createTitledBorder("Tips"));
		tips.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		tips.add(new JLabel("Hover over buttons on grid to view all components"));
		contentPane.add(tips, BorderLayout.SOUTH);
		
		rescuePanel = new JPanel ();
		rescuePanel.setSize(100,100);
		rescuePanel.setBackground(Color.WHITE);
		rescuePanel.setLayout(new GridLayout(10,10));
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				JReferencingButton button = new JReferencingButton();
				
				for (int b=0; b<visibleBuildings.size(); b++) {
					ResidentialBuilding b1 = visibleBuildings.get(b);
					if (b1.getLocation().getY()==i && b1.getLocation().getX()==j && b1.getOccupants().size()==0 && b1.getStructuralIntegrity()!=0) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + EB");
								button.setToolTipText("Empty Building + Empty building");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + EB");
								button.setToolTipText("Empty Building + Empty building");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + EB");
								button.setToolTipText("Ambulance + Empty building");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + EB");
								button.setToolTipText("Gas Control Unit + Empty building");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + EB");
								button.setToolTipText("Citizen + Empty building");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + EB");
								button.setToolTipText("Disease Control Unit + Empty building");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + EB");
								button.setToolTipText("Evacuator + Empty building");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + EB");
								button.setToolTipText("FireTruck + Empty building");
							}
							else {
								button.setText(button.getText() + " + EB");
								button.setToolTipText(button.getToolTipText() + " + Empty building");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Empty building");
							button.setToolTipText("Empty building");
						}
						button.setB(b1);
					}
					if (b1.getLocation().getY()==i && b1.getLocation().getX()==j && b1.getOccupants().size()!=0 && b1.getStructuralIntegrity()!=0) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + B");
								button.setToolTipText("Empty Building + Building with citizen(s)");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + B");
								button.setToolTipText("Empty Building + Building with citizen(s)");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + B");
								button.setToolTipText("Ambulance + Building with citizen(s)");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + B");
								button.setToolTipText("Gas Control Unit + Building with citizen(s)");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + B");
								button.setToolTipText("Citizen + Building with citizen(s)");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + B");
								button.setToolTipText("Disease Control Unit + Building with citizen(s)");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + B");
								button.setToolTipText("Evacuator + Building with citizen(s)");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + B");
								button.setToolTipText("FireTruck + Building with citizen(s)");
							}
							else {
								button.setText(button.getText() + " + B");
								button.setToolTipText(button.getToolTipText() + " + Building with citizen(s)");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Building with citizen(s)");
							button.setToolTipText("Building with citizen(s)");
						}
						button.setB(b1);
					}
				}
				
				for (int c=0; c<visibleCitizens.size(); c++) {
					Citizen c1 = visibleCitizens.get(c);
					if (c1.getLocation().getY()==i && c1.getLocation().getX()==j && c1.getState()!=CitizenState.DECEASED) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + C");
								button.setToolTipText("Empty Building + Citizen");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + C");
								button.setToolTipText("Empty Building + Citizen");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + C");
								button.setToolTipText("Ambulance + Empty building");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + C");
								button.setToolTipText("Gas Control Unit + Citizen");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + C");
								button.setToolTipText("Citizen + Citizen");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + C");
								button.setToolTipText("Disease Control Unit + Citizen");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + C");
								button.setToolTipText("Evacuator + Citizen");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + C");
								button.setToolTipText("FireTruck + Citizen");
							}
							else {
								button.setText(button.getText() + " + C");
								button.setToolTipText(button.getToolTipText() + " + Citizen");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Citizen");
							button.setToolTipText("Citizen");
						}
						button.setC(c1);
					}
				}
				
				for (int u=0; u<emergencyUnits.size(); u++) {
					Unit u1 = emergencyUnits.get(u);
					if (u1.getLocation().getY()==i && u1.getLocation().getX()==j) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + " + u1.getTypeAbb());
								button.setToolTipText("Empty Building + " + u1.getTypeName());
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + " + u1.getTypeAbb());
								button.setToolTipText("Building with citizen(s) + " + u1.getTypeName());
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + " + u1.getTypeAbb());
								button.setToolTipText("Ambulance + " +  u1.getTypeName());
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + " + u1.getTypeAbb());
								button.setToolTipText("Gas Control Unit + " + u1.getTypeName());
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + " + u1.getTypeAbb());
								button.setToolTipText("Citizen + " + u1.getTypeAbb());
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + " + u1.getTypeAbb());
								button.setToolTipText("Disease Control Unit + " + u1.getTypeName());
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + " + u1.getTypeAbb());
								button.setToolTipText("Evacuator + " + u1.getTypeName());
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + " + u1.getTypeAbb());
								button.setToolTipText("Fire Truck + " + u1.getTypeName());
							}
							else {
								button.setText(button.getText() + " + " + u1.getTypeAbb());
								button.setToolTipText(button.getToolTipText() + " + " + u1.getTypeName());
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText(u1.getTypeName());
							button.setToolTipText(u1.getTypeName());
						}
						button.setU(u1);
					}
				}
				
				button.addActionListener(this);
				rescuePanel.add(button);
			}
		}
		contentPane.add(rescuePanel, BorderLayout.CENTER);
		buttons1 = new JPanel ();
		buttons1.setBackground(Color.LIGHT_GRAY);
		buttons1.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		currentCycle = new JLabel("Current cycle: " + cc.getEngine().getCurrentCycle());
		casualties = new JLabel("Casualties: " + cc.getEngine().calculateCasualties());
		buttons1.add(currentCycle);
		buttons1.add(casualties);
		JButton nextCycle = new JButton ("Next Cycle");
		nextCycle.addActionListener(this);
		buttons1.add(nextCycle);
		contentPane.add(buttons1, BorderLayout.NORTH);
		
		infoPanel = new JPanel ();
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info Panel"));
		infoPanel.setSize(1000,1000);
		infoPanel.setBackground(Color.PINK);
		Box infoBox = Box.createVerticalBox();
		infoPanel.add(infoBox);
		
		
		logPanel = new JPanel();
		logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
		logBox = Box.createVerticalBox();
		logBoxx = new JPanel();
		logBoxx.add(logBox);
		log = new JScrollPane(logBoxx);
		log.setPreferredSize(new Dimension(300,200));
		logPanel.add(log);
		JLabel currentCycle = new JLabel("Cycle: " + cc.getEngine().getCurrentCycle());
		logBox.add(currentCycle);
		for (int i=0; i<cc.getEngine().getExecutedDisasters().size(); i++) {
			Disaster d = cc.getEngine().getExecutedDisasters().get(i);
			if (d.getStartCycle() == cc.getEngine().getCurrentCycle()) {
				JLabel disaster = new JLabel (d.getDisasterName() + " struck " + d.getTargetName());
				logBox.add(disaster);
			}
		}
		for (int i=0; i<citizens1.size(); i++) {
			Citizen c = citizens1.get(i);
			if (c.getState() == CitizenState.DECEASED) {
				JLabel deaths = new JLabel (c.getName() + " died at (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
				logBox.add(deaths);
				citizens1.remove(i);
				i--;
			}
		}
		for (int i=0; i<buildings1.size(); i++) {
			ResidentialBuilding b = buildings1.get(i);
			if (b.getStructuralIntegrity() == 0) {
				JLabel group = new JLabel("Group of citizens: ");
				logBox.add(group);
				for (int j=0; j<b.getOccupants().size(); j++) {
					Citizen c = b.getOccupants().get(j);
					if (c.getState() == CitizenState.DECEASED) {
						JLabel deaths = new JLabel (c.getName() + " died at (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
						logBox.add(deaths);
					}
				}
				buildings1.remove(i);
				i--;
			}
		}
		logBox.add(Box.createRigidArea(new Dimension(10, 10)));
		infoBox.add(logPanel);
		infoBox.add(Box.createRigidArea(new Dimension(20, 20)));
		
		activeDisastersPanel = new JPanel();
		activeDisastersPanel.setVisible(true);
		activeDisastersPanel.setBorder(BorderFactory.createTitledBorder("Active Disasters"));
		activeDisastersPanel.setLayout(new BorderLayout());
		activeDisastersBox = Box.createVerticalBox();
		activeDisastersPanel.add(activeDisastersBox);
		activeDisastersBox.add(Box.createRigidArea(new Dimension(10, 10)));
		for (int i=0; i<cc.getEngine().getExecutedDisasters().size(); i++) {
			Disaster d = cc.getEngine().getExecutedDisasters().get(i);
			if (d.isActive()) {
				JLabel disaster = new JLabel (d.getDisasterName() + " is still striking " + d.getTargetName());
				activeDisastersBox.add(disaster);
				activeDisastersBox.add(Box.createRigidArea(new Dimension(5, 5)));
			}
		}
	
		infoBox.add(activeDisastersPanel);
		infoBox.add(Box.createRigidArea(new Dimension(20, 20)));
		
		buildingDescription = new JPanel();
		buildingDescription.setVisible(true);
		buildingDescription.setBorder(BorderFactory.createTitledBorder("Building Description"));
		buildingDescription.setLayout(new BorderLayout());
		buildingDescription.add(Box.createRigidArea(new Dimension(5, 5)));
		infoBox.add(buildingDescription);
		infoBox.add(Box.createRigidArea(new Dimension(20, 20)));
		
		citizenDescription = new JPanel();
		citizenDescription.setVisible(true);
		citizenDescription.setBorder(BorderFactory.createTitledBorder("Citizen Description"));
		citizenDescription.setLayout(new BorderLayout());
		citizenDescription.add(Box.createRigidArea(new Dimension(5, 5)));
		infoBox.add(citizenDescription);
		infoBox.add(Box.createRigidArea(new Dimension(20, 20)));
		
		contentPane.add(infoPanel, BorderLayout.WEST);
		
		unitsPanel = new JPanel();
		unitsPanel.setBorder(BorderFactory.createTitledBorder("Units Panel"));
		unitsPanel.setSize(300,300);
		unitsPanel.setLayout(new BorderLayout());
		unitsPanel.setBackground(Color.WHITE);
		Box unitBox = Box.createVerticalBox();
		unitsPanel.add(unitBox, BorderLayout.WEST);
		unitsPanel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		idleUnits = new JPanel();
		idleUnits.setBorder(BorderFactory.createTitledBorder("Idle Units"));
		Box idleBox = Box.createVerticalBox();
		idleUnits.add(idleBox);
		idleBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		respondingUnits = new JPanel();
		respondingUnits.setBorder(BorderFactory.createTitledBorder("Responding Units"));
		Box respondingBox = Box.createVerticalBox();
		respondingUnits.add(respondingBox);
		respondingBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		treatingUnits = new JPanel();
		treatingUnits.setBorder(BorderFactory.createTitledBorder("Treating Units"));
		Box treatingBox = Box.createVerticalBox();
		treatingUnits.add(treatingBox);
		treatingBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		
		for (int i=0; i<emergencyUnits.size(); i++) {
			Unit u = emergencyUnits.get(i);
			if (u.getState() == UnitState.IDLE) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				idleBox.add(unit);
			}
			if (u.getState() == UnitState.RESPONDING) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				respondingBox.add(unit);
			}
			if (u.getState() == UnitState.TREATING) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				treatingBox.add(unit);
			}
		}
		unitBox.add(idleUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));
		unitBox.add(respondingUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));
		unitBox.add(treatingUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));

		unitDescription = new JPanel();
		unitDescription.setVisible(true);
		unitDescription.setBorder(BorderFactory.createTitledBorder("Unit Description"));
		unitDescription.setLayout(new BorderLayout());
		unitDescription.add(Box.createRigidArea(new Dimension(15, 15)));
		unitBox.add(unitDescription);
		
		contentPane.add(unitsPanel, BorderLayout.EAST);
	}
	
	public void updateCurrentCycle() {
		currentCycle.setText("Current cycle: " + cc.getEngine().getCurrentCycle());
	}
	
	public void updateCasualties() {
		casualties.setText("Casualties: " + cc.getEngine().calculateCasualties());
	}
	
	public void updateGrid() {
		rescuePanel.removeAll();
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				JReferencingButton button = new JReferencingButton();
				
				for (int b=0; b<visibleBuildings.size(); b++) {
					ResidentialBuilding b1 = visibleBuildings.get(b);
					if (b1.getLocation().getY()==i && b1.getLocation().getX()==j && b1.getOccupants().size()==0 && b1.getStructuralIntegrity()!=0) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + EB");
								button.setToolTipText("Empty Building + Empty building");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + EB");
								button.setToolTipText("Empty Building + Empty building");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + EB");
								button.setToolTipText("Ambulance + Empty building");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + EB");
								button.setToolTipText("Gas Control Unit + Empty building");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + EB");
								button.setToolTipText("Citizen + Empty building");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + EB");
								button.setToolTipText("Disease Control Unit + Empty building");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + EB");
								button.setToolTipText("Evacuator + Empty building");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + EB");
								button.setToolTipText("FireTruck + Empty building");
							}
							else {
								button.setText(button.getText() + " + EB");
								button.setToolTipText(button.getToolTipText() + " + Empty building");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Empty building");
							button.setToolTipText("Empty building");
						}
						button.setB(b1);
					}
					if (b1.getLocation().getY()==i && b1.getLocation().getX()==j && b1.getOccupants().size()!=0 && b1.getStructuralIntegrity()!=0) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + B");
								button.setToolTipText("Empty Building + Building with citizen(s)");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + B");
								button.setToolTipText("Empty Building + Building with citizen(s)");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + B");
								button.setToolTipText("Ambulance + Building with citizen(s)");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + B");
								button.setToolTipText("Gas Control Unit + Building with citizen(s)");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + B");
								button.setToolTipText("Citizen + Building with citizen(s)");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + B");
								button.setToolTipText("Disease Control Unit + Building with citizen(s)");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + B");
								button.setToolTipText("Evacuator + Building with citizen(s)");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + B");
								button.setToolTipText("FireTruck + Building with citizen(s)");
							}
							else {
								button.setText(button.getText() + " + B");
								button.setToolTipText(button.getToolTipText() + " + Building with citizen(s)");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Building with citizen(s)");
							button.setToolTipText("Building with citizen(s)");
						}
						button.setB(b1);
					}
				}
				
				for (int c=0; c<visibleCitizens.size(); c++) {
					Citizen c1 = visibleCitizens.get(c);
					if (c1.getLocation().getY()==i && c1.getLocation().getX()==j && c1.getState()!=CitizenState.DECEASED) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + C");
								button.setToolTipText("Empty Building + Citizen");
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + C");
								button.setToolTipText("Empty Building + Citizen");
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + C");
								button.setToolTipText("Ambulance + Empty building");
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + C");
								button.setToolTipText("Gas Control Unit + Citizen");
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + C");
								button.setToolTipText("Citizen + Citizen");
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + C");
								button.setToolTipText("Disease Control Unit + Citizen");
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + C");
								button.setToolTipText("Evacuator + Citizen");
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + C");
								button.setToolTipText("FireTruck + Citizen");
							}
							else {
								button.setText(button.getText() + " + C");
								button.setToolTipText(button.getToolTipText() + " + Citizen");
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText("Citizen");
							button.setToolTipText("Citizen");
						}
						button.setC(c1);
					}
				}
				
				for (int u=0; u<emergencyUnits.size(); u++) {
					Unit u1 = emergencyUnits.get(u);
					if (u1.getLocation().getY()==i && u1.getLocation().getX()==j) {
						if (button.getText()!="") {
							if (button.getText().equals("Empty building")) {
								button.setText("EB + " + u1.getTypeAbb());
								button.setToolTipText("Empty Building + " + u1.getTypeName());
							}
							else if (button.getText().equals("Building with citizen(s)")) {
								button.setText("BwC + " + u1.getTypeAbb());
								button.setToolTipText("Building with citizen(s) + " + u1.getTypeName());
							}
							else if (button.getText().equals("Ambulance")) {
								button.setText("A + " + u1.getTypeAbb());
								button.setToolTipText("Ambulance + " +  u1.getTypeName());
							}
							else if (button.getText().equals("Gas control unit")) {
								button.setText("GCU + " + u1.getTypeAbb());
								button.setToolTipText("Gas Control Unit + " + u1.getTypeName());
							}
							else if (button.getText().equals("Citizen")) {
								button.setText("C + " + u1.getTypeAbb());
								button.setToolTipText("Citizen + " + u1.getTypeAbb());
							}
							else if (button.getText().equals("Disease control unit")) {
								button.setText("DCU + " + u1.getTypeAbb());
								button.setToolTipText("Disease Control Unit + " + u1.getTypeName());
							}
							else if (button.getText().equals("Evacuator")) {
								button.setText("E + " + u1.getTypeAbb());
								button.setToolTipText("Evacuator + " + u1.getTypeName());
							}
							else if (button.getText().equals("Fire truck")) {
								button.setText("FT + " + u1.getTypeAbb());
								button.setToolTipText("Fire Truck + " + u1.getTypeName());
							}
							else {
								button.setText(button.getText() + " + " + u1.getTypeAbb());
								button.setToolTipText(button.getToolTipText() + " + " + u1.getTypeName());
							}
							button.setActionCommand("Multiple Objects");
						}
						else {
							button.setText(u1.getTypeName());
							button.setToolTipText(u1.getTypeName());
						}
						button.setU(u1);
					}
				}
				
				button.addActionListener(this);
				rescuePanel.add(button);
			}
		}
	}
	
	public void createBuildingDescription (ResidentialBuilding b1) {
		
		if (b1.getOccupants().size()==0) {
			buildingDescription.removeAll();
			buildingDescription.add(Box.createRigidArea(new Dimension(5, 5)));
			Box buildingBox = Box.createVerticalBox();
			buildingDescription.add(buildingBox);
			buildingBox.add(Box.createRigidArea(new Dimension(5, 5)));
			JLabel location = new JLabel("Location: (" + b1.getLocation().getX() + ", " + b1.getLocation().getY() + ")");
			JLabel structuralIntegrity = new JLabel("Structural Integrity: " + b1.getStructuralIntegrity());
			JLabel fireDamage = new JLabel("Fire Damage: " + b1.getFireDamage());
			JLabel gasLevel = new JLabel("Gas Level: " + b1.getGasLevel());
			JLabel foundationDamage = new JLabel("Foundation Damage: " + b1.getFoundationDamage());
			JLabel disaster = new JLabel("Disaster: " + b1.getDisaster().getDisasterName() + " started at cycle " + b1.getDisaster().getStartCycle());
			JLabel occupants = new JLabel("Occupants: " + b1.getOccupants().size());
			JReferencingButton sendSOS = new JReferencingButton ();
			sendSOS.setText("Send SOS to Building");
			sendSOS.setB(b1);
			sendSOS.addActionListener(this);
			buildingBox.add(location);
			buildingBox.add(structuralIntegrity);
			buildingBox.add(fireDamage);
			buildingBox.add(gasLevel);
			buildingBox.add(foundationDamage);
			buildingBox.add(occupants);
			buildingBox.add(disaster);
			buildingBox.add(Box.createRigidArea(new Dimension(5, 5)));
			buildingBox.add(sendSOS);
		}
		else {
			buildingDescription.removeAll();
			buildingDescription.add(Box.createRigidArea(new Dimension(5, 5)));
			Box buildingBox = Box.createVerticalBox();
			buildingDescription.add(buildingBox);
			buildingBox.add(Box.createRigidArea(new Dimension(5, 5)));
			JLabel location = new JLabel("Location: (" + b1.getLocation().getX() + ", " + b1.getLocation().getY() + ")");
			JLabel structuralIntegrity = new JLabel("Structural Integrity: " + b1.getStructuralIntegrity());
			JLabel fireDamage = new JLabel("Fire Damage: " + b1.getFireDamage());
			JLabel gasLevel = new JLabel("Gas Level: " + b1.getGasLevel());
			JLabel foundationDamage = new JLabel("Foundation Damage: " + b1.getFoundationDamage());
			JLabel disaster = new JLabel("Disaster: " + b1.getDisaster().getDisasterName() + " started at cycle " + b1.getDisaster().getStartCycle());
			JLabel occupants = new JLabel("Occupants: " + b1.getOccupants().size());
			JReferencingButton sendSOS = new JReferencingButton ();
			sendSOS.setText("Send SOS to Building");
			sendSOS.setB(b1);
			sendSOS.addActionListener(this);
			buildingBox.add(location);
			buildingBox.add(structuralIntegrity);
			buildingBox.add(fireDamage);
			buildingBox.add(gasLevel);
			buildingBox.add(foundationDamage);
			buildingBox.add(occupants);
			for (int o=0; o<b1.getOccupants().size(); o++) {
				JLabel occupant = new JLabel("    	" + b1.getOccupants().get(o).getName() + ", " + b1.getOccupants().get(o).getNationalID() + ", (" + b1.getOccupants().get(o).getLocation().getX() + ", " 
						+ b1.getOccupants().get(o).getLocation().getY() + "), " + b1.getOccupants().get(o).getAge() + ", " + b1.getOccupants().get(o).getState() + ", " + b1.getOccupants().get(o).getHp() + 
						", " + b1.getOccupants().get(o).getBloodLoss() + ", " + b1.getOccupants().get(o).getToxicity());
				buildingBox.add(occupant);
			}
			buildingBox.add(disaster);
			buildingBox.add(Box.createRigidArea(new Dimension(5, 5)));
			buildingBox.add(sendSOS);
		}
	}
	
	public void createCitizenDescription (Citizen c1) {
		citizenDescription.removeAll();
		citizenDescription.add(Box.createRigidArea(new Dimension(5, 5)));
		Box citizenBox = Box.createVerticalBox();
		citizenDescription.add(citizenBox);
		citizenBox.add(Box.createRigidArea(new Dimension(5, 5)));
		JLabel location = new JLabel("Location: (" + c1.getLocation().getX() + ", " + c1.getLocation().getY() + ")");
		JLabel name = new JLabel("Name: " + c1.getName());
		JLabel age = new JLabel("Age: " + c1.getAge());
		JLabel nationalID = new JLabel("National ID: " + c1.getNationalID());
		JLabel hp = new JLabel("HP: " + c1.getHp());
		JLabel bloodLoss = new JLabel("BloodLoss: " + c1.getBloodLoss());
		JLabel toxicity = new JLabel("Toxicity: " + c1.getToxicity());
		JLabel state = new JLabel("State: " + c1.getStateName());
		JLabel disaster = new JLabel("Disaster: " + c1.getDisaster().getDisasterName() + " started at cycle " + c1.getDisaster().getStartCycle());
		JReferencingButton sendSOS = new JReferencingButton ();
		sendSOS.setText("Send SOS to Citizen");
		sendSOS.setC(c1);
		sendSOS.addActionListener(this);
		citizenBox.add(location);
		citizenBox.add(name);
		citizenBox.add(age);
		citizenBox.add(nationalID);
		citizenBox.add(hp);
		citizenBox.add(bloodLoss);
		citizenBox.add(toxicity);
		citizenBox.add(state);
		citizenBox.add(disaster);
		citizenBox.add(Box.createRigidArea(new Dimension(5, 5)));
		citizenBox.add(sendSOS);
	}
	
	public void updateLogPanel() {
		JLabel currentCycle = new JLabel("Cycle: " + cc.getEngine().getCurrentCycle());
		logBox.add(currentCycle);
		for (int i=0; i<cc.getEngine().getExecutedDisasters().size(); i++) {
			Disaster d = cc.getEngine().getExecutedDisasters().get(i);
			if (d.getStartCycle() == cc.getEngine().getCurrentCycle()) {
				JLabel disaster = new JLabel (d.getDisasterName() + " struck " + d.getTargetName());
				logBox.add(disaster);
			}
		}
		for (int i=0; i<citizens1.size(); i++) {
			Citizen c = citizens1.get(i);
			if (c.getState() == CitizenState.DECEASED) {
				JLabel deaths = new JLabel (c.getName() + " died at (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
				logBox.add(deaths);
				citizens1.remove(i);
				i--;
			}
		}
		for (int i=0; i<buildings1.size(); i++) {
			ResidentialBuilding b = buildings1.get(i);
			if (b.getStructuralIntegrity() == 0) {
				JLabel group = new JLabel("Group of citizens: ");
				logBox.add(group);
				for (int j=0; j<b.getOccupants().size(); j++) {
					Citizen c = b.getOccupants().get(j);
					if (c.getState() == CitizenState.DECEASED) {
						JLabel deaths = new JLabel (c.getName() + " died at (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
						logBox.add(deaths);
					}
				}
				buildings1.remove(i);
				i--;
			}
		}
		logBox.add(Box.createRigidArea(new Dimension(10, 10)));
		
	}
	
	public void updateActiveDisastersPanel () {
		activeDisastersPanel.removeAll();
		activeDisastersPanel.setVisible(true);
		activeDisastersBox = Box.createVerticalBox();
		activeDisastersBox.add(Box.createRigidArea(new Dimension(10, 10)));
		activeDisastersPanel.add(activeDisastersBox);
		for (int i=0; i<cc.getEngine().getExecutedDisasters().size(); i++) {
			Disaster d = cc.getEngine().getExecutedDisasters().get(i);
			if (d.isActive()) {
				JLabel disaster = new JLabel (d.getDisasterName() + " is still striking " + d.getTargetName());
				activeDisastersBox.add(disaster);
				activeDisastersBox.add(Box.createRigidArea(new Dimension(5, 5)));
			}
		}
	}

	public void updateUnitsPanel() {
		unitsPanel.removeAll();
		
		Box unitBox = Box.createVerticalBox();
		unitsPanel.add(unitBox, BorderLayout.WEST);
		unitsPanel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		idleUnits = new JPanel();
		idleUnits.setBorder(BorderFactory.createTitledBorder("Idle Units"));
		Box idleBox = Box.createVerticalBox();
		idleUnits.add(idleBox);
		idleBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		respondingUnits = new JPanel();
		respondingUnits.setBorder(BorderFactory.createTitledBorder("Responding Units"));
		Box respondingBox = Box.createVerticalBox();
		respondingUnits.add(respondingBox);
		respondingBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		treatingUnits = new JPanel();
		treatingUnits.setBorder(BorderFactory.createTitledBorder("Treating Units"));
		Box treatingBox = Box.createVerticalBox();
		treatingUnits.add(treatingBox);
		treatingBox.add(Box.createRigidArea(new Dimension(5, 5)));
		
		
		for (int i=0; i<emergencyUnits.size(); i++) {
			Unit u = emergencyUnits.get(i);
			if (u.getState() == UnitState.IDLE) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				idleBox.add(unit);
			}
			if (u.getState() == UnitState.RESPONDING) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				respondingBox.add(unit);
			}
			if (u.getState() == UnitState.TREATING) {
				JReferencingButton unit = new JReferencingButton();
				if (u instanceof Ambulance)
					unit.setText("Ambulance");
				else if (u instanceof FireTruck)
					unit.setText("Fire Truck");
				else if (u instanceof Evacuator)
					unit.setText("Evacuator");
				else if (u instanceof DiseaseControlUnit)
					unit.setText("Disease Control Unit");
				else if (u instanceof GasControlUnit)
					unit.setText("Gas Control Unit");
	
				unit.setU(u);
				unit.addActionListener(this);
				treatingBox.add(unit);
			}
		}
		unitBox.add(idleUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));
		unitBox.add(respondingUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));
		unitBox.add(treatingUnits);
		unitBox.add(Box.createRigidArea(new Dimension(20, 20)));

		unitDescription = new JPanel();
		unitDescription.setVisible(true);
		unitDescription.setBorder(BorderFactory.createTitledBorder("Unit Description"));
		unitDescription.setLayout(new BorderLayout());
		unitDescription.add(Box.createRigidArea(new Dimension(15, 15)));
		unitBox.add(unitDescription);
		
	}
	
	public void createUnitDescription(Unit u1) {
		
		if (u1 instanceof Evacuator) {
			unitDescription.removeAll();
			unitDescription.add(Box.createRigidArea(new Dimension(15, 15)));
			Box unitsBox = Box.createVerticalBox();
			unitDescription.add(unitsBox);
			JLabel id = new JLabel("Unit ID: " + u1.getUnitID());
			JLabel type = new JLabel("Type: " + u1.getTypeName());
			JLabel location = new JLabel("Location: (" + u1.getLocation().getX() + ", " + u1.getLocation().getY() + ")");
			JLabel steps = new JLabel("Steps per cycle: " + u1.getStepsPerCycle());
			JLabel target_loc = null;
			JLabel target = null;
			if (u1.getTarget() != null)
				target_loc = new JLabel("Target + Location: " + u1.getTargetName() + ", (" + (u1.getTarget()).getLocation().getX() + ", " + (u1.getTarget()).getLocation().getY() + ")");
			else
				target = new JLabel("Target: null");
			JLabel state = new JLabel("State: " + u1.getStateName());
			JLabel passengers = new JLabel("Number of passengers: " + ((PoliceUnit) u1).getPassengers().size());
			JButton sendSOS = new JButton ("Send SOS");
			sendSOS.addActionListener(this);
			unitsBox.add(id);
			unitsBox.add(type);
			unitsBox.add(location);
			unitsBox.add(steps);
			if (u1.getTarget() != null)
				unitsBox.add(target_loc);
			else
				unitsBox.add(target);
			unitsBox.add(state);
			unitsBox.add(passengers);
			for (int i=0; i<((PoliceUnit) u1).getPassengers().size(); i++) {
				Citizen c = ((PoliceUnit) u1).getPassengers().get(i);
				JLabel occupant = new JLabel("	" + c.getName() + "," + c.getNationalID() + "," + c.getLocation() + "," + c.getAge() + "," + c.getState() + "," + c.getHp() + "," 
						+ c.getBloodLoss() + "," + c.getToxicity());
				unitsBox.add(occupant);
			}
		}
		else {
			unitDescription.removeAll();
			unitDescription.add(Box.createRigidArea(new Dimension(15, 15)));
			Box unitsBox = Box.createVerticalBox();
			unitDescription.add(unitsBox);
			JLabel id = new JLabel("Unit ID: " + u1.getUnitID());
			JLabel type = new JLabel("Type: " + u1.getTypeName());
			JLabel location = new JLabel("Location: (" + u1.getLocation().getX() + ", " + u1.getLocation().getY() + ")");
			JLabel steps = new JLabel("Steps per cycle: " + u1.getStepsPerCycle());
			JLabel target_loc = null;
			JLabel target = null;
			if (u1.getTarget() != null)
				target_loc = new JLabel("Target + Location: " + u1.getTargetName() + ", ( " + (u1.getTarget()).getLocation().getX() + " ," + (u1.getTarget()).getLocation().getY() + ")");
			else
				target = new JLabel("Target: null");
			JLabel state = new JLabel("State: " + u1.getStateName());
			JButton sendSOS = new JButton ("Send SOS");
			sendSOS.addActionListener(this);
			unitsBox.add(id);
			unitsBox.add(type);
			unitsBox.add(location);
			unitsBox.add(steps);
			if (u1.getTarget() != null)
				unitsBox.add(target_loc);
			else
				unitsBox.add(target);
			unitsBox.add(state);
		}
	}
	
	public void clearSOS() {
		citizenSOS = null;
		buildingSOS = null;
	}
	
	public void clearClick() {
		cSOSclick = false;
		bSOSclick = false;
	}
	
	public void checkGameOver() {
		if (cc.getEngine().checkGameOver()) {
			dispose();
			JFrame gameOver = new JFrame();
			gameOver.setSize(600,300);
			gameOver.setTitle("GAME OVER");
			gameOver.setLayout(new FlowLayout());
			gameOver.add(Box.createRigidArea(new Dimension(30, 30)));
			gameOver.add(new JLabel("GAME OVER"));
			gameOver.add(new JLabel("\n"));
			gameOver.add(new JLabel("Score: " + cc.getEngine().calculateCasualties()));
			gameOver.setVisible(true);
			gameOver.setLocationRelativeTo(null);
		}
			
	}
	
	public void validRepaint() {
		rescuePanel.validate();
		rescuePanel.repaint();
		logBoxx.validate();
		logBoxx.repaint();
		logPanel.validate();
		logPanel.repaint();
		activeDisastersPanel.validate();
		activeDisastersPanel.repaint();
		buildingDescription.validate();
		buildingDescription.repaint();
		citizenDescription.validate();
		citizenDescription.repaint();
		infoPanel.validate();
		infoPanel.repaint();
		buttons1.validate();
		buttons1.repaint();
		idleUnits.validate();
		idleUnits.repaint();
		respondingUnits.validate();
		respondingUnits.repaint();
		treatingUnits.validate();
		treatingUnits.repaint();
		unitDescription.validate();
		unitDescription.repaint();
		unitsPanel.validate();
		unitsPanel.repaint();
		tips.validate();
		tips.repaint();
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Next Cycle")) {
			try {
				checkGameOver();
				cc.getEngine().nextCycle();
				updateCurrentCycle();
				updateCasualties();
				updateGrid();
				updateLogPanel();
				updateActiveDisastersPanel();
				updateUnitsPanel();
				buildingDescription.removeAll();
				buildingDescription.add(Box.createRigidArea(new Dimension(5, 5)));
				citizenDescription.removeAll();
				citizenDescription.add(Box.createRigidArea(new Dimension(5, 5)));
				unitDescription.removeAll();
				unitDescription.add(Box.createRigidArea(new Dimension(15, 15)));
				validRepaint();
			} catch (BuildingAlreadyCollapsedException e1) {
				JFrame popup1 = new JFrame();
				popup1.setSize(600,100);
				popup1.setTitle("Building Already Collapsed Exception");
				popup1.setLayout(new FlowLayout());
				popup1.add(Box.createRigidArea(new Dimension(30, 30)));
				popup1.add(new JLabel(e1.getMessage()));
				popup1.setVisible(true);
				popup1.setLocationRelativeTo(null);
			} catch (CitizenAlreadyDeadException e1) {
				JFrame popup1 = new JFrame();
				popup1.setSize(600,100);
				popup1.setTitle("Citizen Already Dead Exception");
				popup1.setLayout(new FlowLayout());
				popup1.add(Box.createRigidArea(new Dimension(30, 30)));
				popup1.add(new JLabel(e1.getMessage()));
				popup1.setVisible(true);
				popup1.setLocationRelativeTo(null);
			}
		}
		if (e.getActionCommand().equals("Empty building")) {
			clearSOS();
			clearClick();
			createBuildingDescription(((JReferencingButton) e.getSource()).getB());
			validRepaint();
		}
		if (e.getActionCommand().equals("Building with citizen(s)")) {
			clearSOS();
			clearClick();
			createBuildingDescription(((JReferencingButton) e.getSource()).getB());
			validRepaint();
		}
		if (e.getActionCommand().equals("Citizen")) {
			clearSOS();
			clearClick();
			createCitizenDescription(((JReferencingButton) e.getSource()).getC());
			validRepaint();
		}
		if (e.getActionCommand().equals("Multiple Objects")) {
			clearSOS();
			clearClick();
			if(((JReferencingButton) e.getSource()).getC() != null) {
				createCitizenDescription(((JReferencingButton) e.getSource()).getC());
				validRepaint();
			}
			if(((JReferencingButton) e.getSource()).getB() != null) {
				createBuildingDescription(((JReferencingButton) e.getSource()).getB());
				validRepaint();
			}
			if(((JReferencingButton) e.getSource()).getU() != null) {
				createUnitDescription(((JReferencingButton) e.getSource()).getU());
				validRepaint();
			}
			
		}
		if (e.getActionCommand().equals("Ambulance") || e.getActionCommand().equals("Gas Control Unit") || e.getActionCommand().equals("Fire Truck") 
				|| e.getActionCommand().equals("Disease Control Unit") || e.getActionCommand().equals("Evacuator")) {
			createUnitDescription(((JReferencingButton) e.getSource()).getU());
			validRepaint();
			
			if (cSOSclick) {
				try {
					Citizen target = citizenSOS;
					((JReferencingButton) e.getSource()).getU().respond(target);
					updateUnitsPanel();
					validRepaint();
					clearSOS();
					clearClick();
				} 
				catch (CannotTreatException e1) {
					JFrame popup1 = new JFrame();
					popup1.setSize(600,100);
					popup1.setTitle("Cannot Treat Exception");
					popup1.setLayout(new FlowLayout());
					popup1.add(Box.createRigidArea(new Dimension(30, 30)));
					popup1.add(new JLabel(e1.getMessage()));
					popup1.setVisible(true);
					popup1.setLocationRelativeTo(null);
				}
				catch (IncompatibleTargetException e1) {
					JFrame popup1 = new JFrame();
					popup1.setSize(600,100);
					popup1.setTitle("Incompatible Target Exception");
					popup1.setLayout(new FlowLayout());
					popup1.add(Box.createRigidArea(new Dimension(30, 30)));
					popup1.add(new JLabel(e1.getMessage()));
					popup1.setVisible(true);
					popup1.setLocationRelativeTo(null);
				}
			}
			
			if (bSOSclick) {
				try {
					ResidentialBuilding target = buildingSOS;
					((JReferencingButton) e.getSource()).getU().respond(target);
					updateUnitsPanel();
					validRepaint();
					clearSOS();
					clearClick();
				}
				catch (CannotTreatException e1) {
					JFrame popup1 = new JFrame();
					popup1.setSize(600,100);
					popup1.setTitle("Cannot Treat Exception");
					popup1.setLayout(new FlowLayout());
					popup1.add(Box.createRigidArea(new Dimension(30, 30)));
					popup1.add(new JLabel(e1.getMessage()));
					popup1.setVisible(true);
					popup1.setLocationRelativeTo(null);
				}
				catch (IncompatibleTargetException e1) {
					JFrame popup1 = new JFrame();
					popup1.setSize(600,100);
					popup1.setTitle("Incompatible Target Exception");
					popup1.setLayout(new FlowLayout());
					popup1.add(Box.createRigidArea(new Dimension(30, 30)));
					popup1.add(new JLabel(e1.getMessage()));
					popup1.setVisible(true);
					popup1.setLocationRelativeTo(null);
				}
			}
		}
		
		if (e.getActionCommand().equals("Send SOS to Citizen")) {
			clearSOS();
			cSOSclick = true;
			citizenSOS = ((JReferencingButton) e.getSource()).getC();
			bSOSclick = false;
			buildingSOS = null;
			
			JFrame popup1 = new JFrame();
			popup1.setSize(600,200);
			popup1.setTitle("Send SOS to Citizen");
			popup1.setLayout(new BorderLayout());
			popup1.add(Box.createRigidArea(new Dimension(30, 30)));
			popup1.add(new JLabel("          Please choose a unit to dispatch or cancel sending the SOS"), BorderLayout.CENTER);
			JPanel buttons = new JPanel();
			buttons.setSize(600,100);
			buttons.setLayout(new FlowLayout());
			JReferencingButton closesButton = new JReferencingButton();
			closesButton.setText("OK");
			closesButton.setF(popup1);
			closesButton.addActionListener(this);
			buttons.add(closesButton);
			JReferencingButton cancelButton = new JReferencingButton();
			cancelButton.setText("Cancel");
			cancelButton.setF(popup1);
			cancelButton.addActionListener(this);
			buttons.add(cancelButton);
			popup1.add(buttons, BorderLayout.SOUTH);
			popup1.setVisible(true);
			popup1.setLocationRelativeTo(null);
		}
		
		if (e.getActionCommand().equals("OK")) {
			((JReferencingButton) e.getSource()).getF().dispose();
		}
		
		if (e.getActionCommand().equals("Cancel")) {
			((JReferencingButton) e.getSource()).getF().dispose();
			clearSOS();
			clearClick();
		}
		
		if (e.getActionCommand().equals("Send SOS to Building")) {
			clearSOS();
			bSOSclick = true;
			buildingSOS = ((JReferencingButton) e.getSource()).getB();
			cSOSclick = false;
			citizenSOS = null;
			
			JFrame popup1 = new JFrame();
			popup1.setSize(600,200);
			popup1.setTitle("Send SOS to Building");
			popup1.setLayout(new BorderLayout());
			popup1.add(Box.createRigidArea(new Dimension(30, 30)));
			popup1.add(new JLabel("          Please choose a unit to dispatch or cancel sending the SOS"), BorderLayout.CENTER);
			JPanel buttons = new JPanel();
			buttons.setSize(600,100);
			buttons.setLayout(new FlowLayout());
			JReferencingButton closesButton = new JReferencingButton();
			closesButton.setText("OK");
			closesButton.setF(popup1);
			closesButton.addActionListener(this);
			buttons.add(closesButton);
			JReferencingButton cancelButton = new JReferencingButton();
			cancelButton.setText("Cancel");
			cancelButton.setF(popup1);
			cancelButton.addActionListener(this);
			buttons.add(cancelButton);
			popup1.add(buttons, BorderLayout.SOUTH);
			popup1.setVisible(true);
			popup1.setLocationRelativeTo(null);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		GUI gui = new GUI();
		gui.setVisible(true);
	}

}

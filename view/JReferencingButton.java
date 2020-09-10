package view;


import javax.swing.JButton;
import javax.swing.JFrame;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;

public class JReferencingButton extends JButton {
	
  private ResidentialBuilding b;
  private Citizen c;
  private Unit u;
  private JFrame f;
  
  public JReferencingButton () {
	  super();
  }

  public ResidentialBuilding getB() 
  {
    return this.b;
  }
  
  public Citizen getC() 
  {
    return this.c;
  }

  public Unit getU()
  {
    return u;
  }
  
  public JFrame getF()
  {
    return f;
  }
  
  public void setF(JFrame f)
  {
    this.f = f;
  }
  
  public void setB(ResidentialBuilding b) 
  {
    this.b = b;
  }
  
  public void setC(Citizen c) 
  {
    this.c = c;
  }
  
  public void setU(Unit u)
  {
    this.u = u;
  }
}

package model.events;

import simulation.Rescuable;

public interface SOSResponder {
public void respond(Rescuable r) throws Exception ;
}

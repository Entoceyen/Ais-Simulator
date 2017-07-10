package model.scenario;

import java.util.HashMap;

import model.scenario.Scenario.Scenarios;

public class VesselSameIDScenario extends Scenario {

	public VesselSameIDScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setVesselSameID(true);
	}
	
	@Override
	public void remove() {
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.VesselSameIDScenario);
		return dataType;
	}

}

package model.scenario;

import java.util.HashMap;

import model.scenario.Scenario.Scenarios;

public class GhostScenario extends Scenario {

	public GhostScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setSendable(false);
	}
	
	@Override
	public void remove() {
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.GhostScenario);
		return dataType;
	}

}

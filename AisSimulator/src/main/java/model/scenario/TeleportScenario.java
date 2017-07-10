package model.scenario;

import java.util.HashMap;

import model.scenario.Scenario.Scenarios;

public class TeleportScenario extends Scenario {

	public TeleportScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setCut(true);
	}
	
	@Override
	public void remove() {
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.TeleportScenario);
		return dataType;
	}

}

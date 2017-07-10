package model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.scenario.Scenario.Scenarios;

public class ChangeDataScenario extends Scenario {
	
	private Entry<String,Object> field; 

	public ChangeDataScenario(int startTime, int duration, Entry<String,Object> data) {
		super(startTime, duration, false);
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i);
	}
	
	@Override
	public void remove() {
	}
	
	public Entry<String,Object> getField() {
		return field;
	}
	
	public void setField(String s,Object o) {
		field.setValue(o);
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String, Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.ChangeDataScenario);
		dataType.put("Donnée", DataEnum.class);
		return dataType;
	}

}

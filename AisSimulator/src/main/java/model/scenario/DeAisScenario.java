package model.scenario;

import java.util.HashMap;

import model.Path;
import model.scenario.Scenario.Scenarios;

public class DeAisScenario extends ChangePathScenario {
	
	private static Path deaisPath = loadDeAisPath();
	
	public DeAisScenario() {
		super(deaisPath);
	}

	private static Path loadDeAisPath() {
		return null;
	}
	
	public static Path getDeAisPath() {
		return deaisPath;
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = new HashMap<String,Object>();
		dataType.put("SCENARIO", Scenarios.DeAisScenario);
		return dataType;
	}

}

package model.scenario;

import java.util.HashMap;

import model.InstantSimulation;
import model.scenario.Scenario.Scenarios;

public class ChangeSpeedScenario extends Scenario {

	private int speed;
	
	public ChangeSpeedScenario(int startTime, double speed) {
		super(startTime, 0, true);
		this.speed = (int)(speed*10);
	}

	@Override
	public void apply() {
		InstantSimulation instant = getSimulation().getInstant(getStartTime());
		instant.getDynamicData().setSpeed(speed);
		instant.computeNextStepRadius();
		getSimulation().compute(getStartTime());
	}
	
	@Override
	public void remove() {
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = new HashMap<String, Object>();
		dataType.put("SCENARIO", Scenarios.ChangeSpeedScenario);
		dataType.put("Temps d'arrivé", Integer.class);
		dataType.put("Vitesse (noeud)", Double.class);
		return dataType;
	}
	
}

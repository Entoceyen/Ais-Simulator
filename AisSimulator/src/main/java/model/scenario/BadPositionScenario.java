package model.scenario;

import java.util.HashMap;
import java.util.Random;

import model.Coordinate;
import model.scenario.Scenario.Scenarios;

public class BadPositionScenario extends Scenario {
	
	private Coordinate badPosition;
	private Coordinate[] oldPositions;

	public BadPositionScenario(int startTime, int duration) {
		super(startTime, duration, false);
		badPosition = generateBadPosition();
		oldPositions = new Coordinate[duration];
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
			oldPositions[i-getStartTime()] = getSimulation().getInstant(i).getDynamicData().getPosition();
			getSimulation().getInstant(i).getDynamicData().setPosition(badPosition);
		}
	}
	
	@Override
	public void remove() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).getDynamicData().setPosition(oldPositions[i-getStartTime()]);
	}
	
	private Coordinate generateBadPosition() {
		Random rand = new Random();
		int random = rand.nextInt(2 - 0 + 1) + 0;
		if(random == 0) return new Coordinate(95, getSimulation().getInstant(getStartTime()).getDynamicData().getPosition().getLongitudeDouble());
		if(random == 1) return new Coordinate(getSimulation().getInstant(getStartTime()).getDynamicData().getPosition().getLatitudeDouble(), 185);
		return new Coordinate(95, 185);
	}
	
	public Coordinate getBadPosition() {
		return badPosition;
	}
	
	public Coordinate[] getOldPositions() {
		return oldPositions;
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.BadPositionScenario);
		return dataType;
	}
	
}
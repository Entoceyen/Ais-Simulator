package model.scenario;

import java.util.ArrayList;
import java.util.HashMap;

import model.Simulation;

public abstract class Scenario {

	public static enum Scenarios { 
		BadMIDScenario("Faux MID",BadMIDScenario.class), 
		BadPositionScenario("Position erronée",BadPositionScenario.class), 
		ChangeDataScenario("Modification d'une donnée",ChangeDataScenario.class), 
		ChangeSpeedScenario("Changement de vitesse",ChangeSpeedScenario.class), 
		DeAisScenario("DéAIS",DeAisScenario.class), 
		GhostScenario("AIS off",GhostScenario.class), 
		PathOnGroundScenario("Trajet à terre",PathOnGroundScenario.class), 
		TeleportScenario("Positions successives incohérentes",TeleportScenario.class), 
		VesselSameIDScenario("Navire même MMSI",VesselSameIDScenario.class);
		
		private String label;
		private Class<? extends Scenario> type;
		
		Scenarios(String lbl, Class<? extends Scenario> type) {
			label = lbl;
			this.type = type;
		}
		
		public String getLabel() {
			return label;
		}
		public Class<? extends Scenario> getType() {
			return (Class<? extends Scenario>) type;
		}
		public static Scenarios getScenariosByLabel(String lbl) {
			for(Scenarios s : Scenarios.values()) 
				if(s.label.equals(lbl)) return s;
			return null;
		}
	}
	
	private int startTime;
	private int duration;
	private Simulation simulation;
	private boolean compute;

	public static String[] getScenarios() {
		ArrayList<String> scenarios = new ArrayList<String>();
		for(Scenarios s : Scenarios.values()) scenarios.add(s.toString());
		return (String[]) scenarios.toArray();
	}
	
	public static Scenarios[] getScenarios2() {
		return Scenarios.values();
	}
	
	Scenario(int startTime, int duration, boolean compute) {
		this.startTime = startTime;
		this.duration = duration;
		this.compute = compute;
	}

	abstract public void apply();
	abstract public void remove();

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setSimulation(Simulation s) {
		simulation = s;
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	public boolean isCompute() {
		return compute;
	}
	
	public void setCompute(boolean b) {
		compute = b;
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String, Object> dataType = new HashMap<String, Object>();
		dataType.put("Temps d'arrivé", Integer.class);
		dataType.put("Durée", Integer.class);
		return dataType;
	}

}

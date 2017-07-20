package model.scenario;

import model.Simulation;

/**
 * Modèle principal à l'ensemble des modèles sc�nario.
 */
public abstract class Scenario {

	/**
	 * Enuméation de l'ensemble des scénarios. Est utilisé dans la vue ScenarioPanel
	 */
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
	
	/**
	 * Seconde de départ pour l'application du scénario
	 */
	private int startTime;
	
	/**
	 * Durée en seconde de l'application du scénario
	 */
	private int duration;
	private Simulation simulation;
	
	/**
	 * Indique si le scénario implique un re-calcul de la simulation
	 */
	private boolean compute;

	public static Scenarios[] getScenarios() {
		return Scenarios.values();
	}
	
	Scenario(int startTime, int duration, boolean compute) {
		this.startTime = startTime;
		this.duration = duration;
		this.compute = compute;
	}

	/**
	 * Doit être redéfini dans les sous-classe. Applique le scénario à la simulation.
	 */
	abstract public void apply();
	
	/**
	 * Supprime le scénario de la simulation.
	 */
	public void remove() {
		simulation.getScenarios().remove(this);
	}

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
	
	@Override
	public String toString() {
		return "Scenario [startTime=" + startTime + ", duration=" + duration + ", compute=" + compute + "]";
	}
	
	public abstract String description();
}

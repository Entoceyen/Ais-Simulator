package model.scenario;

import model.InstantSimulation;

/**
 * Modèle scénario permettant de changer la vitesse à un instant donné
 * Nécessite de re-calculer de la simulation
 */
public class ChangeSpeedScenario extends Scenario {

	private int speed;
	
	public ChangeSpeedScenario(int startTime, double speed) {
		super(startTime, 0, true);
		this.speed = (int)(speed*10);
	}

	/**
	 * Applique la nouvelle vitesse sur l'instant choisi
	 */
	@Override
	public void apply() {
		InstantSimulation instant = getSimulation().getInstant(getStartTime());
		instant.getDynamicData().setSpeed(speed);
		instant.computeNextStepRadius();
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "ChangeSpeedScenario [speed=" + speed + ", toString()=" + super.toString() + "]";
	}

	@Override
	public String description() {
		return "Changement de vitesse - Moment d'arrivée : "+getStartTime()+", Vitesse : "+speed;
	}
	
	
	
}

package model.scenario;

/**
 * Modèle scénario permettant de changer la fréquence d'émission des messages AIS
 * Ne nécessite pas de re-calcule de la simulation
 */
public class ChangeDelayMessageScenario extends Scenario {
	
	private int delayMessage1;
	private int delayMessage5;

	public ChangeDelayMessageScenario(int delay1, int delay5) {
		super(0, 0, false);
		delayMessage1 = delay1;
		delayMessage5 = delay5;
	}

	public boolean isDelayMessage1() {
		return delayMessage1 != 0;
	}
	
	public int getDelayMessage1() {
		return delayMessage1;
	}
	
	public boolean isDelayMessage5() {
		return delayMessage5 != 0;
	}
	
	public int getDelayMessage5() {
		return delayMessage5;
	}
	
	/**
	 * Ce scénario ne s'applique pas sur la simulation, il est utilisé au calcul des messages AIS
	 */
	@Override
	public void apply() {
		// On ne fait rien
	}

	@Override
	public String description() {
		return null;
	}

}

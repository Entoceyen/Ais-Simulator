package model.scenario;

/**
 * Modèle scénario Positions successives incohérentes
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class TeleportScenario extends Scenario {

	public TeleportScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	/**
	 * Modifie à true sur la plage de valeur définie l'attribut booléen cut des InstantSimulation
	 */
	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setCut(true);
	}
	
	/**
	 * Modifie à false sur la plage de valeur définie l'attribut booléen cut des InstantSimulation
	 */
	@Override
	public void remove() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setCut(false);
		super.remove();
	}
	
	public String description() {
		return "Positions successives incohérentes - Moment d'arrivée : "+getStartTime()+", Durée : "+getDuration();
	}
	
}

package model.scenario;

/**
 * Modèle scénario AIS off
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class GhostScenario extends Scenario {

	public GhostScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	/**
	 * Modifie à true sur la plage de valeur définie l'attribut booléen sendable des InstantSimulation
	 */
	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setSendable(false);
	}
	
	/**
	 * Modifie à false sur la plage de valeur définie l'attribut booléen sendable des InstantSimulation
	 */
	@Override
	public void remove() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setSendable(true);
		super.remove();
	}
	
	public String description() {
		return "AIS off - Moment d'arrivée : "+getStartTime()+", Durée : "+getDuration();
	}
	
}

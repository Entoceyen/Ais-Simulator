package model.scenario;

import dk.dma.ais.message.NavigationalStatus;
import model.Coordinate;
import model.DynamicData;
import model.datavessel.SpecialManIndicator;

/**
 * Modèle scénario Navire avec le même MMSI
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class VesselSameIDScenario extends Scenario {

	/**
	 * Données du second navire à générer sous forme de message
	 */
	private static DynamicData dynData = new DynamicData();
	static {	
		dynData.setPosition(new Coordinate(48.346234, -4.684272));
		dynData.setCog(900);
		dynData.setTrueHeading(90);
		dynData.setPosAcc(0);
		dynData.setNavStat(NavigationalStatus.AT_ANCHOR);
		dynData.setSpeed(0);
		dynData.setRot(0);
		dynData.setRaim(0);
		dynData.setSpecialManIndicator(SpecialManIndicator.NOT_AVAILABLE);
	}
	
	public VesselSameIDScenario(int startTime, int duration) {
		super(startTime, duration, false);
	}

	/**
	 * Modifie à true sur la plage de valeur définie l'attribut booléen vesselSameID des InstantSimulation
	 */
	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setVesselSameID(true);
	}
	
	/**
	 * Modifie à false sur la plage de valeur définie l'attribut booléen vesselSameID des InstantSimulation
	 */
	@Override
	public void remove() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).setVesselSameID(false);
		super.remove();
	}
	
	public String description() {
		return "Navire même MMSI";
	}
	
	public static DynamicData getDynData() {
		return dynData;
	}
	
}

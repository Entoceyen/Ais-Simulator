package model.scenario;

import java.util.Arrays;

import model.Coordinate;

/**
 * Modèle scénario générant une position invalide
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class BadPositionScenario extends Scenario {
	
	private Coordinate badPosition;
	
	/**
	 * Sauvegarde des anciennes valeurs des positions modifiées
	 */
	private Coordinate[] oldPositions;

	public BadPositionScenario(int startTime, int duration) {
		super(startTime, duration, false);
		badPosition = generateBadPosition();
		oldPositions = new Coordinate[duration];
	}

	/**
	 * Applique la position invalide sur la plage de valeur définie
	 */
	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
			oldPositions[i-getStartTime()] = getSimulation().getInstant(i).getDynamicData().getPosition();
			getSimulation().getInstant(i).getDynamicData().setPosition(badPosition);
		}
	}
	
	/**
	 * Restaure les anciennes position sur la plage de valeurs modifiée et supprime le scénario
	 */
	@Override
	public void remove() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++)
			getSimulation().getInstant(i).getDynamicData().setPosition(oldPositions[i-getStartTime()]);
		super.remove();
	}
	
	/**
	 * Génére une position invalide
	 * @return Coordinate
	 */
	private Coordinate generateBadPosition() {
		return new Coordinate(95, 185);
	}
	
	public Coordinate getBadPosition() {
		return badPosition;
	}
	
	public Coordinate[] getOldPositions() {
		return oldPositions;
	}

	@Override
	public String toString() {
		return "BadPositionScenario [badPosition=" + badPosition + ", oldPositions=" + Arrays.toString(oldPositions)
				+ ", toString()=" + super.toString() + "]";
	}
	
	public String description() {
		return "Position erronée - Moment d'arrivée : "+getStartTime()+", Durée : "+getDuration();
	}
	
}
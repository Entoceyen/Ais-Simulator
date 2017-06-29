package model;

import dk.dma.ais.message.NavigationalStatus;
import model.datavessel.SpecialManIndicator;

/**
 * Modèle concernant l'ensemble des données dynamique pour les messages AIS 1 et 5
 */
public class DynamicData implements Cloneable {

	private NavigationalStatus navStat;

	/**
	 * Rate of turne - Vitesse angulaire de virage
	 * 0 à +126 : virage à droite jusqu'à 708° / min ou plus
	 * 0 à -126 : virage à gauche jusqu'à 708° / min ou plus
	 * 
	 * Valeurs comprises en 0 et 708°/min sont codées par : ROTais = 4.733*sqrt(ROTsensor) degrés/min
	 * où ROTsensor est le ROT indiqué par un capteur externe (TI)
	 * ROTais est arrondie à la valeur entière la plus proche
	 * 
	 * +127 : virage à droite à plus de 5°/30s (pas de TI disponible)
	 * -127 : virage à gauche à plus de 5°/30s (pas de TI disponible)
	 * -128 : information non disponible
	 */
	private int rot;

	/**
	 * Vitesse de fond en 1/10 noeud
	 * 1023 = non disponible
	 * 1022 = 102.2 noeud ou plus
	 */
	private int speed;

	/**
	 * Précision de position
	 * 1 : élevée / 0 : peu élevée (par défaut)
	 */
	private int posAcc;

	private Coordinate position;

	/**
	 * Course over ground - Route de fond
	 * En 1/10° (0-3599)
	 * 3600 : non disponible (par défaut)
	 */
	private int cog;

	/**
	 * Cap vrai
	 * en degrés (0-359) (511 par défaut)
	 */
	private int trueHeading;

	private SpecialManIndicator specialManIndicator;

	/**
	 * Fanion RAIM
	 * 0 : non utilisé - par défaut
	 * 1 : utilisé
	 */
	private int raim;

	public NavigationalStatus getNavStat() {
		return navStat;
	}

	public void setNavStat(NavigationalStatus navStat) {
		this.navStat = navStat;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPosAcc() {
		return posAcc;
	}

	public void setPosAcc(int posAcc) {
		this.posAcc = posAcc;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public int getCog() {
		return cog;
	}

	public void setCog(int cog) {
		this.cog = cog;
	}

	public int getTrueHeading() {
		return trueHeading;
	}

	public void setTrueHeading(int trueHeading) {
		this.trueHeading = trueHeading;
	}

	public SpecialManIndicator getSpecialManIndicator() {
		return specialManIndicator;
	}

	public void setSpecialManIndicator(SpecialManIndicator specialManIndicator) {
		this.specialManIndicator = specialManIndicator;
	}

	public int getRaim() {
		return raim;
	}

	public void setRaim(int raim) {
		this.raim = raim;
	}
	
	@Override
	public DynamicData clone() {
		DynamicData dynData = null;
		try {
			dynData = (DynamicData) super.clone();
		} catch (CloneNotSupportedException e) {}
		dynData.position = position.clone();
		return dynData;
	}

	@Override
	public String toString() {
		return "DynamicData [navStat=" + navStat + ", rot=" + rot + ", speed=" + speed + ", posAcc=" + posAcc
				+ ", position=" + position + ", cog=" + cog + ", trueHeading=" + trueHeading + ", specialManIndicator="
				+ specialManIndicator + ", raim=" + raim + "]";
	}
	
}
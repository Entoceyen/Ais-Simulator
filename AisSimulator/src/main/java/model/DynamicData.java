package model;

import dk.dma.ais.message.NavigationalStatus;
import model.datavessel.SpecialManIndicator;

/**
 * Mod�le concernant l'ensemble des donn�es dynamique pour les messages AIS 1 et 5
 */
public class DynamicData {

	private NavigationalStatus navStat;

	/**
	 * Rate of turne - Vitesse angulaire de virage
	 * 0 � +126 : virage � droite jusqu'� 708� / min ou plus
	 * 0 � -126 : virage � gauche jusqu'� 708� / min ou plus
	 * 
	 * Valeurs comprises en 0 et 708�/min sont cod�es par : ROTais = 4.733*sqrt(ROTsensor) degr�s/min
	 * o� ROTsensor est le ROT indiqu� par un capteur externe (TI)
	 * ROTais est arrondie � la valeur enti�re la plus proche
	 * 
	 * +127 : virage � droite � plus de 5�/30s (pas de TI disponible)
	 * -127 : virage � gauche � plus de 5�/30s (pas de TI disponible)
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
	 * Pr�cision de position
	 * 1 : �lev�e / 0 : peu �lev�e (par d�faut)
	 */
	private int posAcc;

	private Coordinate position;

	/**
	 * Course over ground - Route de fond
	 * En 1/10� (0-3599)
	 * 3600 : non disponible (par d�faut)
	 */
	private int cog;

	/**
	 * Cap vrai
	 * en degr�s (0-359) (511 par d�faut)
	 */
	private int trueHeading;

	private SpecialManIndicator specialManIndicator;

	/**
	 * Fanion RAIM
	 * 0 : non utilis� - par d�faut
	 * 1 : utilis�
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
	public String toString() {
		return "DynamicData [navStat=" + navStat + ", rot=" + rot + ", speed=" + speed + ", posAcc=" + posAcc
				+ ", position=" + position + ", cog=" + cog + ", trueHeading=" + trueHeading + ", specialManIndicator="
				+ specialManIndicator + ", raim=" + raim + "]";
	}
	
}
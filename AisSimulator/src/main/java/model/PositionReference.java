package model;

/**
 * Modèle correspondant à la référence pour la position
 */
public class PositionReference {
	
	/**
	 * Distance de la proue (en metre)
	 */
	private int dimBow;
	
	/**
	 * Distance de la poupe (en metre)
	 */
	private int dimStern;
	
	/**
	 * Distance de babord (en metre)
	 */
	private int dimPort;
	
	/**
	 * Distance de tribord (en metre)
	 */
	private int dimStarboard;

	public int getDimBow() {
		return dimBow;
	}
	
	public void setDimBow(int dimBow) {
		this.dimBow = dimBow;
	}
	
	public int getDimStern() {
		return dimStern;
	}
	
	public void setDimStern(int dimStern) {
		this.dimStern = dimStern;
	}
	
	public int getDimPort() {
		return dimPort;
	}
	
	public void setDimPort(int dimPort) {
		this.dimPort = dimPort;
	}
	
	public int getDimStarboard() {
		return dimStarboard;
	}
	
	public void setDimStarboard(int dimStarboard) {
		this.dimStarboard = dimStarboard;
	}

	@Override
	public String toString() {
		return "PositionReference [dimBow=" + dimBow + ", dimStern=" + dimStern + ", dimPort=" + dimPort
				+ ", dimStarboard=" + dimStarboard + "]";
	}
	
	
}

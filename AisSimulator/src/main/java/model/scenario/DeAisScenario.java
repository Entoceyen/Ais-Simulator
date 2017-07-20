package model.scenario;

import model.Calculator;
import model.Coordinate;
import model.Path;

/**
 * Modèle scénario générant un trajet en forme du mot DeAIS en fonction d'un point de départ et de la longueur du mot
 * Nécessite de re-calculer la simulation
 */
public class DeAisScenario extends ChangePathScenario {
	
	/**
	 * Longueur du mot DeAIS en metre
	 */
	private int length;
	
	/**
	 * Point de coordonnées de départ
	 */
	private Coordinate c0;
	
	/**
	 * Matrice des positions relatives représentant le mot DeAIS
	 */
	private static int deais[][] = 
		{{0,0},{0,10},{4,8},{4,2},{0,0},{4,2},{6,2},{10,2},{9,4},{7,4},
		{6,2},{7,0},{9,0},{12,0},{13,4},{15,10},{17,4},{13,4},{17,4},{18,0},
		{20,0},{20,4},{20,0},{22,2},{24,0},{26,2},{22,6},{24,8},{26,6}};
	
	public DeAisScenario(Coordinate coord, int length) {
		super(null);
		c0 = coord;
		this.length = length;
		Path deaisPath = computeDeaisPath(c0);
		setPath(deaisPath);
	}

	/**
	 * Calcule le trajet DeAIS à partir du point d'origine et de la longueur
	 * @param c0 point d'origine
	 * @return
	 */
	private Path computeDeaisPath(Coordinate c0) {
		Path deaisPath = new Path();
		for(int[] coord : deais)
			deaisPath.addStep(computeCoordinate(c0, coord[0], coord[1]));
		return deaisPath;
	}
	
	/**
	 * Calcule un point de cordonnée en fonction de la distance en abscisse et en ordonnée d'un point existant
	 * @param c point existant
	 * @param x distance en abscisse
	 * @param y distance en ordonnée
	 * @return Coordinate
	 */
	private Coordinate computeCoordinate(Coordinate c, int x, int y) {
		double mX = (length/26)*y;
		double mY = (length/26)*x;
		double distance = Math.sqrt(mX*mX+mY*mY);
		double angle = (Math.toDegrees(Math.atan2(mY,mX))+360) % 360;
		double coord[] = Calculator.positionRhumb(c.getLatitudeDouble(), c.getLongitudeDouble(), angle, distance);
		return new Coordinate(coord[0],coord[1]);
	}
	
	public String description() {
		return "DéAIS - Position initiale : "+c0.getLatitudeDouble()+" "+c0.getLongitudeDouble()+", longueur : "+length+"m";
	}
	
}
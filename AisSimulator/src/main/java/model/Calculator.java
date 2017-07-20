package model;

/**
 * Modèle utilitaire proposant l'ensemble des calculs pour le positionnement
 */
public class Calculator
{
	/**
	 * Rayon de la terre en metre
	 */
	private static final int R = 6371000;
	
	public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println(distance(51.127, 1.338, 50.964, 1.853, "K") + " Km");
		System.out.println(distance(51.127, 1.338, 50.964, 1.853, "N") + " Nautical Miles");
		
		System.out.println(position(51.127, 1.338, 116.7, 40300)[0]+" "+position(51.127, 1.338, 116.7, 40300)[1]);
		System.out.println(positionRhumb(51.127, 1.338, 116.7, 40300)[0]+" "+positionRhumb(51.127, 1.338, 116.7, 40300)[1]);
		
		//System.out.println(heading(51.127, 1.338, 50.964, 1.853));
		System.out.println(bearing(51.127, 1.338, 50.964, 1.853));
	}

	/**
	 * Calcule la distance entre 2 points de coordonnées en miles nautiques
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @param unit
	 * @return double
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double lat1R = Math.toRadians(lat1);
		double lat2R = Math.toRadians(lat2);
		double diffLatR = Math.toRadians(lat2-lat1);
		double diffLonR = Math.toRadians(lon2-lon1);
		
		double a = Math.sin(diffLatR/2) * Math.sin(diffLatR/2) +
				   Math.cos(lat1R) * Math.cos(lat2R) * Math.sin(diffLonR/2) * Math.sin(diffLonR/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = R * c;
		
		if (unit == "K") {
			dist = dist / 1000;
		} else if (unit == "N") {
			dist = dist / 1852;
		}
		return dist;
	}
	
	/**
	 * Calcule un point de coordonnées à partir d'un point et d'un angle
	 * Respecte la courbure de la terre
	 * @param latitude
	 * @param longitude
	 * @param bearing 0-360
	 * @param distance meter
	 * @return tableau double
	 */
	public static double[] position(double latitude, double longitude, double bearing, double distance) {
		double[] result = new double[2];
		latitude = Math.toRadians(latitude);
		longitude = Math.toRadians(longitude);
		double lat = Math.asin(Math.sin(latitude)*Math.cos(distance/R) + Math.cos(latitude)*Math.sin(distance/R)*Math.cos(bearing*Math.PI/180));
		double lon = longitude + Math.atan2(Math.sin(bearing*Math.PI/180)*Math.sin(distance/R)*Math.cos(latitude),
										Math.cos(distance/R)-Math.sin(latitude)*Math.sin(lat));
		result[0] = Math.toDegrees(lat);
		result[1] = Math.toDegrees(lon);
		return result;
	}
	
	/**
	 * Calcule un point de coordonnées à partir d'un point et d'un angle de manière rectiligne
	 * @param latitude
	 * @param longitude
	 * @param angle 0-360
	 * @param distance meter
	 * @return tableau double
	 */
	public static double[] positionRhumb(double latitude, double longitude, double angle, double distance) {
		double delta = distance / R; // angular distance in radians
	    double phi1 = Math.toRadians(latitude), lambda1 = Math.toRadians(longitude);
	    double theta = Math.toRadians(angle);

	    double DELTAphi = delta * Math.cos(theta);
	    double phi2 = phi1 + DELTAphi;

	    // check for some daft bugger going past the pole, normalise latitude if so
	    if (Math.abs(phi2) > Math.PI/2) phi2 = phi2>0 ? Math.PI-phi2 : -Math.PI-phi2;

	    double DELTApsi = Math.log(Math.tan(phi2/2+Math.PI/4)/Math.tan(phi1/2+Math.PI/4));
	    double q = Math.abs(DELTApsi) > 10e-12 ? DELTAphi / DELTApsi : Math.cos(phi1); // E-W course becomes ill-conditioned with 0/0

	    double DELTAlambda = delta*Math.sin(theta)/q;
	    double lambda2 = lambda1 + DELTAlambda;

	    return new double[]{Math.toDegrees(phi2), (Math.toDegrees(lambda2)+540) % 360 - 180};
	}
	
	/**
	 * Calcule le cap entre 2 points gps
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return int
	 */
	public static int bearing(double lat1, double lon1, double lat2, double lon2) {
		double phi1 = Math.toRadians(lat1), phi2 = Math.toRadians(lat2);
	    double DELTAlambda = Math.toRadians(lon2-lon1);
	    // if dLon over 180° take shorter rhumb line across the anti-meridian:
	    if (Math.abs(DELTAlambda) > Math.PI) DELTAlambda = DELTAlambda>0 ? -(2*Math.PI-DELTAlambda) : (2*Math.PI+DELTAlambda);

	    double DELTApsi = Math.log(Math.tan(phi2/2+Math.PI/4)/Math.tan(phi1/2+Math.PI/4));

	    double theta = Math.atan2(DELTAlambda, DELTApsi);

	    return (int) ((Math.toDegrees(theta)+360) % 360);
	}
	
	/**
	 * Converti une vitesse en noeud en km/h
	 * @param kts - noeud
	 * @return double
	 */
	protected static double knot2Kmh(double kts) {
		return kts*1.852;
	}
	
	/**
	 * Converti une vitesse en km/h en noeud
	 * @param kmh - km/h
	 * @return double
	 */
	protected static double Kmh2Knot(double kmh) {
		return kmh/1.852;
	}
	
	/**
	 * Converti une durée en seconde en heure minute seconde
	 * @param sec - durée en seconde
	 * @return tableau d'entier
	 */
	public static int[] sec2HourMinSec(long sec) { 
		return new int[] {(int) (sec / 3600),(int) ((sec % 3600) / 60),(int) (sec % 60)}; 
	}
	
}
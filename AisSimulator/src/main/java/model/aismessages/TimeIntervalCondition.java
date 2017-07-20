package model.aismessages;

/**
 * Enumération des différentes condition correspondant aux différents interval de temps pour les messages de type 1
 */
public enum TimeIntervalCondition {
	ANCHOR_MAX3KTS(3*60*1000),
	ANCHOR_MIN3KTS(10000),
	MAX14KTS(10000),
	MAX14KTS_TURNING(3333),
	FROM14TO23KTS(6000),
	FROM14TO23KTS_TURNING(2000),
	MIN23KTS(2000),
	MIN23KTS_TURNING(2000);
	
	private int interval;
	
	TimeIntervalCondition(int millisecond) {
		interval = millisecond;
	}
	
	/**
	 * Retourne la condition en fonction de la vitesse, du virage ou non, de statut à l'ancre ou non
	 * @param speed vitesse
	 * @param turning indique si le bateau tourne
	 * @param anchor indique si le statut de navigation est à l'ancre ou au mouillage
	 * @return TimeIntervalCondition
	 */
	public static TimeIntervalCondition get(double speed, boolean turning, boolean anchor) {
		if(anchor && speed <= 3) return ANCHOR_MAX3KTS;
		if(anchor && speed > 3) return ANCHOR_MIN3KTS;
		if(speed <= 14 && turning) return MAX14KTS_TURNING;
		if(speed <= 14) return MAX14KTS;
		if(speed > 14 && speed <= 23 && turning) return FROM14TO23KTS_TURNING;
		if(speed > 14 && speed <= 23) return FROM14TO23KTS;
		if(speed > 23 && turning) return MIN23KTS_TURNING;
		if(speed > 23) return MIN23KTS;return null;
	}
	
	public int getInterval() {
		return interval;
	}
}
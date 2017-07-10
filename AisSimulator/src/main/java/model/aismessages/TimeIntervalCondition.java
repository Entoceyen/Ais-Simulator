package model.aismessages;

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
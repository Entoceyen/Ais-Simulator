package model.datavessel;

/**
 * Enumération des types dispositif de détermination de la position
 */
public enum PositionFixType {
	UNDIFINED(0, "Non défini (par défaut)"),
	GPS(1, "GPS"),
	GLONASS(2, "GLONASS"),
	GPS_GLONASS(3, "GPS/GLONASS combiné"),
	LORAN_C(4, "Loran-C"),
	CHAYKA(5, "Chayka"),
	INTEGRATED_NAV_SYSTEM(6, "Système de navigation intégré"),
	SURVEYED(7, "Etudié"),
	GALILEO(8, "Galileo"),
	INTERNAL_GNSS(15, "GNSS interne");
	
	private int id;
	private String name;
	
	PositionFixType(int id, String name) {
	    this.id = id;
		this.name = name;
	}
	   
	public String toString() {
		return id + " - " + name;
	}
	
	public int getID() {
		return id;
	}
	
	public static PositionFixType getByID(int id) {
		for(PositionFixType v : PositionFixType.values())
			if(v.getID() == id)	return v;
		return null;
	}
	
	public static String[] toStringArray() {
		String result[] = new String[PositionFixType.values().length];
		int i=0;
		for(PositionFixType v : PositionFixType.values()) {
			result[i] = v.toString();
			i++;
		}
		return result;
	}
}

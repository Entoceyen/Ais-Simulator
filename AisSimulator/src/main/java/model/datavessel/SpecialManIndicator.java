package model.datavessel;

/**
 * Enumération, indicateur de manoeuvre particulière
 */
public enum SpecialManIndicator {
	NOT_AVAILABLE(0, "Indisponible (par défaut)"),
	NOT_ENGAGED(1, "Non engagé dans une manoeuvre particulière"),
	ENGAGED(2, "Engagé dans une manoeuvre particulière");
	
	private int id;
	private String name;
	
	SpecialManIndicator(int id, String name) {
	    this.id = id;
		this.name = name;
	}
	   
	public String toString() {
		return id + " - " + name;
	}
	
	public int getID() {
		return id;
	}
	
	public static SpecialManIndicator getByID(int id) {
		for(SpecialManIndicator v : SpecialManIndicator.values())
			if(v.getID() == id)	return v;
		return null;
	}
	
	public static String[] toStringArray() {
		String result[] = new String[SpecialManIndicator.values().length];
		int i=0;
		for(SpecialManIndicator v : SpecialManIndicator.values()) {
			result[i] = v.toString();
			i++;
		}
		return result;
	}
}

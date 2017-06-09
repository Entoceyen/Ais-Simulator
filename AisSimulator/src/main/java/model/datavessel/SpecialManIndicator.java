package model.datavessel;

/**
 * Enum�ration, indicateur de manoeuvre particuli�re
 */
public enum SpecialManIndicator {
	NOT_AVAILABLE(0, "Indisponible (par defaut)"),
	NOT_ENGAGED(1, "Non engag� dans une manoeuvre particuli�re"),
	ENGAGED(2, "Engag� dans une manoeuvre particuli�re");
	
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

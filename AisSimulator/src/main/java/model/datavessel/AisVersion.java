package model.datavessel;

/**
 * Enum�ration des versions AIS
 */
public enum AisVersion {
	V1(0, "Conforme � la Recommandation UIT-R M.1371-1"),
	V3(1, "Conforme � la Recommandation UIT-R M.1371-3 (ou version ult�rieure)"),
	V5(2, "Conforme � la Recommandation UIT-R M.1371-5 (ou version ult�rieure)"),
	VFuture(3, "Conforme aux futures versions");
	
	private int id;
	private String name;
	
	AisVersion(int id, String name) {
	    this.id = id;
		this.name = name;
	}
	   
	public String toString() {
		return id + " - " + name;
	}
	
	public int getID() {
		return id;
	}
	
	public static AisVersion getByID(int id) {
		for(AisVersion v : AisVersion.values())
			if(v.getID() == id)	return v;
		return null;
	}
	
	public static String[] toStringArray() {
		String result[] = new String[AisVersion.values().length];
		int i=0;
		for(AisVersion v : AisVersion.values()) {
			result[i] = v.toString();
			i++;
		}
		return result;
	}
}

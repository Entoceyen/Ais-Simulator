package controler;

import dk.dma.ais.message.NavigationalStatus;
import dk.dma.ais.message.ShipTypeCargo;
import model.Simulation;
import view.DataPanel;

/**
 * Controleur père pour les classes DataXXXListener
 */
public abstract class DataManager {
	
	protected Simulation simulation;
	protected DataPanel panel;
	
	DataManager(Simulation simulation) {
		this.simulation = simulation;
	}
	
	protected void setPanel(DataPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * Retourne le type de navire correspondant à l'identifiant id
	 * @param id
	 * @return String
	 */
	protected static String getShipTypeByID(int id) {
		ShipTypeCargo stc = new ShipTypeCargo(id);
		return stc.getCode()+" - "+stc.prettyType()+" - "+stc.prettyCargo();
	}
	
	/**
	 * Retourne l'ensemble des types de navire sous forme d'un tableau de chaine
	 * @return String[]
	 */
	public static String[] shipTypeToStringArray() {
		String[] result = new String[81];
		result[0] = getShipTypeByID(0);
		for(int i=20, j=1 ; i<100 ; i++, j++)
			result[j] = getShipTypeByID(i);
		return result;
	}
	
	/**
	 * Retourne l'ensemble des statut de navigations sous forme d'un tableau de chaine
	 * @return String[]
	 */
	public static String[] navStatToStringArray() {
		String[] result = new String[NavigationalStatus.values().length];
		int i=0;
		for(NavigationalStatus ns : NavigationalStatus.values()) {
			result[i] = ns.getCode() +" - "+ ns.toString();
			i++;
		}
		return result;
	}
	
	/**
	 * Retourne la chaine DTE en fonction de son identifiant
	 * @param id l'identifiant
	 * @return String
	 */
	protected static String getDteByID(int id) {
		if(id == 0) return "0 - Disponible";
		else return "1 - Non disponible"; 
	}
	
	/**
	 * Retourne l'ensemble des statut DTE sous forme d'un tableau de chaine
	 * @return String[]
	 */
	public static String[] dteToStringArray() {
		return new String[]{"1 - Non disponible","0 - Disponible"};
	}
	
	/**
	 * Retourne la précision de position en fonction de son identifiant
	 * @param id l'identifiant
	 * @return String
	 */
	protected static String getPosAccByID(int id) {
		if(id == 0) return "0 - Peu élevée";
		else return "1 - Elevée"; 
	}
	
	/**
	 * Retourne l'ensemble des précisions de position sous forme d'un tableau de chaine
	 * @return String[]
	 */
	public static String[] posAccToStringArray() {
		return new String[]{"0 - Peu élevée","1 - Elevée"};
	}
}

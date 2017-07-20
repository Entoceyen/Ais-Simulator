package model;

import dk.dma.ais.message.ShipTypeCargo;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;

/**
 * Modèle concernant l'ensemble des données statique pour les messages AIS 1 et 5
 */
public class StaticData implements Cloneable {

	/**
     * Identifiant du navire
     */
    private int mmsi;

    /**
     * Nom du navire
     * Maximum 20 caractère ASCII à 6 bits
     * "@@@@@@@@@@@@@@@@@@@@" : non disponible (par défaut)
     */
    private String name;

    /**
     * Numéro OMI
     * 0 : non disponible (par défaut)
     */
    private int imo;

    /**
     * Indicatif d'appel
     * 7 caractère ASCII à 6 bits
     * "@@@@@@@" : non disponible (par défaut)
     */
    private String callSign;

    /**
     * Indicateur de version AIS (voir l'enum AisVersion)
     */
    private AisVersion aisVersion;

    /**
     * Type de navire et de cargaison (voir la classe ShipTypeCargo)
     */
    private ShipTypeCargo shipType;

    /**
     * Dimension générales / référence pour la position (voir PositionReference)
     */
    private PositionReference posRef;

    /**
     * Type de dispositif électronique de détermination de la position (voir PositionFixType) 
     */
    private PositionFixType posType;

    /**
     * Tirant d'eau statique actuel maximal en 1/10 metres
     * 255 : 25.5 m ou plus
     * 0 : non disponible (par défaut)
     */
    private int draught;

    /**
     * Terminal de données prêt
     */
    private boolean dte;

    /**
     * Destination
     * Maximum 20 caractère ASCII à 6 bits
     * "@@@@@@@@@@@@@@@@@@@@" : non disponible (par défaut)
     */
    private String destination;

	public int getMmsi() {
		return mmsi;
	}

	public void setMmsi(int mmsi) {
		this.mmsi = mmsi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImo() {
		return imo;
	}

	public void setImo(int imo) {
		this.imo = imo;
	}

	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public AisVersion getAisVersion() {
		return aisVersion;
	}

	public void setAisVersion(AisVersion aisVersion) {
		this.aisVersion = aisVersion;
	}

	public ShipTypeCargo getShipType() {
		return shipType;
	}

	public void setShipType(ShipTypeCargo shipType) {
		this.shipType = shipType;
	}

	public PositionReference getPosRef() {
		return posRef;
	}

	public void setPosRef(PositionReference posRef) {
		this.posRef = posRef;
	}

	public PositionFixType getPosType() {
		return posType;
	}

	public void setPosType(PositionFixType posType) {
		this.posType = posType;
	}

	public int getDraught() {
		return draught;
	}

	public void setDraught(int draught) {
		this.draught = draught;
	}

	public boolean isDte() {
		return dte;
	}

	public void setDte(boolean dte) {
		this.dte = dte;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public StaticData clone() {
		StaticData staticData = null;
		try {
			staticData = (StaticData) super.clone();
		} catch (CloneNotSupportedException e) {}
		staticData.posRef = posRef.clone();
		staticData.shipType = new ShipTypeCargo(shipType.getCode());
		return staticData;
	}
	
	@Override
	public boolean equals(Object obj) {
		StaticData data = (StaticData) obj;
		if(data.mmsi != mmsi) return false;
		if(!data.name.equals(name)) return false;
		if(!data.callSign.equals(callSign)) return false;
		if(data.imo != imo) return false;
		if(!data.destination.equals(destination)) return false;
		if(data.draught != draught) return false;
		if(data.dte != dte) return false;
		if(!data.aisVersion.equals(aisVersion)) return false;
		if(!data.posRef.equals(posRef)) return false;
		if(!data.posType.equals(posType)) return false;
		if(data.shipType.getCode() != shipType.getCode()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StaticData [mmsi=" + mmsi + ", name=" + name + ", imo=" + imo + ", callSign=" + callSign
				+ ", aisVersion=" + aisVersion + ", shipType=" + shipType + ", posRef=" + posRef + ", posType="
				+ posType + ", draught=" + draught + ", dte=" + dte + ", destination=" + destination + "]";
	}
	
	/**
	 * Retourne la chaine de caract�re utilis�e pour la pr�visualisation
	 * @return Texte au format html
	 */
	public String description() {
		return "<ul><h2>Données statiques</h2>"
				+ "<li>MMSI : "+mmsi+"</li>"
				+ "<li>Nom : "+name+"</li>"
				+ "<li>OMI : "+imo+"</li>"
				+ "<li>Callsign : "+callSign+"</li>"
				+ "<li>Version AIS : "+aisVersion+"</li>"
				+ "<li>Type de navire : "+shipType+"</li>"
				+ "<li>Référence pour la position (m) : "+posRef.getDimBow()+","+posRef.getDimStern()+","+posRef.getDimPort()+","+posRef.getDimStarboard()+" (distance de la proue, de la poupe, de babord, de tribord)</li>"
				+ "<li>Type de dispositif de positionnement : "+posType+"</li>"
				+ "<li>Tirant d'eau : "+(double)(draught/10)+" m</li>"
				+ "<li>DTE : "+(dte?"Disponible":"Non disponible")+"</li>"
				+ "<li>Destination : "+destination+"</li>"
				+ "</ul>";
	}
	
}

package model;

import dk.dma.ais.message.ShipTypeCargo;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;

/**
 * Mod�le concernant l'ensemble des donn�es statique pour les messages AIS 1 et 5
 */
public class StaticData implements Cloneable {

	/**
     * Identifiant du navire
     */
    private int mmsi;

    /**
     * Nom du navire
     * Maximum 20 caract�re ASCII � 6 bits
     * "@@@@@@@@@@@@@@@@@@@@" : non disponible (par d�faut)
     */
    private String name;

    /**
     * Num�ro OMI
     * 0 : non disponible (par d�faut)
     */
    private int imo;

    /**
     * Indicatif d'appel
     * 7 caract�re ASCII � 6 bits
     * "@@@@@@@" : non disponible (par d�faut)
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
     * Dimension g�n�rales / r�f�rence pour la position (voir PositionReference)
     */
    private PositionReference posRef;

    /**
     * Type de dispositif �lectronique de d�termination de la position (voir PositionFixType) 
     */
    private PositionFixType posType;

    /**
     * Tirant d'eau statique actuel maximal en 1/10 metres
     * 255 : 25.5 m ou plus
     * 0 : non disponible (par d�faut)
     */
    private int draught;

    /**
     * Terminal de donn�es pr�t
     */
    private boolean dte;

    /**
     * Destination
     * Maximum 20 caract�re ASCII � 6 bits
     * "@@@@@@@@@@@@@@@@@@@@" : non disponible (par d�faut)
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
	public String toString() {
		return "StaticData [mmsi=" + mmsi + ", name=" + name + ", imo=" + imo + ", callSign=" + callSign
				+ ", aisVersion=" + aisVersion + ", shipType=" + shipType + ", posRef=" + posRef + ", posType="
				+ posType + ", draught=" + draught + ", dte=" + dte + ", destination=" + destination + "]";
	}
	
}

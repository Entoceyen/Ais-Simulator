package model.scenario;

import controler.DataManager;
import dk.dma.ais.message.NavigationalStatus;
import dk.dma.ais.message.ShipTypeCargo;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;
import model.datavessel.SpecialManIndicator;

public enum DataEnum {
	MMSI("MMIS", Integer.class),
	IMO("Num�ro OMI", Integer.class),
	CALLSIGN("Indicatif d'appel", String.class),
	NAME("Nom", String.class),
	AIS_VERSION("Version AIS", AisVersion.toStringArray()),
	SHIP_TYPE("Type de navire et de cargaison", DataManager.shipTypeToStringArray()),
	DIM_STERN("Distance de la proupe", Integer.class),
	DIM_BOW("Distance de proue", Integer.class),
	DIM_PORT("Distance de babord", Integer.class),
	DIM_STARBOARD("Distance de tribord", Integer.class),
	POS_FIX_TYPE("Types dispositif de positionement", PositionFixType.toStringArray()),
	DRAUGHT("Tirant d'eau", Integer.class),
	DTE("Terminal de donn�es pr�t", DataManager.dteToStringArray()),
	DESTINATION("Destination", String.class),
	
	NAV_STATS("Statut de navigation", DataManager.navStatToStringArray()),
	ROT("Vitesse de giration", Integer.class),
	POS_ACC("Pr�cision de position", DataManager.posAccToStringArray()),
	LATITUDE("Latitude", Double.class),
	LONGITUDE("Longitude", Double.class),
	COG("Route de fond", Integer.class),
	TRUEHEADING("Cap vrai", Integer.class),
	SPECIAL_MAN_INDICATOR("Indicateur de manoeuvre particuli�re", SpecialManIndicator.toStringArray()),
	RAIM("Fanion RAIM", Integer.class);

	private String label;
	private Object type;
	
	DataEnum(String label, Object type) {
		this.label = label;
		this.type = type;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Object getType() {
		return type;
	}
}

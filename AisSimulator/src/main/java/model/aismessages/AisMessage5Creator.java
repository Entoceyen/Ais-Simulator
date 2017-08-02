package model.aismessages;

import dk.dma.ais.message.AisMessage5;
import dk.dma.ais.message.AisMessageException;
import model.StaticData;

/**
 * Modèle de création d'un message AIS de type 5
 */
public abstract class AisMessage5Creator {
	
	/**
	 * Fonction permettant de créer un message AisMessage5 à partir des données statiques
	 * @param staticData StaticData 
	 * @param eta long 
	 * @return Un objet de la classe AisMessage5 (AisLib)
	 * @throws AisMessageException
	 */
	public static AisMessage5 create(StaticData staticData, long eta) throws AisMessageException {
		AisMessage5 msg = new AisMessage5();
		
		msg.setVersion(staticData.getAisVersion().getID());
		msg.setImo(staticData.getImo());
		msg.setCallsign(staticData.getCallSign().toUpperCase());
		msg.setName(staticData.getName().toUpperCase());
		msg.setShipType(staticData.getShipType().getCode());
		msg.setDimBow(staticData.getPosRef().getDimBow());
		msg.setDimStern(staticData.getPosRef().getDimStern());
		msg.setDimPort(staticData.getPosRef().getDimPort());
		msg.setDimStarboard(staticData.getPosRef().getDimStarboard());
		msg.setPosType(staticData.getPosType().getID());
		msg.setEta(eta);
		msg.setDraught(staticData.getDraught());
		msg.setDest(staticData.getDestination().toUpperCase());
		msg.setDte(staticData.isDte() ? 0:1);
		msg.setSpare(0);
		return msg;
	}
	
}

package model.aismessages;

import dk.dma.ais.message.AisMessage5;
import dk.dma.ais.message.AisMessageException;
import model.StaticData;

public class AisMessage5Creator {
	
	/**
	 * Fonction permettant cr�er un message AisMessage5 � partir des donn�es pass�es en param�tres
	 * @param data : Liste des donn�es du message <nomduchamp,donn�e>
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

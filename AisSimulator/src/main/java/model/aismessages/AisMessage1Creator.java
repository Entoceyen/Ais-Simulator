package model.aismessages;

import dk.dma.ais.message.AisMessage1;
import dk.dma.ais.message.AisMessageException;
import model.DynamicData;

public abstract class AisMessage1Creator {

	/**
	 * Fonction permettant cr�er un message AisMessage1 � partir des donn�es pass�es en param�tres
	 * @param data : Liste des donn�es du message <nomduchamp,donn�e>
	 * @return Un objet de la classe AisMessage1 (AisLib)
	 * @throws AisMessageException
	 */
	public static AisMessage1 create(DynamicData dynData, int utcSec) throws AisMessageException {
		AisMessage1 msg = new AisMessage1();

		msg.setNavStatus(dynData.getNavStat().getCode());
		msg.setRot(dynData.getRot());
		msg.setSog(dynData.getSpeed());
		msg.setPosAcc(dynData.getPosAcc());
		msg.setPos(dynData.getPosition());
		msg.setCog(dynData.getCog());
		msg.setTrueHeading(dynData.getTrueHeading());
		msg.setUtcSec(utcSec);
		msg.setSpecialManIndicator(dynData.getSpecialManIndicator().getID());
		msg.setRaim(dynData.getRaim());
		msg.setSpare(0);
		msg.setSyncState(0);
		msg.setSlotTimeout(0);
		msg.setSubMessage(0);
		return msg;
	}
	
}

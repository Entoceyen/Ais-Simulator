package model.aismessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.function.Consumer;

import dk.dma.ais.binary.SixbitException;
import dk.dma.ais.message.AisMessage;
import dk.dma.ais.reader.AisReader;
import dk.dma.ais.reader.AisReaders;
import dk.dma.ais.sentence.CommentBlock;
import dk.dma.ais.sentence.Sentence;
import dk.dma.ais.sentence.Vdm;
import model.DynamicData;
import model.Simulation;

/**
 * Modèle de création des messages AIS
 */
public abstract class AisMessageCreator {
	
	/**
	 * Créé un message
	 * @param type int 1 ou 5
	 * @param simu Simulation 
	 * @param second int 
	 * @param instant int 
	 * @return Tableau de messages timestampé
	 * @throws Exception
	 */
	public static String[] create(int type, Simulation simu, int second, int instant) throws Exception {
		AisMessage message = null;
		if(type == 1) {
			Calendar c = Calendar.getInstance(); 
			c.setTimeInMillis(simu.getStartTime().getTimeInMillis()+second*1000);
			message = AisMessage1Creator.create(simu.getInstant(instant).getDynamicData(), c.get(Calendar.SECOND));
		} else if(type == 5) message = AisMessage5Creator.create(simu.getInstant(instant).getStaticData(), simu.getETA());
		else throw new Exception("Type de message inconnu");
		message.setUserId(simu.getInstant(instant).getStaticData().getMmsi());
		message.setRepeat(0);
		String[] lines = Vdm.createSentences(message, 0);
		int padBits = Integer.parseInt(lines[lines.length-1].split(",")[6].substring(0, 1));
		message = readSentences(lines);
		message.getVdm().setChannel(Calendar.getInstance().getTimeInMillis()%2 == 0 ? 'A':'B'); //On g�n�re A ou B selon la parit� de la milliseconde actuelle 
		message.getVdm().setPadBits(padBits);
		return createSentencesWithVdm(message);
	}
	
	/**
	 * Créé un message de type 1 en fonction de données dynamiques et d'un mmsi
	 * Utilisé par le scénario VesselSameID
	 * @param dynData DynamicData
	 * @param mmsi int
	 * @return tableau de message
	 * @throws Exception
	 */
	public static String[] create(DynamicData dynData, int mmsi) throws Exception {
		AisMessage message = null;
		Calendar c = Calendar.getInstance(); 
		message = AisMessage1Creator.create(dynData, c.get(Calendar.SECOND));
		message.setRepeat(0);
		message.setUserId(mmsi);
		String[] lines = Vdm.createSentences(message, 0);
		int padBits = Integer.parseInt(lines[lines.length-1].split(",")[6].substring(0, 1));
		message = readSentences(lines);
		message.getVdm().setChannel(Calendar.getInstance().getTimeInMillis()%2 == 0 ? 'A':'B'); //On g�n�re A ou B selon la parit� de la milliseconde actuelle 
		message.getVdm().setPadBits(padBits);
		return createSentencesWithVdm(message);
	}
	
	/**
	 * Fonction permettant la création des phrases NMEA correspondant à un message AIS pass� en paramètre.
	 * Utilise le Vdm compris dans le message.
	 * @param msg Objet de la classe AisMessage (AisLib)
	 * @return Un tableau des phrases NMEA générées pour le message msg
	 * @throws SixbitException
	 */
	private static String[] createSentencesWithVdm(AisMessage msg) throws SixbitException {
		Vdm[] vdms = msg.getVdm().createSentences();
		String[] sentences = new String[vdms.length];
		for(int i=0 ; i<vdms.length ; i++) {
			sentences[i] = vdms[i].getEncoded();
		}
		return sentences;
	}
	
	/**
	 * Retourne un TagBlock avec timestamp en s
	 * @param c date et heure
	 * @return tagBlock
	 */
	public static String createTagBlockS(Calendar c) {
		CommentBlock tag = new CommentBlock();
		tag.addTimestamp(c.getTime());
		return tag.encode();
	}
	
	/**
	 * Retourne un TagBlock avec timestamp en ms
	 * @param c date et heure 
	 * @return tagBlock
	 */
	public static String createTagBlockMs(Calendar c) {
		String lineStr = "c:"+c.getTimeInMillis();
		int checksum = 0;
        for (int i = 0; i < lineStr.length(); i++) checksum ^= lineStr.charAt(i);
		return "\\"+lineStr+"*"+Sentence.getStringChecksum(checksum)+"\\";
	}
	
	/**
	 * Lit les phrases nmea passées en paramètre et retourne le message correspondant
	 * @param lines tableau de phrase nmea correspondant à 1 message AIS
	 * @return AisMessage
	 * @throws InterruptedException
	 */
	private static AisMessage readSentences(String[] lines) throws InterruptedException {
		AisReader reader = AisReaders.createReaderFromInputStream(StringArrayToFileInputStream(lines));
		final AisMessage msg[] = new AisMessage[1];
		reader.registerHandler(new Consumer<AisMessage>() {
			@Override
			public void accept(AisMessage aisMessage) {
				msg[0] = aisMessage;
			}
		});
		reader.start();
		reader.join();
		return msg[0];
	}
	
	/**
	 * Converti un tableau de String en FileInputStream
	 * @param lines
	 * @return FileInputStream
	 */
	private static FileInputStream StringArrayToFileInputStream(String[] lines) {
		File f = new File("temp");
		f.deleteOnExit();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			for(String l : lines) {
				bw.write(l);
				bw.newLine();
			}
		} catch (Exception e) { System.out.println(e.getMessage()); }
		finally { 
			if (bw != null) try { bw.close(); } catch (IOException ignore) {}
		}
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) { return null;}
	}
}

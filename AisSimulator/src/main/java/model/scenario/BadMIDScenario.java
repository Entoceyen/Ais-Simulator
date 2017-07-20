package model.scenario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Modèle scénario générant un mauvais code pays (MID)
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class BadMIDScenario extends Scenario {

	private int badMID;
	
	/**
	 * Sauvegarde les anciens MID des instants modifiés
	 */
	private ArrayList<Integer> oldMID;
	
	/**
	 * Liste des MID existant. Créé à partir du fichier resources/codesMID.log.
	 */
	private static ArrayList<Integer> codesMID;
	
	/**
	 * Charge le fichier codesMID.log
	 */
	static {
		try {
			codesMID = loadMIDFile();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public BadMIDScenario(int startTime, int duration) {
		super(startTime, duration, false);
		badMID = generateBadMID();
		oldMID = new ArrayList<Integer>();
	}

	/**
	 * Modifie le MID sur la plage de valeur indiquée.
	 */
	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
			String mmsi = ""+getSimulation().getInstant(i).getStaticData().getMmsi();
			oldMID.add(Integer.parseInt(mmsi.substring(0,3)));
			mmsi = badMID+mmsi.substring(3);
			getSimulation().getInstant(i).getStaticData().setMmsi(Integer.parseInt(mmsi));
		}
	}

	/**
	 * Fonction de suppression du scénario
	 * Restaure les anciennes valeurs sur la plage de valeur modifié et supprime le scénario.
	 */
	@Override
	public void remove() {
		for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++) {
			String mmsi = ""+getSimulation().getInstant(i).getStaticData().getMmsi();
			if(mmsi.length() == 8) mmsi = "0" + mmsi;
			if(mmsi.length() == 7) mmsi = "00" + mmsi;
			mmsi = oldMID.get(j)+mmsi.substring(3);
			getSimulation().getInstant(i).getStaticData().setMmsi(Integer.parseInt(mmsi));
		}
		super.remove();
	}
	
	public int getBadMID() {
		return badMID;
	}
	
	/**
	 * Lit et charge le fichier des codes MID existant en mémoire
	 * @return ArrayList d'entier
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static ArrayList<Integer> loadMIDFile() throws IOException, URISyntaxException {
		ArrayList<Integer> mids = new ArrayList<Integer>();
		URL url = ClassLoader.getSystemResource("codesMID.log");
		FileInputStream fis = new FileInputStream(new File(url.toURI()));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			mids.add(Integer.parseInt(line.substring(0, 3)));
		}
		br.close();
		
		return mids;
	}
	
	/**
	 * Génére aléatoirement un code MID invalide
	 * @return int code pays invalide
	 */
	private int generateBadMID() {
		int mid;
		do {
			Random rand = new Random();
			mid = rand.nextInt(999 - 0 + 1) + 0;
		} while(codesMID.contains(mid));
		return mid;
	}
	
	public String description() {
		return "Faux MID - Moment d'arrivée : "+getStartTime()+", Durée : "+getDuration();
	}
	
}

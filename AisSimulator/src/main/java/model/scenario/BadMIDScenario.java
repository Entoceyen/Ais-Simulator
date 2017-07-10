package model.scenario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BadMIDScenario extends Scenario {

	private int badMID;
	private static ArrayList<Integer> codesMID;
	
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
	}

	@Override
	public void apply() {
		for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
			String mmsi = ""+getSimulation().getInstant(i).getStaticData().getMmsi();
			mmsi = badMID+mmsi.substring(3);
			getSimulation().getInstant(i).getStaticData().setMmsi(Integer.parseInt(mmsi));
		}
	}

	@Override
	public void remove() {
	}
	
	public int getBadMID() {
		return badMID;
	}
	
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
	
	private int generateBadMID() {
		int mid;
		do {
			Random rand = new Random();
			mid = rand.nextInt(999 - 0 + 1) + 0;
		} while(codesMID.contains(mid));
		return mid;
	}
	
	public static HashMap<String,Object> getDataType() {
		HashMap<String,Object> dataType = Scenario.getDataType();
		dataType.put("SCENARIO", Scenarios.BadMIDScenario);
		return dataType;
	}
	
}

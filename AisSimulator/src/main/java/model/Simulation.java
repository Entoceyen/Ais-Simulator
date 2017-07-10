package model;

import java.util.ArrayList;
import java.util.Calendar;

import dk.dma.ais.message.AisPosition;
import model.Calculator;
import model.aismessages.AisStream;
import model.scenario.Scenario;

public class Simulation {
	
	private DynamicData dynData;
	private StaticData staticData;
	
	private ArrayList<InstantSimulation> instants;
	private ArrayList<Scenario> scenarios;
	private AisStream aisStream;
	
	private Path path;
	private double distance;
	private long ETA;
	private Calendar startTime;
	
	public Simulation() {
		instants = new ArrayList<InstantSimulation>();
		scenarios = new ArrayList<Scenario>();
	}
	
	/**
	 * Calcule tout les instants à partir du numéro n
	 * @param n : instant n 
	 */
	public void compute(int n) {
		InstantSimulation instantNext;
		do {
			InstantSimulation instant = getInstant(n);
			instantNext = instant.computeNext();
			instants.add(instantNext);
			n++;
		} while(!instantNext.isEnd());
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}

	public void addInstant(InstantSimulation instant) {
		instants.add(instant);
	}
	
	public InstantSimulation getInstant(int instant) {
		try {
			return instants.get(instant);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Retourne le nombre totale d'instant
	 * @return
	 */
	public int getSize() {
		return instants.size();
	}
	
	public Scenario getScenario(int i) {
		return scenarios.get(i);
	}
	
	public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}
	
	/**
	 * Calcule la distance totale du trajet path en miles nautiques
	 */
	public void computeDistance() {
		double distance = 0;
		for(int i=0 ; i<path.getNbSteps()-1 ; i++) {
			AisPosition first = path.getStep(i);
			AisPosition second = path.getStep(i+1);
			distance += Calculator.distance(first.getLatitudeDouble(), first.getLongitudeDouble(), second.getLatitudeDouble(), second.getLongitudeDouble(), "N");
		}
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public long getETA() {
		return ETA;
	}
	
	public Calendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public DynamicData getDynamicData() {
		return dynData;
	}

	/**
	 * Initialise les données dynamique
	 * @param dynData
	 */
	public void setDynamicData(DynamicData dynData) {
		dynData.setPosition(path.getStep(0));
		int heading = Calculator.bearing(
				dynData.getPosition().getLatitudeDouble(),
				dynData.getPosition().getLongitudeDouble(),
				path.getStep(1).getLatitudeDouble(),
				path.getStep(1).getLongitudeDouble());
		dynData.setTrueHeading(heading);
		dynData.setCog(heading*10);
		this.dynData = dynData;
	}

	public StaticData getStaticData() {
		return staticData;
	}

	public void setStaticData(StaticData staticData) {
		this.staticData = staticData;
	}
	
	public AisStream getAisStream() {
		return aisStream;
	}
	
	public void setAisStream(AisStream stream) {
		aisStream = stream;
	}
	
	/**
	 * Initialise et calcule la simulation avec les données du formulaire 
	 * @param sd StaticData
	 * @param dd DynamicData
	 * @param c Calendar moment de départ
	 */
	public void init(StaticData sd, DynamicData dd, Calendar c) {
		setDynamicData(dd);
		setStaticData(sd);
		if(c != null) setStartTime(c);
		computeDistance();
		initFirstInstant();
		compute(0);
	}
	
	/**
	 * Calcule le 1er InstantSimulation
	 */
	private void initFirstInstant() {
		InstantSimulation instant0 = new InstantSimulation();
		instant0.setDynamicData(dynData);
		instant0.setStaticData(staticData);
		instant0.computeNextStepRadius();
		instant0.setSimulation(this);
		addInstant(instant0);
	}
	
	/**
	 * Indique pour un instant à la position (seconde) id si la route change
	 * @param id seconde relative à la simulation correspondant à un InstantSimulation
	 * @param instant InstantSimulation
	 * @return true si la route/cap change, falsee sinon
	 */
	protected boolean isRouteChange(int id, InstantSimulation instant) {
		int average = 0;
		for(int i=id-1 ; i>id-31 ; i--)
			average += getInstant(i) != null ? getInstant(i).getDynamicData().getTrueHeading():getInstant(0).getDynamicData().getTrueHeading();
		average /= 30;
		if(Math.abs(instant.getDynamicData().getTrueHeading()-average) > 5) return true;
		else return false;
	}
	
	/**
	 * Calcule pour un instant à la position (seconde) id le ROTsensor
	 * @param id seconde relative à la simulation correspondant à un InstantSimulation
	 * @param instant InstantSimulation
	 * @return int de -708 à 708°
	 */
	protected int computeROTsensor(int id, InstantSimulation instant) {
		int ROTsensor = 0;
		for(int i=id ; i>id-61 ; i--) {
			if(getInstant(i) == null) continue;
			int diff = getInstant(i).getDynamicData().getTrueHeading() - getInstant(i-1).getDynamicData().getTrueHeading();
			if((getInstant(i).getDynamicData().getTrueHeading() < 270 
				&& getInstant(i).getDynamicData().getTrueHeading() <= 359
				&& getInstant(i-1).getDynamicData().getTrueHeading() <= 0
				&& getInstant(i-1).getDynamicData().getTrueHeading() < 90)
				|| (getInstant(i-1).getDynamicData().getTrueHeading() < 270 
				&& getInstant(i-1).getDynamicData().getTrueHeading() <= 359
				&& getInstant(i).getDynamicData().getTrueHeading() <= 0
				&& getInstant(i).getDynamicData().getTrueHeading() < 90))
				ROTsensor += (360-Math.abs(diff))*-(diff/diff);
			else ROTsensor += diff;
		}
		if(Math.abs(ROTsensor) > 708) ROTsensor = (int) (708*Math.signum(ROTsensor));
		return ROTsensor;
	}
	
	/**
	 * Fonction retournant la valeur (long) ETA, encodée (voir ci-dessous), à partir des valeurs "lisible"
	 * Bits 19-16: mois; 1-12; 0 = non disponible = par défaut 
	 * Bits 15-11: jour; 1-31; 0 = non disponible = par défaut 
	 * Bits 10-6: heure; 0-23; 24 = non disponible = par défaut 
	 * Bits 5-0: minute; 0-59; 60 = non disponible = par défaut
	 * @return long
	 */
	public long etaEncoder() {
		Calendar c = startTime;
		c.add(Calendar.SECOND, getSize());
		String binMin = Integer.toBinaryString(c.get(Calendar.MINUTE));
		String binHour = Integer.toBinaryString(c.get(Calendar.HOUR_OF_DAY));
		String binDay = Integer.toBinaryString(c.get(Calendar.DAY_OF_MONTH));
		String binMonth = Integer.toBinaryString(c.get(Calendar.MONTH)+1);
		
		int nb0 = 0;
		if(binMin.length() != 6) {
			nb0 = 6-binMin.length();
			for(int i=0 ; i<nb0; i++) binMin = "0"+binMin;
		}
		if(binHour.length() != 5) {
			nb0 = 5-binHour.length();
			for(int i=0 ; i<nb0; i++) binHour = "0"+binHour;
		}
		if(binDay.length() != 5) {
			nb0 = 5-binDay.length();
			for(int i=0 ; i<nb0; i++) binDay = "0"+binDay;
		}
		String binEta = binMonth + binDay + binHour + binMin;
		long eta = Long.parseLong(binEta, 2);
		return eta;
	}
		
	public String toString() {
		return "Simulation [\n"+dynData.toString()+",\n"
			 + "\t"+staticData.toString()+",\n"
	 		 + "\t"+path.toString()+",\n"
	 		 + "\tdistance ["+distance+"]\n"
	 		 + "\tETA ["+ETA+"],\n"
	 		 + "\tstartTime ["+startTime+"]\n]";
	}
}
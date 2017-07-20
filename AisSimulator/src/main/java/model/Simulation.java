package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import dk.dma.ais.message.AisPosition;
import model.Calculator;
import model.aismessages.AisStream;
import model.scenario.ChangeDelayMessageScenario;
import model.scenario.Scenario;

/**
 * Modèle correspondant à la simulation globale
 */
public class Simulation {
	
	/**
	 * Données dynamiques et statique de l'état initial
	 */
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
	 * Calcule les instants à partir du numéro n en prennant en compte les scénarios
	 * Utilise la fonction basiCompute
	 * @param n : intant n
	 */
	public void compute(int n) {
		ArrayList<Scenario> computableScenarios = getComputableScenarios();
		System.out.println(computableScenarios);
		if(computableScenarios.size() == 0)
			basicCompute(n);
		else
			for(Scenario s : computableScenarios) {
				if(s.getStartTime() < n) continue;
				if(s.getStartTime() > getSize()) continue;
				s.apply();
				basicCompute(s.getStartTime());
			}
		for(Scenario s : getNoComputableScenarios()) s.apply();
	}
	
	/**
	 * Calcule tout les instants à partir du numéro n
	 * @param n : instant n 
	 */
	private void basicCompute(int n) {
		remove(n);
		InstantSimulation instantNext;
		do {
			InstantSimulation instant = getInstant(n);
			instantNext = instant.computeNext();
			instants.add(instantNext);
			n++;
		} while(!instantNext.isEnd());
	}

	/**
	 * Supprime tout les instants après le numéro n
	 * @param n
	 */
	private void remove(int n) {
		for(int i=getSize()-1 ; i>n ; i--) instants.remove(i);
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
	
	public ArrayList<Scenario> getScenarios() {
		return scenarios;
	}
	
	public Scenario getScenario(int i) {
		return scenarios.get(i);
	}
	
	public void addScenario(Scenario scenario) {
		scenario.setSimulation(this);
		scenarios.add(scenario);
	}
	
	/**
	 * Cherche et retourne le scénario de changement des fréquences d'émission des messages s'il a été ajouté
	 * @return ChangeDelayMessageScenario s'il existe, sinon null
	 */
	public ChangeDelayMessageScenario getChangeDelayScenario() {
		for(Scenario s : scenarios)
			if(s instanceof ChangeDelayMessageScenario) return (ChangeDelayMessageScenario) s;
		return null;
	}
	
	/**
	 * Retourne la liste des scénarios necessitant un calcul ordonnés en fonction du moment d'arrivé 
	 * @return ArrayList de Scenario
	 */
	private ArrayList<Scenario> getComputableScenarios() {
		ArrayList<Scenario> computableScenario = new ArrayList<Scenario>();
		for(Scenario s : scenarios)
			if(s.isCompute()) computableScenario.add(s);
		Comparator<Scenario> comparator = new Comparator<Scenario>() {
			@Override
			public int compare(Scenario s1, Scenario s2) {
				if(s1.getStartTime() < s2.getStartTime()) return -1;
				if(s1.getStartTime() > s2.getStartTime()) return 1;
				return 0;
			}
		};
		computableScenario.sort(comparator);
		return computableScenario;
	}
	
	/**
	 * Retourne la liste des scénarios ne necessitant pas de calcul 
	 * @return ArrayList de Scenario
	 */
	private ArrayList<Scenario> getNoComputableScenarios() {
		ArrayList<Scenario> noComputableScenario = new ArrayList<Scenario>();
		for(Scenario s : scenarios)
			if(!s.isCompute()) noComputableScenario.add(s);
		return noComputableScenario;
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
	 * @throws Exception
	 */
	public void setDynamicData(DynamicData dynData) throws Exception {
		if(path == null) throw new Exception();
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
	 * @throws Exception 
	 */
	public void init(StaticData sd, DynamicData dd, Calendar c) throws Exception {
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
	 * @return true si la route/cap change, false sinon
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
	 * @return int de -708 � 708�
	 */
	protected int computeROTsensor(int id, InstantSimulation instant) {
		int ROTsensor = 0;
		for(int i=id ; i>id-61 ; i--) {
			if(getInstant(i) == null || getInstant(i-1) == null) continue;
			int diff = getInstant(i).getDynamicData().getTrueHeading() - getInstant(i-1).getDynamicData().getTrueHeading();
			if((getInstant(i).getDynamicData().getTrueHeading() > 270 
				&& getInstant(i).getDynamicData().getTrueHeading() <= 359
				&& getInstant(i-1).getDynamicData().getTrueHeading() >= 0
				&& getInstant(i-1).getDynamicData().getTrueHeading() < 90)
				|| (getInstant(i-1).getDynamicData().getTrueHeading() > 270 
				&& getInstant(i-1).getDynamicData().getTrueHeading() <= 359
				&& getInstant(i).getDynamicData().getTrueHeading() >= 0
				&& getInstant(i).getDynamicData().getTrueHeading() < 90)) {
				if(diff == 0) continue;
				ROTsensor += (360-Math.abs(diff))*(diff/Math.abs(diff));
			}
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
		Calendar c = (Calendar) startTime.clone();
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
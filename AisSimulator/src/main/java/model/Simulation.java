package model;

import java.util.ArrayList;
import java.util.Calendar;

import dk.dma.ais.message.AisPosition;
import model.Calculator;

public class Simulation {
	
	private DynamicData dynData;
	private StaticData staticData;
	
	private ArrayList<InstantSimulation> instants;
	
	private Path path;
	private double distance;
	private long ETA;
	private Calendar startTime;
	
	public Simulation() {
		instants = new ArrayList<InstantSimulation>();
	}
	
	/**
	 * Calcule tout les instants à partir du numéro n
	 * @param n : instant n 
	 */
	/*public void compute(int n) {
		System.out.println(n);
		InstantSimulation instant = getInstant(n);
		InstantSimulation instantNext = instant.computeNext();
		instants.add(instantNext);
		System.out.println(instantNext.isEnd());
		if(!instantNext.isEnd()) compute(n+1);
	}*/
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
		//TODO 
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
		return ROTsensor;
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
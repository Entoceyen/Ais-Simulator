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
	public void compute(int n) {
		// TODO
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}

	public InstantSimulation getInstant(int instant) {
		return instants.get(instant);
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
	
	public String toString() {
		return "Simulation [\n"+dynData.toString()+",\n"
			 + "\t"+staticData.toString()+",\n"
	 		 + "\t"+path.toString()+",\n"
	 		 + "\tdistance ["+distance+"]\n"
	 		 + "\tETA ["+ETA+"],\n"
	 		 + "\tstartTime ["+startTime+"]\n]";
	}
	
	
}
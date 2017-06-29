package model;

import model.Calculator;

/**
 * Mod�le correspondant � un instant de simulation, soit l'ensemble des donn�es � une seconde
 */
public class InstantSimulation implements Cloneable {
	
	/**
	 * Indique le dernier InstantSimulation de la simulation
	 */
	private boolean end;
	/**
	 * indique si l'instant doit �tre omis/pris en compte
	 */
	private boolean cut;
	/**
	 * indique si un message AIS peut �tre cr�� sur cet instant
	 */
	private boolean sendable = true;
	/**
	 * indique si un bateau identique doit apparaitre/continuer � cet instant
	 */
	private boolean vesselSameID;
	/**
	 * indique i le bateau change de route/cap
	 */
	private boolean changeRoute;
	/**
	 * indice de la prochaine �tape dans le trajet path
	 */
	private int nextStepID = 1;
	/**
	 * zone consid�r�e dans laquelle le bateau atteint l'�tape suivante
	 */
	private double nextStepRadius;
	
	private Simulation simulation;
	private StaticData staticData;
	private DynamicData dynamicData;
	
	public boolean isEnd() {
		return end;
	}
	
	public boolean isCut() {
		return cut;
	}

	public boolean isSendable() {
		return sendable;
	}

	public boolean isVesselSameID() {
		return vesselSameID;
	}
	
	public boolean isChangeRoute() {
		return changeRoute;
	}

	public void setChangeRoute(boolean changeRoute) {
		this.changeRoute = changeRoute;
	}

	public void setSimulation(Simulation s) {
		simulation = s;
	}
	
	public StaticData getStaticData() {
		return staticData;
	}

	public void setStaticData(StaticData staticData) {
		this.staticData = staticData;
	}

	public DynamicData getDynamicData() {
		return dynamicData;
	}

	public void setDynamicData(DynamicData dynamicData) {
		this.dynamicData = dynamicData;
	}
	
	/**
	 * Calcule le rayon de la zone autour de l'�tape suivante en fonction de la vitesse
	 * Doit �tre mis � jour en cas de changement de vitesse
	 */
	public void computeNextStepRadius() {
		nextStepRadius = (dynamicData.getSpeed()*1.0) / (2.0*3600.0);
	}
	
	/**
	 * Calcule les nouvelles coordonn�es en fonction de l'instant pr�c�dent
	 */
	private void computePosition() {
		double distance = (Calculator.knot2Kmh(dynamicData.getSpeed()/10)/3600) * 1000;
		Coordinate c = dynamicData.getPosition();
		double[] pos = Calculator.positionRhumb(c.getLatitudeDouble(), c.getLongitudeDouble(), dynamicData.getTrueHeading(), distance);
		dynamicData.setPosition(new Coordinate(pos[0],pos[1]));
	}
	
	/**
	 * Calcule du cap en fonction de la position et de l'�tape suivante
	 * La position doit au pr�alable �tre mise � jour
	 * D�tecte aussi si cet instant est le dernier sur le trajet
	 */
	private void computeHeading() {
		Coordinate c = dynamicData.getPosition();
		int heading = dynamicData.getTrueHeading();
		try {
			Coordinate c1 = simulation.getPath().getStep(nextStepID);
			heading = Calculator.bearing(c.getLatitudeDouble(), c.getLongitudeDouble(), c1.getLatitudeDouble(), c1.getLongitudeDouble());
		} catch (IndexOutOfBoundsException e) {
			end = true;
		}
		dynamicData.setTrueHeading(heading);
		dynamicData.setCog(heading*10);
	}
	
	/**
	 * Calcule le ROTais en fonction du ROTsensor
	 */
	public void computeROT() {
		if(!changeRoute) {
			dynamicData.setRot(0);
			return;
		}
		int ROTsensor = simulation.computeROTsensor(simulation.getSize(), this);
		int ROTais = (int) (Math.signum(ROTsensor) * Math.round(4.733 * Math.sqrt(Math.abs(ROTsensor))));
		dynamicData.setRot(ROTais);
	}
	
	/**
	 * Clonage en profondeur de l'InstantSimulation
	 */
	@Override
	public InstantSimulation clone() {
		InstantSimulation instant = null;
		try {
			instant = (InstantSimulation) super.clone();
		} catch (CloneNotSupportedException e) {}
		instant.staticData = staticData.clone();
		instant.dynamicData = dynamicData.clone();
		return instant;
	}
	
	/**
	 * Calcule et retourne l'InstantSimulation suivant
	 * @return InstantSimulation ou null (signifie la fin)
	 */
	public InstantSimulation computeNext() {
		InstantSimulation instant = this.clone();
		instant.computePosition();
		Coordinate c = instant.getDynamicData().getPosition();
		Coordinate c1 = simulation.getPath().getStep(nextStepID);
		double distance2NextStep = Calculator.distance(c.getLatitudeDouble(), c.getLongitudeDouble(), c1.getLatitudeDouble(), c1.getLongitudeDouble(), "N");
		if(distance2NextStep < nextStepRadius) instant.nextStepID++;
		instant.computeHeading();
		instant.changeRoute = simulation.isRouteChange(simulation.getSize(), instant);
		instant.computeROT();
		return instant;
	}

	@Override
	public String toString() {
		return "InstantSimulation [\n end=" + end + ", cut=" + cut + ", sendable=" + sendable + ", vesselSameID="
				+ vesselSameID + ", changeRoute=" + changeRoute + ", nextStepID=" + nextStepID + ", nextStepRadius="
				+ nextStepRadius + ",\n staticData=" + staticData + ",\n dynamicData=" + dynamicData + "\n]";
	}
	
}
package model;

import java.util.*;

/**
 * Modèle correspondant au trajet du bateau
 */
public class Path {
	
	/**
	 * Suite de point de coordonnées ordonnées
	 */
	private ArrayList<Coordinate> steps;
	private String name;

    public Path() {
    	steps = new ArrayList<Coordinate>();
    }

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    /**
     * Ajoute un point de coordonnées étape
     * @param c : Coordinate
     */
    public void addStep(Coordinate c) {
        steps.add(c);
    }
    
    /**
     * Retourne le nombre d'étape
     * @return entier
     */
    public int getNbSteps() {
    	return steps.size();
    }
    
    /**
     * Retourne le point de coordonnées à l'indice i dans la liste
     * @param i : numéro de l'étape dans la liste
     * @return Coordinate
     */
    public Coordinate getStep(int i) {
    	return steps.get(i);
    }
    
    public String toString() {
		return "Path "+steps.toString();
    }

}
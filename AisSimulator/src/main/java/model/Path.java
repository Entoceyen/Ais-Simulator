package model;

import java.util.*;

/**
 * Mod�le correspondant au trajet du bateau
 */
public class Path {
	
	/**
	 * Suite de point de coordonn�es ordonn�es
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
     * Ajoute un point de coordonn�es �tape
     * @param c : Coordinate
     */
    public void addStep(Coordinate c) {
        steps.add(c);
    }
    
    /**
     * Retourne le nombre d'�tape
     * @return entier
     */
    public int getNbSteps() {
    	return steps.size();
    }
    
    /**
     * Retourne le point de coordonn�es � l'indice i dans la liste
     * @param i : num�ro de l'�tape dans la liste
     * @return Coordinate
     */
    public Coordinate getStep(int i) {
    	return steps.get(i);
    }
    
    public String toString() {
		return "Path "+steps.toString();
    }

}
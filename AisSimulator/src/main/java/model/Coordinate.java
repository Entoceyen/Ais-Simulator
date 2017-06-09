package model;

import dk.dma.ais.message.AisPosition;

/**
 * Modèle correspondant à une cordonnée gps
 */
public class Coordinate extends AisPosition {
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor
     */
    public Coordinate(double lat, double lon) {
    	super((long) (lat*600000), (long) (lon*600000));
    }

    public String toString() {
		return "["+getLatitudeDouble()+":"+getLongitudeDouble()+"]";
    }

}
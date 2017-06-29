package model;

import dk.dma.ais.message.AisPosition;

/**
 * Mod�le correspondant � une cordonn�e gps
 */
public class Coordinate extends AisPosition implements Cloneable {
	private static final long serialVersionUID = 1L;

    public Coordinate(double lat, double lon) {
    	super((long) (lat*600000), (long) (lon*600000));
    }

    @Override
    public Coordinate clone() {
    	Coordinate coord = null;
    	try {
			coord = (Coordinate) super.clone();
		} catch (CloneNotSupportedException e) {}
    	return coord;
    }
    
    public String toString() {
		return "["+getLatitudeDouble()+":"+getLongitudeDouble()+"]";
    }

}
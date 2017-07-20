package model;

import dk.dma.ais.message.AisPosition;

/**
 * Modèle correspondant à une cordonnée gps
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
    
    public void setLatitude(double lat) {
    	setRawLatitude((long) (lat*600000));
    }
    
    public void setLongitude(double lon) {
    	setRawLongitude((long) (lon*600000));
    }
    
    public String toString() {
		return "["+getLatitudeDouble()+":"+getLongitudeDouble()+"]";
    }

}
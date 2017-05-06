package main.java.models;

import java.io.Serializable;
import main.java.constants.SensorType;

/**
 * This class represents the sensor
 * 
 * @author dinukshakandasamanage
 */
public class Sensor implements Serializable{
//    private SensorType type;
    private String location;
    private Reading latestReading;

    public Sensor() {
    }

//    public Sensor(SensorType type, String location, double latestReading) {
//        this.type = type;
//        this.location = location;
//        this.latestReading = latestReading;
//    }

//    public SensorType getType() {
//        return type;
//    }
//
//    public void setType(SensorType type) {
//        this.type = type;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Reading getLatestReading() {
        return latestReading;
    }

    public void setLatestReading(Reading latestReading) {
        this.latestReading = latestReading;
    }

    @Override
    public String toString() {
        return "Sensor{" + "location=" + location + ", latestReading=" + latestReading + '}';
    }

    
    
}

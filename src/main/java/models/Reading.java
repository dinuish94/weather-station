/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.models;

import java.io.Serializable;

/**
 *
 * @author dinukshakandasamanage
 */
public class Reading implements Serializable{
    private double temperature;
    private double rainfall;
    private double airpressure;
    private int humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getRainfall() {
        return rainfall;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    public double getAirpressure() {
        return airpressure;
    }

    public void setAirpressure(double airpressure) {
        this.airpressure = airpressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Reading{" + "temperature=" + temperature + ", rainfall=" + rainfall + ", airpressure=" + airpressure + ", humidity=" + humidity + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.temperature) ^ (Double.doubleToLongBits(this.temperature) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.rainfall) ^ (Double.doubleToLongBits(this.rainfall) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.airpressure) ^ (Double.doubleToLongBits(this.airpressure) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.humidity) ^ (Double.doubleToLongBits(this.humidity) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reading other = (Reading) obj;
        if (Double.doubleToLongBits(this.temperature) != Double.doubleToLongBits(other.temperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.rainfall) != Double.doubleToLongBits(other.rainfall)) {
            return false;
        }
        if (Double.doubleToLongBits(this.airpressure) != Double.doubleToLongBits(other.airpressure)) {
            return false;
        }
        if (Double.doubleToLongBits(this.humidity) != Double.doubleToLongBits(other.humidity)) {
            return false;
        }
        return true;
    }
    
    
}

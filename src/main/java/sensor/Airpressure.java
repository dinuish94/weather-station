package main.java.sensor;

import java.util.Random;

/**
 *
 * @author dinuksha
 */
public class Airpressure implements SensorReading{

    private double airPressure;
    
    public Airpressure() {
        airPressure = 18.64;
    }
    
    @Override
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide weather reading goes up or down
        if( airPressure > 20 ){
        
        }
        int num = r.nextInt();
        if (num < 0) {
                airPressure += 0.5;
        } else {
                airPressure -= 0.5;
        }
        return Math.round(airPressure * 100.0) / 100.0;
    }
    
}

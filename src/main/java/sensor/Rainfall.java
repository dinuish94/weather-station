package main.java.sensor;

import java.util.Random;

/**
 *
 * @author dinuksha
 */
public class Rainfall implements SensorReading{

    private double rainfall;
    
    public Rainfall() {
        rainfall = 15.0;
    }
    
    @Override
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide weather reading goes up or down
        int num = r.nextInt();
        if (num < 0) {
                rainfall += 0.2;
        } else {
                rainfall -= 0.2;
        }
        return Math.round(rainfall * 100.0) / 100.0;
    }
    
}

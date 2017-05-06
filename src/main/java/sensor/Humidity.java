package main.java.sensor;

import java.util.Random;

/**
 *
 * @author dinuksha
 */
public class Humidity implements SensorReading{
    private int humidity;
    
    public Humidity() {
        humidity = 80;
    }
    
    public double getReading(){
        Random r = new Random();
        // Get random number, to decide weather reading goes up or down
        int num = r.nextInt();
        if (num < 0) {
                humidity += 3;
        } else {
                humidity -= 3;
        }
        
        // if it exceeds 100 reinitiaize
        if( humidity >= 100 ){
            humidity = 82;
        }
        return humidity;
    }
}

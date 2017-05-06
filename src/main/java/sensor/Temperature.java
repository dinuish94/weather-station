package main.java.sensor;

import java.util.Random;

/**
 *
 * @author dinuksha
 */
public class Temperature implements SensorReading{
    
    private double temp;
    
    public Temperature() {
        temp = 33.0;
    }
    
    @Override
    public double getReading(){
        Random r = new Random();
        if (temp > 35.0){
            temp = 33.0;
        }else{
            // Get random number, to decide weather reading goes up or down
            int num = r.nextInt();
            if (num < 0) {
                    temp += 0.5;
            } else {
                    temp -= 0.5;
            }
        }
        return temp;
    }
   
}

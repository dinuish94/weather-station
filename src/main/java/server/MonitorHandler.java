package main.java.server;

import java.util.List;
import main.java.models.Sensor;

/**
 * Handles the monitors connected to the server
 * 
 * @author dinukshakandasamanage
 */
public class MonitorHandler extends Thread {
        
        private final WeatherStationServer weatherStationServer;
        private List<Sensor> sensors;
        
        public MonitorHandler(WeatherStationServer weatherStationServer, List<Sensor> sensors) {
            this.weatherStationServer = weatherStationServer;
            this.sensors = sensors;
        }
        
        @Override
        public void run(){
            
            while(true){
                
                try {
                    Thread.sleep(3600000);
                    synchronized( sensors ){
                        // Notify listeners every hour
                        weatherStationServer.notifyListeners(sensors);
                    }
                    System.out.println("Thread Success");
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

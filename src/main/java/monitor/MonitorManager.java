package main.java.monitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.models.Sensor;
import main.java.server.WeatherStationServerRemote;

/**
 * Handles the sensor UIs and the readings shown on them
 * 
 * @author dinuksha
 */
public class MonitorManager {
    
    private Map<String,SensorMonitorGUI> sensorMonitors = new HashMap<>();
    private List<String> sensorLocations = new ArrayList();
    
    /**
     * Adding a new monitor/s or updating the existing readings
     * @param monitorGUI
     * @param sensors 
     */
    public void addMonitor(MonitorGUI monitorGUI,List<Sensor> sensors){
        
        sensors.forEach(sensor ->{
            SensorMonitorGUI monitor;
            String location = sensor.getLocation();
            
            // If the monitor for the relevant location has not been initialized yet
            if(!sensorMonitors.containsKey(location)){
                monitor = new SensorMonitorGUI(location);
                // Storing each monitor with reference to its location
                sensorMonitors.put(location, monitor);
                // Adding new monitor
                monitorGUI.addMonitor(monitor);
                // Add location to Combo box
                monitorGUI.fillLocations(location);
                // Add location to a list
                sensorLocations.add(location);
            }
            else{
                monitor = sensorMonitors.get(sensor.getLocation());
            }
            setReading(sensor, monitor);
        });
        
    }
    
    /**
     * Updating the monitor reading when readings are required
     * for a particular location
     * @param monitorGUI
     * @param sensor 
     */
    public void updateMonitor(MonitorGUI monitorGUI, Sensor sensor){
        if(sensorMonitors.containsKey(sensor.getLocation())){
            SensorMonitorGUI monitor = sensorMonitors.get(sensor.getLocation());
            setReading(sensor, monitor);
        }
    }
    
    /**
     * Set readings in the UI
     * @param sensor
     * @param monitor 
     */
    public void setReading(Sensor sensor,SensorMonitorGUI monitor){

        monitor.setTemperature(sensor.getLatestReading().getTemperature());
        monitor.setAirPressure(sensor.getLatestReading().getAirpressure());
        monitor.setHumidity(sensor.getLatestReading().getHumidity());
        monitor.setRainfall(sensor.getLatestReading().getRainfall());
                
    } 
    
    /**
     * Initiate a new monitor
     */
    public void startMonitor(){
        try {

            // Retrieving remote object
            WeatherStationServerRemote temperatureSensorServerRemote = (WeatherStationServerRemote) Naming.lookup("rmi://localhost/weather");

            // Initializing a weather monitor instance
            WeatherMonitorController monitor = new WeatherMonitorController(temperatureSensorServerRemote);
            monitor.report();
            // Adding the monitor instance to server by invoking the method through the remote interface provided
            temperatureSensorServerRemote.addWeatherMonitor(monitor);

            monitor.initiateMonitorWindow(monitor);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    
    }
    
}

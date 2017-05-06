package main.java.server;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import main.java.exceptions.SensorNotFoundException;
import main.java.models.Sensor;
import main.java.monitor.WeatherListener;

/**
 * Implementation class of the Remote Interface TemperatureSensorServer Remote
 * 
 * @author dinuksha
 */
public class WeatherStationServer extends UnicastRemoteObject implements WeatherStationServerRemote{

    //Weather Sensors that are connected to the server
    private List<Sensor> sensors= new ArrayList<>();
    
    //Weather monitors that are connected to the server
    private ArrayList<WeatherListener> monitors = new ArrayList<>();

    public WeatherStationServer() throws RemoteException{
    }  

    @Override
    public int getSensorCount() throws RemoteException{
        return sensors.size();
    }
    
    @Override
    public int getMonitorCount() throws RemoteException{
        return monitors.size();
    }

    @Override
    public List<Sensor> getSensor() throws RemoteException{
        return sensors;
    }
    
    @Override
    public void addWeatherMonitor(WeatherListener weatherListener) throws RemoteException {
        // Other threads can only read sensor list while a sensor is being added
        System.out.println("adding listener -" + weatherListener);
        synchronized(monitors){
            monitors.add(weatherListener);
        }
        // Start Thread for this monitor
        new MonitorHandler(this,sensors).start();
    }
    
    @Override
    public void removeWeatherMonitor(WeatherListener weatherListener) throws RemoteException {
        // Other threads can only read monitor list while a monitor is being removed
        synchronized(monitors){
            monitors.remove(weatherListener); 
        }
    }
    
    @Override
    public void getLatestReadings(WeatherListener weatherListener) throws RemoteException {
        // When the latest readings are requested, update only the requested monitor
        weatherListener.weatherChanged(sensors, getMonitorCount(), getSensorCount());
    }
    
    @Override
    public Sensor getLatestReadingsByLocation(WeatherListener weatherListener, String location) throws SensorNotFoundException{
        for(Sensor sensor:sensors){
            if (sensor.getLocation().equals(location)){
                return sensor;
            }
        }
        throw new SensorNotFoundException();
    }
    
    /**
     * Adds a new sensor
     * @param sensor 
     */
    public void addSensor(Sensor sensor) {
        // Other threads can only read sensor list while a sensor is being added
        synchronized(sensors){
            this.sensors.add(sensor);
        }
    }
    
    /**
     * Removes a particular sensor
     * @param sensor 
     */
    void removeSensor(Sensor sensor) {
        // Other threads can only read sensor list while a sensor is being removed
        synchronized(sensors){
            this.sensors.remove(sensor);
        }
    }
    
    /**
     * Alerts all monitors in case the weather levels reach critical levels
     * @param sensor 
     */
    public void alertMonitors(Sensor sensor){
        
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    System.out.println("Alert - WeatherStationServer");
                    // Calling the remote method of the WeatherListener Interface
                    listener.alertMonitor(sensor);
                } catch (RemoteException ex) {
                    try {
                        // Remove weather monitor if it fails
                        removeWeatherMonitor(listener);
                    } catch (RemoteException ex1) {
                        System.err.println(ex1.getMessage());
                    }
                }
            });
        }
    }
    
    /**
     * Alerts all monitors if a sensor fails to send updates
     * @param sensor 
     */
    public void alertSensorFailure(Sensor sensor){
        
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    System.out.println("Alert - WeatherStationServer");
                    // Calling the remote method of the WeatherListener Interface
                    listener.notifySensorFailure(sensor);
                } catch (RemoteException ex) {
                    try {
                        // Remove weather monitor if it fails
                        removeWeatherMonitor(listener);
                    } catch (RemoteException ex1) {
                        System.err.println(ex1.getMessage());
                    }
                }
            });
        }
    }
    
    /**
     * Notifies monitors on hourly updates
     * @param sensors
     */
    public void notifyListeners(List<Sensor> sensors) {
        if(!monitors.isEmpty()){
            // Notify all the listeners in the list
            monitors.forEach((listener) -> {
                try {
                    listener.weatherChanged(sensors, getMonitorCount(), getSensorCount());
                } catch (ConnectException e){
                    // remove client in case of a connection issue
                    monitors.remove(listener);
                    System.err.println("Error - Removing client - " + e);
                } catch (RemoteException e) {
                    System.err.println("Error - " + e);
                }
            });
        }
    }

    
}

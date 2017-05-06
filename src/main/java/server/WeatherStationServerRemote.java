package main.java.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import main.java.exceptions.SensorNotFoundException;
import main.java.models.Reading;
import main.java.models.Sensor;
import main.java.monitor.WeatherListener;

/**
 * Remote Interface that is exposed to the client
 * Contains the methods that can be invoked by Clients remotely
 * 
 * @author dinuksha
 */
public interface WeatherStationServerRemote extends Remote{
    /**
     * 
     * @return the number of sensors connected
     * @throws RemoteException 
     */
    int getSensorCount() throws RemoteException;
    
    /**
     * 
     * @return the number of monitors connected
     * @throws RemoteException 
     */
    int getMonitorCount() throws RemoteException;
    
    /**
     * 
     * @return a particular sensor
     * @throws RemoteException 
     */
    List<Sensor> getSensor() throws RemoteException;
    
    /**
     * Adds a remote monitor to the list
     * @param weatherListener
     * @throws RemoteException 
     */
    void addWeatherMonitor(WeatherListener weatherListener) throws RemoteException;
    
    /**
     * Removes a remote monitor from the list
     * @param weatherListener
     * @throws RemoteException 
     */
    void removeWeatherMonitor(WeatherListener weatherListener) throws RemoteException;
    
    /**
     * Returns the latest readings of all sensors
     * @param weatherListener
     * @throws RemoteException 
     */
    void getLatestReadings(WeatherListener weatherListener) throws RemoteException;
    
    
    /**
     * Retrieves the weather readings at a particular location
     * @param weatherListener
     * @param location
     * @return
     * @throws SensorNotFoundException
     * @throws RemoteException 
     */
    Sensor getLatestReadingsByLocation(WeatherListener weatherListener, String location) throws SensorNotFoundException, RemoteException;

}

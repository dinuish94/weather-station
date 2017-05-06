package main.java.monitor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.java.exceptions.SensorNotFoundException;
import main.java.models.Sensor;
import main.java.server.WeatherStationServerRemote;

/**
 * Weather Monitor program
 * Invokes remote operations of the server
 * Also the implementation for the Remote Interface WeatherListener
 * 
 * @author dinuksha
 */
public class WeatherMonitorController extends UnicastRemoteObject implements WeatherListener{
    
    public WeatherStationServerRemote remoteServer;
    // Handle the Sensor readings and display on the relevant frame
    MonitorManager handler;
    private static MonitorGUI monitoringStation;
    private WeatherMonitorController weatherMonitor;
    
    public WeatherMonitorController(WeatherStationServerRemote sensor) throws RemoteException{
        this.remoteServer = sensor;
        this.handler = new MonitorManager();
    }
    
    /**
     * Indicates that the monitor initiated successfully
     * @throws RemoteException 
     */
    public void report() throws RemoteException{
        System.out.println("Monitor initiated ");
    }

    @Override
    public void weatherChanged(List<Sensor> sensors, int monitorCount, int sensorCount) throws RemoteException {
        
        monitoringStation.setMonitorCount(monitorCount);
        monitoringStation.setSensorCount(sensorCount);
        
        if( null != sensors ){
            this.handler.addMonitor(monitoringStation, sensors);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Server failed to send updates. Please restart the monitor");
        }
    }
    
    @Override
    public void alertMonitor(Sensor sensor) throws RemoteException {
        // Once the monitor recieves an alert, it updates all readings to the latest
        updateAllReadings();
        JOptionPane.showMessageDialog(
                                    monitoringStation,
                                    "Readings in "+sensor.getLocation()+" reached critical levels!",
                                    "CRITICAL LEVEL",
                                    JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public void notifySensorFailure(Sensor sensor) throws RemoteException {
        JOptionPane.showMessageDialog(
                                    monitoringStation,
                                    "Sensor in "+sensor.getLocation()+" is unavailable!",
                                    "CRITICAL LEVEL",
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Updates all readings to the latest
     */
    public void updateAllReadings(){
        try {
            remoteServer.getLatestReadings(weatherMonitor);
        } catch (RemoteException ex) {
            Logger.getLogger(WeatherMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets all the latest readings of a specified location
     * @param location 
     */
    public void getLatestReadingsByLocation(String location){
        try {
            Sensor sensor = remoteServer.getLatestReadingsByLocation(weatherMonitor,location);
            handler.updateMonitor(monitoringStation, sensor);
        } catch (RemoteException | SensorNotFoundException ex) {
            Logger.getLogger(WeatherMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Starts the monitor window
     * @param monitor 
     */
    void initiateMonitorWindow(WeatherMonitorController monitor) throws RemoteException{
        // 
        weatherMonitor = monitor;
        // Initiate a new UI instance of the Monitor
        monitoringStation = new MonitorGUI(monitor);
        monitoringStation.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Removing the monitor from the server on closing the application
        monitoringStation.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                        int result = JOptionPane.showConfirmDialog(
                                monitoringStation,
                                "Are you sure you want to exit the application?",
                                "Exit Application",
                                JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION){
                            try {
                                remoteServer.removeWeatherMonitor(monitor);
                                monitoringStation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            } catch (RemoteException ex) {
                                Logger.getLogger(WeatherMonitorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                }
            });
        
        monitoringStation.setVisible(true);
        monitoringStation.setMonitorCount(remoteServer.getMonitorCount());
        monitoringStation.setSensorCount(remoteServer.getSensorCount());

        // Add sensors to UI
        monitor.handler.addMonitor(monitoringStation, monitor.remoteServer.getSensor());
        
    }
}

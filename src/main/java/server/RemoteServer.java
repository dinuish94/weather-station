package main.java.server;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Server program which acts as the middleman between the Sensors and the Monitors
 * 
 * @author dinuksha
 */
public class RemoteServer {

    // Server port
    private static final int PORT = 9001;
    
    // Remote service
    private static WeatherStationServer remoteServer;
    
    /**
     * Listens to the specified port
     * Registers the server in the RMIRegistry for remote access
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        
        try{
            // Create a remote object and bind to the rmiregistry in order for the clients to look up
            remoteServer = new WeatherStationServer();
            Naming.rebind("rmi://localhost/weather", remoteServer);
        }
        catch(RemoteException | MalformedURLException e){
            System.err.println(e.getMessage());
        }
        
        // Handle clients that connect through sockets
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                SensorHandler sensorHandler = new SensorHandler(listener.accept(),remoteServer);
                sensorHandler.start();
            }
        }
    }
}

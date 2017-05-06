package main.java.sensor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import main.java.models.Reading;

/**
 *
 * Initializes the connection with the server and registers a new sensor
 * @author dinuksha
 */
public class WeatherSensor {
    
    private Temperature temperature;
    private Humidity humidity;
    private Rainfall rainfall;
    private Airpressure airpressure;
    
    private Reading reading;
    private BufferedReader in;
    private PrintWriter out;

    public WeatherSensor() {
        temperature = new Temperature();
        humidity = new Humidity();
        airpressure = new Airpressure();
        rainfall = new Rainfall();
        reading = new Reading();
    }
    
    /**
     * Connects to the server then enters the processing loop.
     * @throws java.io.IOException
     */
    public void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("SUBMITPASSWORD")){
                out.println(getPassword());
            } else if (line.startsWith("SUBMITSENSORLOCATION")){
                out.println(getLocation());
            } else if (line.startsWith("SENSORREGISTERED")){
                sendReading();
            }
        }
    }
    
    /**
     * Prompt for and return the sensor's name.
     * This should be unique and used to authenticate the sensor
     * @return 
     */
    private String getName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter sensor name (Unique) : ");
        String name = sc.nextLine();
        return name;
    }
    
    /**
     * Prompt for and return the password of the sensor.
     * @return 
     */
    private String getPassword(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Password: ");
        String pw = sc.nextLine();
        return pw;
    }
    
    /**
     * Prompt for and return the location of the sensor.
     * @return 
     */
    private String getLocation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter sensor location: ");
        String location = sc.nextLine();
        return location;
    }
    
    /**
     * Retrieves reading every 5 minutes
     * And sends to the server as an object of type Reading
     * @throws IOException 
     */
    private void sendReading() throws IOException{
        for (; ;) {
            try {
                reading.setTemperature(temperature.getReading());
                // Converting double value of humidity to an int
                Double d = humidity.getReading();
                reading.setHumidity(d.intValue());
                reading.setAirpressure(airpressure.getReading());
                reading.setRainfall(rainfall.getReading());
                System.out.println(reading);
                // Serializing reading object and encoding it 
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ObjectOutputStream oos = new ObjectOutputStream( baos )) {
                    oos.writeObject( reading );
                    // Sending the serialized object to the Weather Server
                    out.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                int duration = 300000;
                Thread.sleep(duration);
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            }
        }
    }
    
    /**
     * Runs the sensor
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        WeatherSensor weatherSensor = new WeatherSensor();
        weatherSensor.run();
    }
}

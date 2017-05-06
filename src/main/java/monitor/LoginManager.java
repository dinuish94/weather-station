
package main.java.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manages the authentication to monitor stations
 * Credentials are read from credentials.txt file
 * 
 * @author dinuksha
 */
public class LoginManager {
    
    /**
     * Authenticates a user
     * @param username
     * @param password
     * @return 
     */
    public static boolean authenticateUser(String username, String password){
        // Retrieve file path
        try (BufferedReader br = new BufferedReader(new InputStreamReader(LoginManager.class.getResourceAsStream("credentials.txt")))) {

            String line;

            // Read line by line 
            while ((line = br.readLine()) != null) { 
                String[] data = line.split(":");
                String un = data[0];
                String pw = data[1];
                // Check username and password
                if(un.equals(username) && pw.equals(password)){
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
}

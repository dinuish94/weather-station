# Introduction
This system is a weather monitoring system for mobile base-stations around the country. It uses IOT sensors that can measure temperature, air pressure, humidity and rainfall. Sensors are placed in different locations.
It consists of 3 main components namely, the sensors, a remote server and the monitors. The sensors send updates of the weather readings to the server every 5 mins. The server will store these values and update the monitors periodically every 1 hour with the latest readings at each station.
Monitors will show all the latest weather readings at all locations. Additionally, to receiving periodic updates, monitors are able to query for the latest readings of all the stations or of one location at any time if required. Monitors can view the number of sensors and monitors connected to the server at any given time.
The server will alert the monitors if the weather at any location reaches the critical values specified below.
Temperature - If exceeds 35C or is below 20C Rainfall - If exceeds 20mm
Server will also alert the monitors if any sensor stops responding.

# Overview of the implementation
* The system is implemented in Java, using Java RMI and Sockets.
* The sensors establishes a socket connection with the remote server via TCP/IP ports in order to send updates to the remote server. Socket connections are ideal to establish communication between two processes specially in this scenario where sensor and server may run at two different machines. Readings are serialized and encoded using Base64 and passed through socket communication to the server where they are deserialized and decoded.
* The monitors use Java RMI connections to communicate with the remote server. This enables the monitors to invoke methods in the server remotely. The server and monitors will need to perform complex operations and this can be difficult through a socket connection. Java RMI is highly optimized and serves this purpose well.
* All the readings received by the server from the sensors are written to a text file.

### Running the server

* Run ‘RemoteServer’ class in main.java.server package
java main.java.server.RemoteServer (inside /build/classes)

### Running the sensors

* Run ‘WeatherSensor’ class in main.java.sensor package
java main.java.sensor.WeatherSensor

* username and password can be found in the users.txt file in the same package
* only one can log in with the same username and password at a given time

e.g.: (name) sensorAdmin1 - (pwd) admin123

### Running the monitor

* Run ‘LoginForm’ class in main.java.monitor package
java main.java.monitor.LoginForm

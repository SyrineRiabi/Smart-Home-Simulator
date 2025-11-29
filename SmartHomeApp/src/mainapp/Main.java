/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainapp; // Ensure this matches your actual package structure

import sensors.MotionSensor;
import sensors.TemperatureSensor;
import sensors.DoorSensor;

public class Main {
    public static void main(String[] args) {
        // Create and test the MotionSensor
        MotionSensor motion = new MotionSensor("MS01");
        motion.activate();  // Activates the motion sensor
        System.out.println("Motion Sensor ID: " + motion.getDeviceId());
        motion.detect();  // Detects motion
        motion.deactivate();  // Deactivates the motion sensor
        motion.detect();  // Cannot detect when off

        // Create and test the TemperatureSensor
        TemperatureSensor tempSensor = new TemperatureSensor("TS01");
        tempSensor.activate();
        System.out.println("Temperature Sensor ID: " + tempSensor.getDeviceId());
        tempSensor.setTemperature(22.5);  // Sets the temperature
        tempSensor.detect();  // Detects the temperature
        tempSensor.deactivate();
        tempSensor.detect();

        // Create and test the DoorSensor
        DoorSensor doorSensor = new DoorSensor("DS01");
        doorSensor.activate();
        System.out.println("Door Sensor ID: " + doorSensor.getDeviceId());
        doorSensor.setDoorState(true);  // Door is open
        doorSensor.setDoorState(false);  // Door is closed
        doorSensor.deactivate();
        doorSensor.detect();
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainapp;

import sensors.MotionSensor;
import sensors.TemperatureSensor;
import sensors.DoorSensor;

public class Main {
    public static void main(String[] args) {
        // Test MotionSensor
        MotionSensor motion = new MotionSensor("MS01");
        motion.activate();
        motion.detect();
        motion.deactivate();
        motion.detect();

        // Test TemperatureSensor
        TemperatureSensor tempSensor = new TemperatureSensor("TS01");
        tempSensor.activate();
        tempSensor.setTemperature(22.5);
        tempSensor.deactivate();
        tempSensor.detect();

        // Test DoorSensor
        DoorSensor doorSensor = new DoorSensor("DS01");
        doorSensor.activate();
        doorSensor.setDoorState(true);  // Door open
        doorSensor.setDoorState(false); // Door closed
        doorSensor.deactivate();
        doorSensor.detect();
    }
}

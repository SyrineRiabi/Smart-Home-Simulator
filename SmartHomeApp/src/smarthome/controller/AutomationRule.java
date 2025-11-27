/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;
import smarthome.home.Home;  // Added: Import Nour's Home class for global access
import smarthome.devices.*;
import smarthome.devices.SmartDevice.*;
import java.util.List;

public class AutomationRule {
    private String condition;  // e.g., "motionDetected"
    private Runnable action;   // Lambda for the action (e.g., turn on light)

    // Updated: Constructor can optionally take Home for global access (or pass devices later)
    public AutomationRule(String condition, Runnable action) {
        this.condition = condition;
        this.action = action;
    }

    // Updated: Check condition using global device list from Home (polymorphism for sensor types)
    public boolean checkCondition(Home home) {
        List<SmartDevice> allDevices = home.getAllDevices();  // Nour's global list
        for (SmartDevice device : allDevices) {
            if (device instanceof MotionSensor && condition.equals("motionDetected")) {
                return ((MotionSensor) device).isMotionDetected();  // Assume method exists in MotionSensor
            } else if (device instanceof SmokeSensor && condition.equals("smokeDetected")) {
                return ((SmokeSensor) device).isSmokeDetected();  // Assume method exists in SmokeSensor
            }
            // Add more actions (e.g., adjust thermostat)
        }
        return false;  // No matching sensor or condition
    }

    // Execute action (unchanged, but now works with global devices)
    public void executeAction() {
        action.run();  // Runs the defined action (e.g., turn on a light)
    }
    // Example usage: new AutomationRule("motionDetected", () -> { Light light = (Light) home.findDeviceById("L1"); light.turnOn(); });
}
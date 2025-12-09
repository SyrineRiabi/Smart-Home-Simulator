package smarthome.controller;

import sensors.*;
import smarthome.devices.SmartDevice;
import smarthome.interfaces.Controllable;
import smarthome.home.Home;

public class AutomationRule {

    private final String name;
    private final Sensor sensor;
    private final SmartDevice targetDevice;
    private final String conditionType;
    private final double threshold;
    private final String action;

    public AutomationRule(final String name, final Sensor sensor, final SmartDevice targetDevice,
                          final String conditionType, final double threshold, final String action) {
        this.name = name;
        this.sensor = sensor;
        this.targetDevice = targetDevice;
        this.conditionType = conditionType;
        this.threshold = threshold;
        this.action = action;
    }

    public boolean checkCondition(Home home) {

        if (sensor instanceof MotionSensor motionSensor && conditionType.equalsIgnoreCase("motion")) {
            return motionSensor.isMotionDetected(); 
        }

        if (sensor instanceof DoorSensor doorSensor) {
            if (conditionType.equalsIgnoreCase("doorOpen") && doorSensor.isDoorOpen()) return true;
            if (conditionType.equalsIgnoreCase("doorClosed") && !doorSensor.isDoorOpen()) return true;
        }

        if (sensor instanceof TemperatureSensor temperatureSensor) {
            double temp = temperatureSensor.getTemperature();
            if (conditionType.equalsIgnoreCase("temperatureAbove") && temp > threshold) return true;
            if (conditionType.equalsIgnoreCase("temperatureBelow") && temp < threshold) return true;
        }

        return false;
    }

    public void executeAction() {
        
        if (targetDevice instanceof Controllable controllableDevice) {
            
            // CRITICAL FIX: Cast targetDevice to SmartDevice to safely call getId()
            String deviceId = ((SmartDevice) targetDevice).getId();
            String normalizedAction = action.toLowerCase();
            
            System.out.println("Rule [" + name + "] executing action: " + normalizedAction + " on " + deviceId); 

            switch (normalizedAction) {
                case "turnon":
                    controllableDevice.turnOn();
                    break;
                case "turnoff":
                    controllableDevice.turnOff();
                    break;
                case "toggle":
                    if (controllableDevice.getStatus().contains("ON")) { 
                        controllableDevice.turnOff();
                    } else {
                        controllableDevice.turnOn();
                    }
                    break;
                default:
                    System.out.println("Error: Unknown action '" + action + "' in rule: " + name);
                    break;
            }
        } else {
             System.out.println("Warning: Target device " + targetDevice.getId() + " is not controllable. Skipping action.");
        }
    }

    @Override
    public String toString() {
        return "AutomationRule[" + name + "]";
    }
    
    public SmartDevice getTargetDevice() {
        return targetDevice;
    }
}
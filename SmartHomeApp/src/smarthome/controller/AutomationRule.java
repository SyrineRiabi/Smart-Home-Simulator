package smarthome.controller;

import sensors.*;
import smarthome.devices.*;
import smarthome.home.Home;
import smarthome.interfaces.Controllable; // <-- Added this import!

public class AutomationRule {

    private String name;
    private Sensor sensor;
    private SmartDevice targetDevice;
    private String conditionType; // motion, doorOpen, temperatureAbove…
    private double threshold;     // used only for temperature rules
    private String action;        // turnOn, turnOff, toggle (will be fixed)

    public AutomationRule(String name, Sensor sensor, SmartDevice targetDevice,
                          String conditionType, double threshold, String action) {
        this.name = name;
        this.sensor = sensor;
        this.targetDevice = targetDevice;
        this.conditionType = conditionType;
        this.threshold = threshold;
        this.action = action;
    }

    /**
     * Evaluates whether the rule should execute based on the sensor state.
     */
    // ... (checkCondition method is fine and remains the same)

    /**
     * Executes the linked device action.
     * CRITICAL FIX: We must check if the targetDevice is Controllable before executing actions.
     */
    public void executeAction() {
        if (targetDevice instanceof Controllable) {
            Controllable controllableDevice = (Controllable) targetDevice;
            
            switch (action) {
                case "turnOn":
                    controllableDevice.turnOn();
                    break;
                case "turnOff":
                    controllableDevice.turnOff();
                    break;
                case "toggle":
                    // Fix: Since SmartDevice/Controllable doesn't have a 'toggle()', 
                    // we replace it with a simple check and action.
                    if (controllableDevice.getStatus().contains("OFF")) {
                        controllableDevice.turnOn();
                    } else {
                        controllableDevice.turnOff();
                    }
                    break;
                default:
                    System.out.println("Unknown action in rule: " + name);
            }
        } else {
            System.out.println("Error in Rule: Target device " + targetDevice.getId() + " is not controllable.");
        }
    }

    @Override
    public String toString() {
        return "Rule[" + name + "]";
    }
    
    // ... (rest of the checkCondition method is omitted for brevity)
    public boolean checkCondition(Home home) {
        // ... (The rest of the checkCondition method remains unchanged)
        if (sensor instanceof MotionSensor && conditionType.equals("motion")) {
            return true; 
        }

        if (sensor instanceof DoorSensor) {
            DoorSensor d = (DoorSensor) sensor;

            if (conditionType.equals("doorOpen") && d.isDoorOpen()) return true;
            if (conditionType.equals("doorClosed") && !d.isDoorOpen()) return true;
        }

        if (sensor instanceof TemperatureSensor) {
            TemperatureSensor t = (TemperatureSensor) sensor;
            double temp = t.getTemperature();

            if (conditionType.equals("temperatureAbove") && temp > threshold) return true;
            if (conditionType.equals("temperatureBelow") && temp < threshold) return true;
        }

        return false;
    }
}

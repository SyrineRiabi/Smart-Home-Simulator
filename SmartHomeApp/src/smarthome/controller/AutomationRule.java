/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import sensors.*;
import smarthome.devices.*;
import smarthome.home.Home;

public class AutomationRule {

    private String name;
    private Sensor sensor;
    private SmartDevice targetDevice;
    private String conditionType;     // motion, doorOpen, temperatureAbove…
    private double threshold;         // used only for temperature rules
    private String action;            // turnOn, turnOff, toggle

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
    public boolean checkCondition(Home home) {

        if (sensor instanceof MotionSensor && conditionType.equals("motion")) {
            return true; // motion always triggers
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

    /**
     * Executes the linked device action.
     */
    public void executeAction() {
        switch (action) {
            case "turnOn":
                targetDevice.turnOn();
                break;
            case "turnOff":
                targetDevice.turnOff();
                break;
            case "toggle":
                targetDevice.toggle();
                break;
            default:
                System.out.println("Unknown action in rule: " + name);
        }
    }

    @Override
    public String toString() {
        return "Rule[" + name + "]";
    }
}


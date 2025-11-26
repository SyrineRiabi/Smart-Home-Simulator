/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

/**
 *
 * @author MSI
 */
package smarthome.controller;

import smarthome.home.Home;
import smarthome.devices.*;
import smarthome.sensors.*;
import smarthome.exceptions.DeviceNotFoundException;
import java.util.*;

public class CentralController {
    private Home home;
    private List<AutomationRule> rules = new ArrayList<>();

    public CentralController(Home home) {
        this.home = home;
    }

    // Execute commands (e.g., "turn on all lights")
    public void executeAction(String action) {
        List<SmartDevice> allDevices = home.getAllDevices();  // Assume method in Home
        for (SmartDevice device : allDevices) {
            if (action.equals("turn on all lights") && device instanceof Light) {
                ((Controllable) device).turnOn();  // Polymorphism via interface
            } else if (action.equals("turn off all devices")) {
                ((Controllable) device).turnOff();
            }
            // Add more actions (e.g., adjust thermostat)
        }
    }

    // List all device statuses
    public List<String> listAllStatuses() {
        List<String> statuses = new ArrayList<>();
        for (SmartDevice device : home.getAllDevices()) {
            statuses.add(device.getStatus());  // Polymorphic call
        }
        return statuses;
    }

    // Search device by ID or type
    public SmartDevice findDevice(String id) throws DeviceNotFoundException {
        SmartDevice device = home.findDeviceById(id);  // Assume method in Home
        if (device == null) throw new DeviceNotFoundException("Device not found: " + id);
        return device;
    }

    // Add automation rule
    public void addRule(AutomationRule rule) {
        rules.add(rule);
    }

    // Run automation (check sensors and trigger rules)
    public void runAutomation() {
        List<SmartDevice> sensors = home.getAllDevices().stream()
            .filter(d -> d instanceof MotionSensor || d instanceof SmokeSensor)
            .toList();  // Get sensors
        for (AutomationRule rule : rules) {
            for (SmartDevice sensor : sensors) {
                if (rule.checkCondition(sensor)) {
                    rule.executeAction();
                }
            }
        }
    }

    // Add device to room (integrate with Home)
    public void addDeviceToRoom(String roomName, SmartDevice device) {
        home.addDeviceToRoom(roomName, device);  // Assume method in Home
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import smarthome.home.*;  // Import Nour's home classes (Home, HomeStructure, rooms)
import smarthome.devices.*;
import smarthome.sensors.*;
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.interfaces.EnergyConsumer;  // For energy tracking
import java.util.*;

/**
 * CentralController manages the smart home, including devices, automation, and commands.
 * Integrates with Home for global device access and room management.
 * @author Yosr (Controller & Exceptions Lead)
 */
public class CentralController {
    private Home home;  // Reference to Nour's Home class for global access
    private List<AutomationRule> rules = new ArrayList<>();

    /**
     * Constructor: Initializes the controller with the home structure.
     * @param home The Home instance containing rooms and devices.
     */
    public CentralController(Home home) {
        this.home = home;
    }

    /**
     * Executes a command on devices (e.g., "turn on all lights").
     * Uses polymorphism to handle different device types.
     * @param action The action string (e.g., "turn on all lights").
     */
    public void executeAction(String action) {
        List<SmartDevice> allDevices = home.getAllDevices();  // Nour's global list
        switch (action.toLowerCase()) {
            case "turn on all lights":
                for (SmartDevice device : allDevices) {
                    if (device instanceof Light) {
                        ((Controllable) device).turnOn();
                    }
                }
                break;
            case "turn off all devices":
                for (SmartDevice device : allDevices) {
                    if (device instanceof Controllable) {
                        ((Controllable) device).turnOff();
                    }
                }
                break;
            case "adjust thermostat":
                for (SmartDevice device : allDevices) {
                    if (device instanceof Thermostat) {
                        ((Thermostat) device).setTemperature(22.0);  // Example adjustment
                    }
                }
                break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }

    /**
     * Lists statuses of all devices.
     * @return A list of status strings.
     */
    public List<String> listAllStatuses() {
        List<String> statuses = new ArrayList<>();
        for (SmartDevice device : home.getAllDevices()) {
            statuses.add(device.getStatus());  // Polymorphic call
        }
        return statuses;
    }

    /**
     * Searches for a device by ID across the home.
     * @param id The device ID.
     * @return The SmartDevice if found.
     * @throws DeviceNotFoundException If the device is not found.
     */
    public SmartDevice findDevice(String id) throws DeviceNotFoundException {
        return home.findDeviceById(id);  // Delegates to Home, throws exception
    }

    /**
     * Adds an automation rule.
     * @param rule The AutomationRule to add.
     */
    public void addRule(AutomationRule rule) {
        rules.add(rule);
    }

    /**
     * Runs automation by checking rules against sensors in the home.
     * Integrates with AutomationRule for condition checks.
     */
    public void runAutomation() {
        for (AutomationRule rule : rules) {
            if (rule.checkCondition(home)) {  // Pass home for global sensor access
                rule.executeAction();
            }
        }
    }

    /**
     * Adds a device to a specific room and updates the global list.
     * @param roomName The name of the room (e.g., "Living Room").
     * @param device The device to add.
     */
    public void addDeviceToRoom(String roomName, SmartDevice device) {
        HomeStructure room = null;
        switch (roomName.toLowerCase()) {
            case "living room": room = home.getLivingRoom(); break;
            case "kitchen": room = home.getKitchen(); break;
            case "bedroom": room = home.getBedroom(); break;
            case "bathroom": room = home.getBathroom(); break;
        }
        if (room != null) {
            home.addDeviceToRoom(device, room);  // Nour's method
        } else {
            System.out.println("Room not found: " + roomName);
        }
    }

    /**
     * Calculates total energy usage across all devices.
     * @return The total energy usage (double).
     */
    public double getTotalEnergyUsage() {
        double total = 0.0;
        for (SmartDevice device : home.getAllDevices()) {
            if (device instanceof EnergyConsumer) {
                total += ((EnergyConsumer) device).getEnergyUsage();
            }
        }
        return total;
    }

    /**
     * Parses and executes a custom command (e.g., "turn on L1").
     * @param command The full command string.
     */
    public void parseAndExecuteCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length >= 2) {
            String action = parts[0] + " " + parts[1];  // e.g., "turn on"
            String target = parts[2];  // e.g., "L1"
            try {
                SmartDevice device = findDevice(target);
                if (action.equals("turn on") && device instanceof Controllable) {
                    ((Controllable) device).turnOn();
                } else if (action.equals("turn off") && device instanceof Controllable) {
                    ((Controllable) device).turnOff();
                } else {
                    System.out.println("Invalid command for device: " + target);
                }
            } catch (DeviceNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid command format.");
        }
    }
}
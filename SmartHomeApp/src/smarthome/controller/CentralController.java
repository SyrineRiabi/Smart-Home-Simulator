/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import smarthome.home.*;                     // rooms + Home
import smarthome.devices.*;                  // SmartDevice, Light, Thermostat...
import sensors.*;                            // FIXED → sensors package
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.interfaces.*;               // Controllable + EnergyConsumer
import java.util.*;

/**
 * CentralController manages the smart home, including devices, automation, and commands.
 * Integrates with Home for global device access and room management.
 * @author Yosr
 */
public class CentralController {

    private Home home;                         // Reference to Nour’s global Home structure
    private List<AutomationRule> rules = new ArrayList<>();

    /**
     * Constructor: Initializes the controller with the home structure.
     */
    public CentralController(Home home) {
        this.home = home;
    }

    /**
     * Executes a predefined action (e.g., "turn on all lights").
     */
    public void executeAction(String action) {
        List<SmartDevice> allDevices = home.getAllDevices();

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
                        ((Thermostat) device).setTemperature(22.0);
                    }
                }
                break;

            default:
                System.out.println("Unknown action: " + action);
        }
    }

    /**
     * Lists the status of all devices.
     */
    public List<String> listAllStatuses() {
        List<String> statuses = new ArrayList<>();

        for (SmartDevice device : home.getAllDevices()) {
            statuses.add(device.getStatus());
        }

        return statuses;
    }

    /**
     * Finds a device by ID using Nour’s Home global list.
     */
    public SmartDevice findDevice(String id) throws DeviceNotFoundException {
        return home.findDeviceById(id);
    }

    /**
     * Adds an automation rule.
     */
    public void addRule(AutomationRule rule) {
        rules.add(rule);
    }

    /**
     * Runs all automation rules.
     */
    public void runAutomation() {
        for (AutomationRule rule : rules) {
            if (rule.checkCondition(home)) {
                rule.executeAction();
            }
        }
    }

    /**
     * Adds a new device to a specific room.
     */
    public void addDeviceToRoom(String roomName, SmartDevice device) {

        HomeStructure room = switch (roomName.toLowerCase()) {
            case "living room" -> home.getLivingRoom();
            case "kitchen"     -> home.getKitchen();
            case "bedroom"     -> home.getBedroom();
            case "bathroom"    -> home.getBathroom();
            default -> null;
        };

        if (room != null) {
            home.addDeviceToRoom(device, room);
        } else {
            System.out.println("Room not found: " + roomName);
        }
    }

    /**
     * Calculates all energy usage by devices implementing EnergyConsumer.
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
     * Parses and executes commands like: "turn on L1"
     */
    public void parseAndExecuteCommand(String command) {
        String[] parts = command.split(" ");

        if (parts.length < 3) {
            System.out.println("Invalid command format.");
            return;
        }

        String action = parts[0] + " " + parts[1];   // "turn on"
        String target = parts[2];                    // device ID

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
    }
}

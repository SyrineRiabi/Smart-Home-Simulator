package smarthome.controller;

import smarthome.home.*;               
import smarthome.devices.*;             
import sensors.*;                       
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.interfaces.*;          
import java.util.*;
import java.time.LocalTime;

public class CentralController {

    private final Home home;
    private final List<AutomationRule> rules = new ArrayList<>();

    public CentralController(Home home) {
        this.home = home;
    }

    public void executeAction(String action) {
        List<SmartDevice> allDevices = home.getAllDevices();

        switch (action.toLowerCase()) {
            case "turn on all lights" -> allDevices.stream()
                    .filter(d -> d instanceof Light)
                    .forEach(d -> ((Controllable)d).turnOn());

            case "turn off all devices" -> allDevices.stream()
                    .filter(d -> d instanceof Controllable)
                    .forEach(d -> ((Controllable)d).turnOff());

            case "adjust thermostat" -> allDevices.stream()
                    .filter(d -> d instanceof Thermostat)
                    .forEach(d -> ((Thermostat)d).setTemperature(22));

            default -> System.out.println("Unknown action: " + action);
        }
    }

    public List<String> listAllStatuses() {
        List<String> statuses = new ArrayList<>();
        home.getAllDevices().forEach(d -> statuses.add(d.getStatus()));
        return statuses;
    }

    public SmartDevice findDevice(String id) throws DeviceNotFoundException {
        return home.findDeviceById(id);
    }

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
     * Runs scheduled tasks on all Schedulable devices for the current time.
     */
    public void runScheduledTasks(LocalTime currentTime) {
        home.getAllDevices().stream()
            .filter(d -> d instanceof Schedulable)
            .map(d -> (Schedulable) d)
            .forEach(s -> s.executeScheduledTask(currentTime));
    }


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

    public double getTotalEnergyUsage() {
        // Correctly maps to the fixed getEnergyUsage() in SmartDevice
        return home.getAllDevices().stream()
                .filter(d -> d instanceof EnergyConsumer)
                .mapToDouble(d -> ((EnergyConsumer)d).getEnergyUsage()) 
                .sum();
    }

    public void parseAndExecuteCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid command format. Use: <action> <deviceId>");
            return;
        }

        String action = parts[0].toLowerCase();  
        String deviceId = parts[1];

        try {
            SmartDevice device = findDevice(deviceId);

            switch (action) {
                case "turnon" -> {
                    if (device instanceof Controllable controllableDevice) controllableDevice.turnOn();
                }
                case "turnoff" -> {
                    if (device instanceof Controllable controllableDevice) controllableDevice.turnOff();
                }
                case "toggle" -> {
                    if (device instanceof Controllable controllableDevice) {
                        if (controllableDevice.getStatus().contains("ON")) { 
                            controllableDevice.turnOff();
                        } else {
                            controllableDevice.turnOn();
                        }
                    }
                }
                default -> System.out.println("Unknown action: " + action);
            }

        } catch (DeviceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

package ui;

import smarthome.home.*;
import smarthome.devices.*;
import smarthome.controller.*;
import sensors.*;
import java.util.List;
import java.time.LocalTime;

public class Bridge {
    private Home home;
    private CentralController controller;
    
    public Bridge() {
        System.out.println("=== INITIALIZING SMART HOME BRIDGE ===");
        
        // Create the home and controller
        this.home = new Home();
        this.controller = new CentralController(home);
        
        // Create and add devices to rooms
        initializeDevices();
        
        System.out.println("✓ Bridge initialized with " + home.getAllDevices().size() + " devices");
    }
    
    private void initializeDevices() {
        // Create devices
        Light livingRoomLight = new Light("L1-LR");
        Light kitchenLight = new Light("L2-KI");
        Light bedroomLight = new Light("L3-BR");
        Light bathroomLight = new Light("L4-BA");
        Thermostat thermostat = new Thermostat("T1-LR");
        Refrigerator fridge = new Refrigerator("FR1-KI");
        Alarm bedroomAlarm = new Alarm("A1-BR");
        SmartTV tv = new SmartTV("TV1-LR");
        AirConditioner ac = new AirConditioner("AC1-LR");
        
        // Add devices to rooms
        controller.addDeviceToRoom("living room", livingRoomLight);
        controller.addDeviceToRoom("living room", thermostat);
        controller.addDeviceToRoom("living room", tv);
        controller.addDeviceToRoom("living room", ac);
        controller.addDeviceToRoom("kitchen", kitchenLight);
        controller.addDeviceToRoom("kitchen", fridge);
        controller.addDeviceToRoom("bedroom", bedroomLight);
        controller.addDeviceToRoom("bedroom", bedroomAlarm);
        controller.addDeviceToRoom("bathroom", bathroomLight);
        
        // Create and activate sensors
        MotionSensor motionSensor = new MotionSensor("MS1-LR");
        motionSensor.activate();
        
        TemperatureSensor tempSensor = new TemperatureSensor("TS1-LR");
        tempSensor.setTemperature(22);
        tempSensor.activate();
        
        // Create automation rules
        AutomationRule motionRule = new AutomationRule(
            "Motion_Lights",
            motionSensor,
            livingRoomLight,
            "motion",
            0,
            "turnOn"
        );
        controller.addRule(motionRule);
        
        AutomationRule tempRule = new AutomationRule(
            "Temp_AC",
            tempSensor,
            ac,
            "temperatureAbove",
            25,
            "turnOn"
        );
        controller.addRule(tempRule);
    }
    
    // === Methods for UI to call ===
    
    public List<String> getAllDeviceStatuses() {
        return controller.listAllStatuses();
    }
    
    public void sendCommand(String command) {
        System.out.println("Bridge executing command: " + command);
        controller.parseAndExecuteCommand(command);
    }
    
    public double getTotalEnergyUsage() {
        return controller.getTotalEnergyUsage();
    }
    
    public void runAutomation() {
        System.out.println("Bridge: Running automation rules");
        controller.runAutomation();
    }
    
    public void runScheduledTasks() {
        System.out.println("Bridge: Running scheduled tasks");
        controller.runScheduledTasks(LocalTime.now());
    }
    
    public CentralController getController() {
        return controller;
    }
    
    public Home getHome() {
        return home;
    }
}
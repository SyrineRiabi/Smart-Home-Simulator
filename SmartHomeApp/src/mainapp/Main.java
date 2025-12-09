package mainapp;

import smarthome.devices.*;
import smarthome.home.Home;
import smarthome.controller.CentralController;
import smarthome.controller.AutomationRule; // Corrected package name
import sensors.MotionSensor; 
import sensors.TemperatureSensor; // Added for comprehensive test
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.interfaces.Schedulable;
import smarthome.interfaces.EnergyConsumer; // Added for comprehensive test

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  SMART HOME AUTOMATION SIMULATOR: FINAL DEMO");
        System.out.println("=================================================");

        // 1. Setup the Home and Controller (Requirement 3.2, 3.3)
        Home myHome = new Home();
        CentralController controller = new CentralController(myHome);

        // 2. Create and Populate Devices (Requirement 3.1, 5)
        Light livingRoomLight = new Light("L1-LR");
        Alarm bedsideAlarm = new Alarm("A1-BR");
        Light kitchenLight = new Light("L2-KI");
        Thermostat thermostat = new Thermostat("T1-LR");
        AirConditioner ac = new AirConditioner("AC1-LR");
        Refrigerator fridge = new Refrigerator("FR1-KI");
        
        // Create Sensors
        MotionSensor lrMotionSensor = new MotionSensor("M1-LR"); 
        TemperatureSensor lrTempSensor = new TemperatureSensor("TS1-LR"); 
        
        // Add devices to rooms
        controller.addDeviceToRoom("living room", livingRoomLight);
        controller.addDeviceToRoom("living room", thermostat);
        controller.addDeviceToRoom("living room", ac);
        controller.addDeviceToRoom("bedroom", bedsideAlarm);
        controller.addDeviceToRoom("kitchen", kitchenLight);
        controller.addDeviceToRoom("kitchen", fridge);

        // Add sensors (for rules)
        lrMotionSensor.activate();
        lrTempSensor.activate();

        // ----------------------------------------------------
        // --- TEST 1: AUTOMATION RULE (Motion Sensor -> Light On) (Requirement 3.4) ---
        // ----------------------------------------------------
        
        System.out.println("\n--- TEST 1: Automation (Motion -> Light ON) ---");
        
        // Define the Rule: IF motion is detected THEN turn on L1-LR
        AutomationRule rule1 = new AutomationRule(
            "Motion_Lights_ON", lrMotionSensor, livingRoomLight, "motion", 0, "turnOn" 
        );
        controller.addRule(rule1);

        System.out.println("[Initial] Light Status: " + livingRoomLight.getStatus()); 
        
        // Trigger the sensor state
        lrMotionSensor.detect(); 
        
        // Run Automation
        controller.runAutomation(); 

        // Check Final Status
        System.out.println("[Final] Light Status: " + livingRoomLight.getStatus()); 

        // Reset motion sensor state for next potential trigger
        lrMotionSensor.reset(); 

        // ----------------------------------------------------
        // --- TEST 2: SCHEDULING (Alarm) (Requirement 3.3) ---
        // ----------------------------------------------------

        System.out.println("\n--- TEST 2: Scheduling (Alarm) ---");
        
        // Define the schedule for the alarm to go off at 07:00
        LocalTime alarmTime = LocalTime.of(7, 0);
        
        if (bedsideAlarm instanceof Schedulable schedulableAlarm) {
            schedulableAlarm.scheduleTask(alarmTime, "ON");
            
            System.out.println("Simulating time check at " + alarmTime + "...");
            controller.runScheduledTasks(alarmTime); 
            
            if (bedsideAlarm.getStatus().contains("ON")) {
                 System.out.println("✅ SCHEDULING SUCCESSFUL: Alarm Triggered at " + alarmTime);
            } else {
                 System.out.println("❌ SCHEDULING FAILED: Alarm did not trigger.");
            }
        }
        
        // ----------------------------------------------------
        // --- TEST 3: POLYMORPHISM and ENERGY (Requirement 3.1, 5) ---
        // ----------------------------------------------------

        System.out.println("\n--- TEST 3: Polymorphism & Energy Consumption ---");
        
        // Ensure devices are ON for the highest energy draw
        ac.turnOn();
        kitchenLight.turnOn(); 
        thermostat.turnOn();
        thermostat.setTemperature(18); // Set low temp for high AC consumption

        // Check specific consumption (Thermostat)
        System.out.println("Thermostat Status: " + thermostat.getStatus());
        
        // Use Central Controller to execute a broad action (Polymorphism using Controllable)
        System.out.println("\nExecuting broad action: 'Turn off all devices'");
        controller.executeAction("turn off all devices");

        // Check final status and total energy (Polymorphism using EnergyConsumer)
        double finalUsage = controller.getTotalEnergyUsage();
        
        System.out.println("AC Status (Final): " + ac.getStatus());
        System.out.printf("Total System Energy Usage (W): %.2f%n", finalUsage);

        if (finalUsage < 1.0) { // Should be very low after turning everything off
            System.out.println("✅ POLYMORPHISM & ENERGY SUCCESSFUL: Devices turned off and energy recalculated.");
        } else {
            System.out.println("❌ POLYMORPHISM & ENERGY FAILED: Usage is still high.");
        }

        // ----------------------------------------------------
        // --- TEST 4: EXCEPTION HANDLING (Requirement 4, 5) ---
        // ----------------------------------------------------
        
        System.out.println("\n--- TEST 4: Custom Exception Handling ---");
        
        final String BAD_ID = "XYZ_999";

        try {
            System.out.println("Attempting to find non-existent device with ID: " + BAD_ID);
            controller.findDevice(BAD_ID);
            System.out.println("❌ EXCEPTION FAILED: Device unexpectedly found.");
            
        } catch (DeviceNotFoundException e) {
            // This is the expected successful path
            System.out.println("✅ EXCEPTION SUCCESSFUL: Caught expected custom exception:");
            System.out.println("   --> " + e.getMessage());
        }
        
        // ----------------------------------------------------
        // --- TEST 5: ROOM STRUCTURE (Requirement 3.2) ---
        // ----------------------------------------------------
        
        System.out.println("\n--- TEST 5: Home/Room Structure & Search ---");

        // 1. Verify Structure (Composition)
        System.out.println("Living Room Devices Count: " + myHome.getLivingRoom().getRoomDevices().size());
        System.out.println("Kitchen Devices Count: " + myHome.getKitchen().getRoomDevices().size());
        
        // 2. Verify Global Search
        try {
            SmartDevice foundDevice = myHome.findDeviceById("T1-LR");
            
            if (foundDevice != null && foundDevice.getDeviceType().equals("Thermostat")) {
                System.out.println("✅ STRUCTURE SUCCESSFUL: Found device T1-LR globally.");
            } else {
                System.out.println("❌ STRUCTURE FAILED: Device not found globally.");
            }
        } catch (DeviceNotFoundException e) {
            System.out.println("❌ STRUCTURE FAILED: " + e.getMessage());
        }

        System.out.println("\n=================================================");
        System.out.println("      ALL FUNCTIONAL REQUIREMENTS DEMONSTRATED");
        System.out.println("=================================================");
    }
}
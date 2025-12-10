package smarthomeapp;

import smarthome.controller.*;
import smarthome.devices.*;
import smarthome.home.*;
import sensors.*;

import java.time.LocalTime;

public class SmartHomeApp {

    public static void main(String[] args) {

        // 1️⃣ Create Home
        Home myHome = new Home();

        // 2️⃣ Create Devices
        Light livingLight = new Light("L1");
        Thermostat bedroomThermo = new Thermostat("T1");
        Alarm kitchenAlarm = new Alarm("A1");

        // 3️⃣ Add Devices to Rooms
        CentralController controller = new CentralController(myHome);
        controller.addDeviceToRoom("living room", livingLight);
        controller.addDeviceToRoom("bedroom", bedroomThermo);
        controller.addDeviceToRoom("kitchen", kitchenAlarm);

        // 4️⃣ Create Sensors
        MotionSensor motionSensor = new MotionSensor("M1");
        DoorSensor doorSensor = new DoorSensor("D1");
        TemperatureSensor tempSensor = new TemperatureSensor("TS1");

        // 5️⃣ Create Automation Rules
        AutomationRule motionLightRule = new AutomationRule(
                "Motion triggers living light",
                motionSensor,
                livingLight,
                "motion",
                0,
                "turnon"
        );

        AutomationRule doorThermoRule = new AutomationRule(
                "Door opens -> Thermostat ON",
                doorSensor,
                bedroomThermo,
                "doorOpen",
                0,
                "turnon"
        );

        AutomationRule tempAlarmRule = new AutomationRule(
                "Temp above 25 -> Alarm ON",
                tempSensor,
                kitchenAlarm,
                "temperatureAbove",
                25,
                "turnon"
        );

        // 6️⃣ Add rules to controller
        controller.addRule(motionLightRule);
        controller.addRule(doorThermoRule);
        controller.addRule(tempAlarmRule);

        // 7️⃣ Simulate Sensor events
        System.out.println("\n--- Simulating Motion Sensor ---");
        motionSensor.activate();  // Motion detected
        controller.runAutomation();

        System.out.println("\n--- Simulating Door Sensor ---");
        doorSensor.setDoorState(true); // Door opened
        controller.runAutomation();

        System.out.println("\n--- Simulating Temperature Sensor ---");
        tempSensor.setTemperature(27); // Temperature high
        controller.runAutomation();

        // 8️⃣ Show device statuses
        System.out.println("\n--- Device Statuses ---");
        controller.listAllStatuses().forEach(System.out::println);

        // 9️⃣ Energy consumption
        System.out.println("\nTotal Energy Consumption: " + controller.getTotalEnergyUsage() + " units");

        //  🔟 Test parseAndExecuteCommand
        System.out.println("\n--- Parsing Commands ---");
        controller.parseAndExecuteCommand("toggle L1"); // Should turn OFF living light
        controller.parseAndExecuteCommand("turnoff T1"); // Turn off bedroom thermostat
        controller.parseAndExecuteCommand("turnon A1");  // Turn on kitchen alarm

        System.out.println("\n--- Device Statuses After Commands ---");
        controller.listAllStatuses().forEach(System.out::println);
    }
}

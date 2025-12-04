package mainapp;

import smarthome.devices.Light;
import smarthome.home.Home;
import smarthome.controller.CentralController;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Starting Energy Consumption Integration Test ---");

        // 1. Setup the Home and Controller
        Home myHome = new Home();
        CentralController controller = new CentralController(myHome);

        // 2. Create Devices
        Light kitchenLight = new Light("L1-KITCHEN");
        Light bedroomLight = new Light("L2-BEDROOM");
        
        // --- TEST SETUP ---
        // L1 is OFF (default)
        kitchenLight.setBrightness(100); // Max brightness if turned on
        // L2 is ON
        bedroomLight.turnOn();
        bedroomLight.setBrightness(50); 
        
        // 3. Add Devices to Home (using a room)
        // We use the addDeviceToRoom method defined in the CentralController
        controller.addDeviceToRoom("kitchen", kitchenLight);
        controller.addDeviceToRoom("bedroom", bedroomLight);
        
        // 4. Test 1: Calculate Total Energy (L1 OFF, L2 ON)
        System.out.println("\n*** Test 1: L1 is OFF, L2 is ON ***");
        double energy1 = controller.getTotalEnergyUsage();
        double expected1 = 6.0; // Expected: L1=0.0, L2=50*0.12 = 6.0
        
        System.out.printf("Total Energy Usage: %.2f units\n", energy1);
        System.out.printf("Expected Energy: %.2f units\n", expected1);

        // 5. Test 2: Turn L1 ON and Recalculate
        System.out.println("\n*** Test 2: Turning L1 ON and Recalculating ***");
        controller.parseAndExecuteCommand("turn on L1-KITCHEN"); // This method should exist in Controller
        
        double energy2 = controller.getTotalEnergyUsage();
        double expected2 = 18.0; // Expected: L1=100*0.12=12.0, L2=6.0. Total = 18.0
        
        System.out.printf("Total Energy Usage: %.2f units\n", energy2);
        System.out.printf("Expected Energy: %.2f units\n", expected2);
        
        // 6. Final Check
        if (Math.abs(energy2 - expected2) < 0.01) {
             System.out.println("\n✅ INTEGRATION SUCCESSFUL: Energy calculation is correct!");
        } else {
             System.out.println("\n❌ INTEGRATION FAILED: Check Light.java and CentralController.java!");
        }
    }
}
package test;

import java.io.File;

public class VerifyFXML {
    public static void main(String[] args) {
        System.out.println("=== Verifying FXML File ===");
        
        String[] possibleLocations = {
            "src/ui/views/main.fxml",
            "src\\ui\\views\\main.fxml",
            "SmartHomeApp/src/ui/views/main.fxml",
            "C:/Users/MSI/Documents/NetBeansProjects/Smart-Home-Simulator/SmartHomeApp/src/ui/views/main.fxml"
        };
        
        boolean found = false;
        for (String location : possibleLocations) {
            File file = new File(location);
            if (file.exists()) {
                System.out.println("✓ FOUND: " + location);
                System.out.println("   Absolute path: " + file.getAbsolutePath());
                found = true;
            } else {
                System.out.println("✗ NOT FOUND: " + location);
            }
        }
        
        if (!found) {
            System.out.println("\n=== CREATE THE FILE ===");
            System.out.println("1. Right-click on 'ui.views' package");
            System.out.println("2. New → Empty File");
            System.out.println("3. Name: main.fxml");
            System.out.println("4. Paste the FXML code");
        }
    }
}
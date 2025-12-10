package ui.controllers;

import ui.Bridge;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    // Bridge instance
    private Bridge bridge;
    
    // Initialize method (called automatically by FXMLLoader)
    public void initialize() {
        System.out.println("MainController initialized!");
        
        // Create bridge if not set
        if (bridge == null) {
            bridge = new Bridge();
            System.out.println("Bridge created in controller");
        }
        
        // Start update timer
        startUpdateTimer();
    }
    
    // Setter for bridge (can be called from SmartHomeUI)
    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
        System.out.println("Bridge set via setter");
    }
    
    // Action methods (called from FXML)
    public void onRunAutomation() {
        System.out.println("Run Automation button clicked!");
        if (bridge != null) {
            bridge.runAutomation();
            System.out.println("Automation executed");
        }
    }
    
    public void onRefresh() {
        System.out.println("Refresh button clicked!");
        // You can add refresh logic here
    }
    
    public void onExecuteCommand() {
        System.out.println("Execute Command clicked!");
        // You can add command execution logic here
    }
    
    // Timer for updates
    private void startUpdateTimer() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 1000); // Update every second
    }
    
    private void updateTime() {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("Current time: " + time);
        // In real UI, you'd update a label here
    }
    
    // Getter for bridge
    public Bridge getBridge() {
        return bridge;
    }
}
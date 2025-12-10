package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DashboardController {
    
    // Device states
    private boolean lightOn = false;
    private int temperature = 22;
    private boolean fridgeOn = true;
    private boolean alarmOn = false;
    
    // FXML elements
    @FXML private Label statusLabel;
    @FXML private Label lightLabel;
    @FXML private Label tempLabel;
    @FXML private Label fridgeLabel;
    @FXML private Label alarmLabel;
    @FXML private Label energyReportLabel;
    @FXML private Label footerLabel;
    @FXML private TextField commandField;
    
    // Initialize
    @FXML
    public void initialize() {
        System.out.println("✅ CONTROLLER INITIALIZED!");
        updateDisplay();
    }
    
    private void updateDisplay() {
        // Update labels
        lightLabel.setText(lightOn ? "[ON]" : "[OFF]");
        tempLabel.setText(temperature + "°C");
        fridgeLabel.setText(fridgeOn ? "[ON]" : "[OFF]");
        alarmLabel.setText(alarmOn ? "[ON]" : "[OFF]");
        
        // Update footer
        footerLabel.setText("Smart Home | Active: " + countActiveDevices() + "/4 | Temp: " + temperature + "°C");
        
        System.out.println("Display updated");
    }
    
    private int countActiveDevices() {
        int count = 0;
        if (lightOn) count++;
        if (fridgeOn) count++;
        if (alarmOn) count++;
        return count;
    }
    
    // === BUTTON HANDLERS ===
    
    @FXML
    private void toggleLight() {
        System.out.println("TOGGLING LIGHT");
        lightOn = !lightOn;
        updateDisplay();
        showAlert("Light", "Living Room Light turned " + (lightOn ? "ON" : "OFF"));
    }
    
    @FXML
    private void tempUp() {
        System.out.println("INCREASING TEMP");
        temperature++;
        updateDisplay();
    }
    
    @FXML
    private void tempDown() {
        System.out.println("DECREASING TEMP");
        temperature--;
        updateDisplay();
    }
    
    @FXML
    private void toggleFridge() {
        System.out.println("TOGGLING FRIDGE");
        fridgeOn = !fridgeOn;
        updateDisplay();
        showAlert("Fridge", "Refrigerator turned " + (fridgeOn ? "ON" : "OFF"));
    }
    
    @FXML
    private void toggleAlarm() {
        System.out.println("TOGGLING ALARM");
        alarmOn = !alarmOn;
        updateDisplay();
        showAlert("Alarm", "Bedroom Alarm turned " + (alarmOn ? "ON" : "OFF"));
    }
    
    @FXML
    private void generateEnergyReport() {
        System.out.println("GENERATING ENERGY REPORT");
        
        double lightEnergy = lightOn ? 40 : 0;
        double fridgeEnergy = fridgeOn ? 150 : 0;
        double tempEnergy = Math.abs(temperature - 20) * 10;
        double alarmEnergy = alarmOn ? 5 : 0;
        double total = lightEnergy + fridgeEnergy + tempEnergy + alarmEnergy;
        
        String report = "⚡ ENERGY REPORT ⚡\n\n" +
                       "Living Room Light: " + lightEnergy + "W\n" +
                       "Refrigerator: " + fridgeEnergy + "W\n" +
                       "Thermostat/HVAC: " + tempEnergy + "W\n" +
                       "Alarm: " + alarmEnergy + "W\n" +
                       "-------------------\n" +
                       "TOTAL: " + total + "W\n\n" +
                       (total > 200 ? "⚠️ High energy usage!" : "✅ Energy efficient!");
        
        energyReportLabel.setText(report);
        showAlert("Energy Report", report);
    }
    
    @FXML
    private void executeCommand() {
        String command = commandField.getText().trim().toLowerCase();
        System.out.println("EXECUTING COMMAND: " + command);
        
        if (command.isEmpty()) return;
        
        if (command.equals("toggle l1-lr")) {
            toggleLight();
        } else if (command.equals("toggle fr1-ki")) {
            toggleFridge();
        } else if (command.equals("toggle a1-br")) {
            toggleAlarm();
        } else if (command.equals("temp up") || command.equals("increase temp")) {
            tempUp();
        } else if (command.equals("temp down") || command.equals("decrease temp")) {
            tempDown();
        } else if (command.equals("energy report")) {
            generateEnergyReport();
        } else if (command.equals("all on")) {
            lightOn = true;
            fridgeOn = true;
            alarmOn = true;
            updateDisplay();
            showAlert("All On", "All devices turned ON");
        } else if (command.equals("all off")) {
            lightOn = false;
            fridgeOn = false;
            alarmOn = false;
            updateDisplay();
            showAlert("All Off", "All devices turned OFF");
        } else {
            showAlert("Unknown Command", 
                "Unknown: " + command + "\n\n" +
                "Try these:\n" +
                "- toggle L1-LR\n" +
                "- toggle FR1-KI\n" +
                "- toggle A1-BR\n" +
                "- temp up\n" +
                "- temp down\n" +
                "- energy report\n" +
                "- all on\n" +
                "- all off"
            );
        }
        
        commandField.clear();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
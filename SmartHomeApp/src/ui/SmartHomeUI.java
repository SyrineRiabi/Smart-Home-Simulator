package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.File;
import java.net.URL;

public class SmartHomeUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.out.println("=== SMART HOME UI STARTING ===");
        
        try {
            Parent root = null;
            
            // METHOD 1: Try to load from file system (like SimpleSmartHomeUI does)
            File file = new File("src/ui/views/main.fxml");
            if (file.exists()) {
                System.out.println("✓ Found FXML in file system: " + file.getAbsolutePath());
                URL url = file.toURI().toURL();
                root = FXMLLoader.load(url);
            } 
            // METHOD 2: Try to load as resource (for packaged JAR)
            else {
                System.out.println("⚠️  File not found, trying resource path...");
                URL url = getClass().getResource("/ui/views/main.fxml");
                if (url != null) {
                    System.out.println("✓ Found FXML as resource: " + url);
                    root = FXMLLoader.load(url);
                } else {
                    throw new Exception("Could not find main.fxml anywhere!");
                }
            }
            
            // Create scene
            Scene scene = new Scene(root, 1000, 700);
            primaryStage.setTitle("Smart Home Dashboard");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            System.out.println("=== UI LAUNCHED SUCCESSFULLY! ===");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            
            // Show a working simple dashboard
            showWorkingDashboard(primaryStage, e);
        }
    }
    
    private void showWorkingDashboard(Stage stage, Exception error) {
        System.out.println("\n--- Creating Working Dashboard ---");
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 40; -fx-background-color: linear-gradient(to bottom, #1a2980, #26d0ce);");
        
        // Title
        Label title = new Label("🏠 SMART HOME SIMULATOR");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Status
        Label status = new Label("✅ JavaFX IS WORKING!");
        status.setStyle("-fx-font-size: 18px; -fx-text-fill: #e8f4f8;");
        
        // Room exploration buttons
        VBox roomPanel = new VBox(10);
        roomPanel.setAlignment(Pos.CENTER);
        
        Label roomTitle = new Label("Explore Rooms:");
        roomTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        HBox roomButtons = new HBox(15);
        roomButtons.setAlignment(Pos.CENTER);
        
        String[] rooms = {"Living Room", "Kitchen", "Bedroom", "Bathroom"};
        for (String room : rooms) {
            Button btn = new Button("🚪 " + room);
            btn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: #34495e; -fx-text-fill: white;");
            btn.setOnAction(e -> {
                System.out.println("Entering: " + room);
                showRoomDetails(room);
            });
            roomButtons.getChildren().add(btn);
        }
        
        roomPanel.getChildren().addAll(roomTitle, roomButtons);
        
        // Sensors panel
        VBox sensorPanel = new VBox(10);
        sensorPanel.setAlignment(Pos.CENTER);
        sensorPanel.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 20; -fx-background-radius: 10;");
        
        Label sensorTitle = new Label("📡 Active Sensors:");
        sensorTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        
        String[] sensors = {"Temperature: 22°C", "Motion: No Activity", "Lights: 3 ON", "Security: Armed"};
        for (String sensor : sensors) {
            Label sensorLabel = new Label("• " + sensor);
            sensorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            sensorPanel.getChildren().add(sensorLabel);
        }
        
        // Error info (small)
        Label errorInfo = new Label(
            "Note: " + error.getMessage() + "\n" +
            "But we can still explore the house!"
        );
        errorInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");
        
        // Add everything to main layout
        root.getChildren().addAll(title, status, roomPanel, sensorPanel, errorInfo);
        
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("Smart Home - Working Dashboard");
        stage.show();
        
        System.out.println("=== WORKING DASHBOARD SHOWN ===");
    }
    
    private void showRoomDetails(String roomName) {
        System.out.println("\n=== ENTERING " + roomName.toUpperCase() + " ===");
        
        // Create a new window for room details
        Stage roomStage = new Stage();
        roomStage.setTitle(roomName + " - Smart Home");
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30; -fx-background-color: #2c3e50;");
        
        Label title = new Label("🚪 " + roomName);
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Simulated devices in this room
        VBox devices = new VBox(10);
        devices.setAlignment(Pos.CENTER_LEFT);
        
        String[] roomDevices = {
            "💡 Main Light: OFF",
            "🔌 Smart Outlet: ON", 
            "🌡️ Thermostat: 22°C",
            "🔒 Window Sensor: SECURE"
        };
        
        for (String device : roomDevices) {
            HBox deviceRow = new HBox(10);
            deviceRow.setAlignment(Pos.CENTER_LEFT);
            
            Label deviceLabel = new Label(device);
            deviceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            
            Button toggleBtn = new Button("Toggle");
            toggleBtn.setStyle("-fx-font-size: 12px;");
            toggleBtn.setOnAction(e -> {
                System.out.println("Toggling: " + device);
            });
            
            deviceRow.getChildren().addAll(deviceLabel, toggleBtn);
            devices.getChildren().add(deviceRow);
        }
        
        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setOnAction(e -> roomStage.close());
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        
        root.getChildren().addAll(title, devices, backBtn);
        
        Scene scene = new Scene(root, 400, 400);
        roomStage.setScene(scene);
        roomStage.show();
    }
    
    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        System.out.println("=== STARTING SMART HOME SIMULATOR ===");
        launch(args);
    }
}
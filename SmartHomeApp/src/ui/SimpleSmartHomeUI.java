package ui;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader; // <-- ADD THIS IMPORT
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight; // <-- ADD THIS IMPORT
import javafx.scene.text.FontPosture;

public class SimpleSmartHomeUI {
    public static void main(String[] args) {
        System.out.println("=== SMART HOME SIMULATOR ===");
        App.launch(App.class, args);
    }
    
    public static class App extends Application {
        
        @Override
        public void start(Stage primaryStage) {
            try {
                System.out.println("=== LOADING SMART HOME DASHBOARD ===");
                
                // Try to load FXML dashboard
                File fxmlFile = new File("src/ui/views/dashboard.fxml");
                System.out.println("Checking FXML: " + fxmlFile.getAbsolutePath());
                
                if (fxmlFile.exists()) {
                    // Load from FXML file
                    URL url = fxmlFile.toURI().toURL();
                    System.out.println("✓ Loading FXML dashboard...");
                    Parent root = FXMLLoader.load(url); // <-- NOW FXMLLoader IS IMPORTED
                    
                    Scene scene = new Scene(root, 1200, 800);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Smart Home Dashboard");
                    primaryStage.show();
                    
                    System.out.println("=== FXML DASHBOARD LOADED ===");
                    
                } else {
                    System.out.println("⚠️  No FXML found, creating enhanced dashboard...");
                    createEnhancedDashboard(primaryStage);
                }
                
            } catch (Exception e) {
                System.err.println("ERROR: " + e.getMessage());
                System.out.println("Creating enhanced dashboard as fallback...");
                createEnhancedDashboard(primaryStage);
            }
        }
        
        private void createEnhancedDashboard(Stage stage) {
            System.out.println("=== CREATING ENHANCED DASHBOARD ===");
            
            // Main container with gradient background
            VBox mainContainer = new VBox(20);
            mainContainer.setAlignment(Pos.TOP_CENTER);
            mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);");
            mainContainer.setPadding(new javafx.geometry.Insets(30));
            
            // === HEADER ===
            HBox header = new HBox(20);
            header.setAlignment(Pos.CENTER_LEFT);
            
            Label title = new Label("🏠 SMART HOME SIMULATOR");
            title.setFont(Font.font("Arial", 32));
            title.setTextFill(Color.WHITE);
            
            Label status = new Label("🟢 SYSTEM ONLINE");
            status.setFont(Font.font("Arial", 14));
            status.setTextFill(Color.LIMEGREEN);
            
            header.getChildren().addAll(title, status);
            
            // === CONTROL PANEL ===
            VBox controlPanel = new VBox(15);
            controlPanel.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 20; -fx-background-radius: 10;");
            
            Label controlTitle = new Label("🚀 QUICK CONTROLS");
            controlTitle.setFont(Font.font("Arial", 18));
            controlTitle.setTextFill(Color.WHITE);
            
            HBox controlButtons = new HBox(15);
            controlButtons.setAlignment(Pos.CENTER);
            
            String[] controls = {"Run Automation", "Refresh Status", "Energy Report", "Security Check"};
            for (String control : controls) {
                Button btn = new Button(control);
                btn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: #3498db; -fx-text-fill: white;");
                btn.setOnAction(e -> handleControl(control));
                controlButtons.getChildren().add(btn);
            }
            
            controlPanel.getChildren().addAll(controlTitle, controlButtons);
            
            // === ROOM EXPLORATION ===
            VBox roomsPanel = new VBox(15);
            roomsPanel.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 20; -fx-background-radius: 10;");
            
            Label roomsTitle = new Label("🚪 EXPLORE ROOMS");
            roomsTitle.setFont(Font.font("Arial", 18));
            roomsTitle.setTextFill(Color.WHITE);
            
            TilePane roomGrid = new TilePane(15, 15);
            roomGrid.setPrefColumns(4);
            
            Room[] rooms = {
                new Room("Living Room", "💡", "3 devices", Color.LIGHTBLUE),
                new Room("Kitchen", "👨‍🍳", "4 devices", Color.LIGHTCORAL),
                new Room("Bedroom", "🛏️", "2 devices", Color.LIGHTPINK),
                new Room("Bathroom", "🚿", "2 devices", Color.LIGHTSEAGREEN),
                new Room("Garage", "🚗", "1 device", Color.LIGHTGRAY),
                new Room("Garden", "🌿", "0 devices", Color.LIGHTGREEN),
                new Room("Office", "💼", "3 devices", Color.LIGHTSALMON),
                new Room("Basement", "🔦", "2 devices", Color.LIGHTSLATEGRAY)
            };
            
            for (Room room : rooms) {
                VBox roomCard = createRoomCard(room);
                roomGrid.getChildren().add(roomCard);
            }
            
            roomsPanel.getChildren().addAll(roomsTitle, roomGrid);
            
            // === SENSOR STATUS ===
            VBox sensorsPanel = new VBox(15);
            sensorsPanel.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 20; -fx-background-radius: 10;");
            
            Label sensorsTitle = new Label("📡 ACTIVE SENSORS");
            sensorsTitle.setFont(Font.font("Arial", 18));
            sensorsTitle.setTextFill(Color.WHITE);
            
            GridPane sensorGrid = new GridPane();
            sensorGrid.setHgap(20);
            sensorGrid.setVgap(10);
            
            Sensor[] sensors = {
                new Sensor("Temperature", "22°C", "🌡️", "Normal"),
                new Sensor("Motion", "No Activity", "👣", "Idle"),
                new Sensor("Humidity", "45%", "💧", "Optimal"),
                new Sensor("Light", "350 lux", "💡", "Daylight"),
                new Sensor("Security", "Armed", "🔒", "Active"),
                new Sensor("Energy", "2.3 kW", "⚡", "Low Usage")
            };
            
            for (int i = 0; i < sensors.length; i++) {
                HBox sensorCard = createSensorCard(sensors[i]);
                sensorGrid.add(sensorCard, i % 3, i / 3);
            }
            
            sensorsPanel.getChildren().addAll(sensorsTitle, sensorGrid);
            
            // === COMMAND INPUT ===
            HBox commandPanel = new HBox(10);
            commandPanel.setAlignment(Pos.CENTER);
            
            TextField commandInput = new TextField();
            commandInput.setPromptText("Enter command (e.g., 'turn on living room lights')");
            commandInput.setPrefWidth(400);
            commandInput.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
            
            Button executeBtn = new Button("Execute Command");
            executeBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: #e74c3c; -fx-text-fill: white;");
            executeBtn.setOnAction(e -> executeCommand(commandInput.getText()));
            
            commandPanel.getChildren().addAll(commandInput, executeBtn);
            
            // === FOOTER ===
            Label footer = new Label("Smart Home Simulator v1.0 | Connected to: SimpleSmartHomeUI | Last update: Just now");
            footer.setFont(Font.font("Arial", 12));
            footer.setTextFill(Color.LIGHTGRAY);
            
            // Add all components to main container
            mainContainer.getChildren().addAll(
                header, controlPanel, roomsPanel, sensorsPanel, commandPanel, footer
            );
            
            // Create scene and show stage
            Scene scene = new Scene(mainContainer, 1200, 850);
            stage.setScene(scene);
            stage.setTitle("Smart Home - Enhanced Dashboard");
            stage.show();
            
            System.out.println("=== ENHANCED DASHBOARD CREATED ===");
        }
        
        // Helper class for room data
        private class Room {
            String name;
            String emoji;
            String deviceCount;
            Color color;
            
            Room(String name, String emoji, String deviceCount, Color color) {
                this.name = name;
                this.emoji = emoji;
                this.deviceCount = deviceCount;
                this.color = color;
            }
        }
        
        // Helper class for sensor data
        private class Sensor {
            String name;
            String value;
            String emoji;
            String status;
            
            Sensor(String name, String value, String emoji, String status) {
                this.name = name;
                this.value = value;
                this.emoji = emoji;
                this.status = status;
            }
        }
        
        // Create a room card UI component
        private VBox createRoomCard(Room room) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            card.setStyle(String.format(
                "-fx-background-color: %s; -fx-padding: 20; -fx-background-radius: 10;",
                toHex(room.color)
            ));
            card.setPrefSize(180, 120);
            
            Label emojiLabel = new Label(room.emoji);
            emojiLabel.setFont(Font.font(24));
            
            Label nameLabel = new Label(room.name);
            nameLabel.setFont(Font.font("Arial", 16));
            nameLabel.setTextFill(Color.BLACK);
            
            Label deviceLabel = new Label(room.deviceCount);
            deviceLabel.setFont(Font.font("Arial", 12));
            deviceLabel.setTextFill(Color.DARKGRAY);
            
            Button exploreBtn = new Button("Explore");
            exploreBtn.setStyle("-fx-font-size: 12px;");
            exploreBtn.setOnAction(e -> exploreRoom(room.name));
            
            card.getChildren().addAll(emojiLabel, nameLabel, deviceLabel, exploreBtn);
            return card;
        }
        
        // Create a sensor card UI component
        private HBox createSensorCard(Sensor sensor) {
            HBox card = new HBox(15);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-padding: 15; -fx-background-radius: 8;");
            card.setPrefWidth(250);
            
            Label emojiLabel = new Label(sensor.emoji);
            emojiLabel.setFont(Font.font(20));
            
            VBox sensorInfo = new VBox(5);
            
            Label nameLabel = new Label(sensor.name);
            nameLabel.setFont(Font.font("Arial", 14));
            nameLabel.setTextFill(Color.WHITE);
            
            // FIXED THIS LINE:
            Label valueLabel = new Label(sensor.value);
            valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // <-- FIXED
            valueLabel.setTextFill(Color.LIGHTGREEN);
            
            Label statusLabel = new Label("Status: " + sensor.status);
            statusLabel.setFont(Font.font("Arial", 10));
            statusLabel.setTextFill(Color.LIGHTGRAY);
            
            sensorInfo.getChildren().addAll(nameLabel, valueLabel, statusLabel);
            card.getChildren().addAll(emojiLabel, sensorInfo);
            
            return card;
        }
        
        // Convert Color to hex string
        private String toHex(Color color) {
            return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255)
            );
        }
        
        // === EVENT HANDLERS ===
        
        private void handleControl(String control) {
            System.out.println("Control clicked: " + control);
            switch (control) {
                case "Run Automation":
                    showAlert("Automation", "Running automation rules...");
                    break;
                case "Refresh Status":
                    showAlert("Refresh", "Refreshing device statuses...");
                    break;
                case "Energy Report":
                    showAlert("Energy", "Generating energy consumption report...");
                    break;
                case "Security Check":
                    showAlert("Security", "Performing security check...");
                    break;
            }
        }
        
        private void exploreRoom(String roomName) {
            System.out.println("=== EXPLORING ROOM: " + roomName + " ===");
            
            // Create room details window
            Stage roomStage = new Stage();
            roomStage.setTitle(roomName + " - Details");
            
            VBox roomDetails = new VBox(20);
            roomDetails.setAlignment(Pos.CENTER);
            roomDetails.setStyle("-fx-padding: 30; -fx-background-color: #34495e;");
            
            Label title = new Label("🚪 " + roomName);
            title.setFont(Font.font("Arial", 24));
            title.setTextFill(Color.WHITE);
            
            // Simulated devices in room
            VBox devicesList = new VBox(10);
            devicesList.setAlignment(Pos.CENTER_LEFT);
            
            String[] devices = {
                "💡 Main Light: OFF",
                "🔌 Smart Outlet: ON",
                "🌡️ Thermostat: 22°C",
                "📺 Smart TV: OFF",
                "🔒 Security Camera: ACTIVE"
            };
            
            for (String device : devices) {
                HBox deviceRow = new HBox(15);
                deviceRow.setAlignment(Pos.CENTER_LEFT);
                
                Label deviceLabel = new Label(device);
                deviceLabel.setFont(Font.font("Arial", 16));
                deviceLabel.setTextFill(Color.WHITE);
                
                Button toggleBtn = new Button("Toggle");
                toggleBtn.setStyle("-fx-font-size: 12px;");
                toggleBtn.setOnAction(e -> {
                    System.out.println("Toggling device: " + device);
                    showAlert("Device", "Toggling: " + device.split(":")[0]);
                });
                
                deviceRow.getChildren().addAll(deviceLabel, toggleBtn);
                devicesList.getChildren().add(deviceRow);
            }
            
            Button backBtn = new Button("← Back to Dashboard");
            backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
            backBtn.setOnAction(e -> roomStage.close());
            
            roomDetails.getChildren().addAll(title, devicesList, backBtn);
            
            Scene scene = new Scene(roomDetails, 500, 400);
            roomStage.setScene(scene);
            roomStage.show();
        }
        
        private void executeCommand(String command) {
            if (command == null || command.trim().isEmpty()) {
                showAlert("Error", "Please enter a command");
                return;
            }
            
            System.out.println("Executing command: " + command);
            showAlert("Command Executed", "Processing: " + command + "\nSimulating device response...");
        }
        
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}
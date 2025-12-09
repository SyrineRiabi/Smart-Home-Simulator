package smarthome.devices;

import smarthome.interfaces.Schedulable;
import java.time.LocalTime;

public class Refrigerator extends SmartDevice implements Schedulable {

    private int fridgeTemp;
    private int freezerTemp;

    public Refrigerator(String id) {
        super(id);
        this.fridgeTemp = 4;
        this.freezerTemp = -18;
    }

    public void setFridgeTemp(int temp) {
        this.fridgeTemp = Math.max(0, Math.min(8, temp));
        System.out.println(id + " fridge temp set to " + this.fridgeTemp + "°C");
    }

    public void setFreezerTemp(int temp) {
        this.freezerTemp = Math.max(-25, Math.min(-15, temp));
        System.out.println(id + " freezer temp set to " + this.freezerTemp + "°C");
    }

    @Override
    public String getDeviceType() {
        return "Refrigerator";
    }

    @Override
    public String getInfo() {
        return "Refrigerator{id=" + id + ", fridge=" + fridgeTemp + "°C, freezer=" + freezerTemp + "°C, status=" + getStatus() + "}";
    }

    @Override
    public void activateLowPowerMode() {
        System.out.println("Refrigerator " + id + " is now in low-power mode (economy setting).");
        this.fridgeTemp = 6;
        this.freezerTemp = -16;
    }

    @Override
    public double getCurrentPowerConsumption() {
        // Refrigerator is almost always ON, but usage is calculated based on temperature delta or mode
        double base = 0.15;
        if (fridgeTemp < 4 || freezerTemp < -20) {
            base += 0.1; // Extra cooling load
        }
        return base;
    }
    
    // Schedulable interface implementation
    @Override
    public void scheduleTask(LocalTime time, String action) {
        System.out.println(id + " scheduled " + action + " at " + time);
    }

    @Override
    public boolean cancelTask(String taskId) {
        System.out.println(id + " canceled task: " + taskId);
        return true;
    }

    @Override
    public boolean executeScheduledTask(LocalTime currentTime) {
        // Example schedule execution: activate Low Power Mode at 01:00 AM
        if (currentTime.equals(LocalTime.of(1, 0))) {
            activateLowPowerMode();
            System.out.println(id + " SCHEDULE TRIGGERED: Low Power Mode at " + currentTime);
            return true;
        }
        return false;
    }
}
package smarthome.devices;

import smarthome.interfaces.Schedulable;
import java.time.LocalTime;

public class Oven extends SmartDevice implements Schedulable {

    private int currentTemperature;

    public Oven(String id) {
        super(id);
        this.currentTemperature = 0;
    }

    public void setTemperature(int temp) {
        if (isOn) {
            this.currentTemperature = Math.max(0, Math.min(300, temp));
            System.out.println(id + " temperature set to " + this.currentTemperature + "°C");
        } else {
            System.out.println(id + " cannot change temperature; device is OFF.");
        }
    }

    @Override
    public void turnOff() {
        super.turnOff();
        this.currentTemperature = 0; // Reset temp when turned off
    }

    @Override
    public String getDeviceType() {
        return "Oven";
    }

    @Override
    public String getInfo() {
        return "Oven{id=" + id + ", temp=" + currentTemperature + "°C, status=" + getStatus() + "}";
    }

    @Override
    public void activateLowPowerMode() {
        System.out.println("Oven " + id + " does not support low-power mode.");
    }

    @Override
    public double getCurrentPowerConsumption() {
        if (isOn && currentTemperature > 0) {
            // Consumption depends on temperature
            return 0.5 + (currentTemperature / 100.0);
        }
        return 0.0;
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
        // Example schedule execution: turn off Oven at 05:00 PM
        if (currentTime.equals(LocalTime.of(17, 0))) {
            turnOff();
            System.out.println(id + " SCHEDULE TRIGGERED: Turn Off at " + currentTime);
            return true;
        }
        return false;
    }
}
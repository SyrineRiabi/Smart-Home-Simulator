package smarthome.devices;

import smarthome.interfaces.Schedulable;
import java.time.LocalTime;

public class AirConditioner extends SmartDevice implements Schedulable {

    private int temperature;

    public AirConditioner(String id) {
        super(id);
        this.temperature = 25; 
    }

    public void setTemperature(int temp) {
        if (isOn) {
            this.temperature = Math.max(18, Math.min(30, temp));
            System.out.println(id + " temperature set to " + this.temperature + "°C");
        } else {
            System.out.println(id + " cannot change temperature; device is OFF.");
        }
    }

    @Override
    public String getDeviceType() {
        return "AirConditioner";
    }

    @Override
    public String getInfo() {
        return "AirConditioner{id=" + id + ", temp=" + temperature + "°C, status=" + getStatus() + "}";
    }

    @Override
    public void activateLowPowerMode() {
        System.out.println("AirConditioner " + id + " is now in low-power mode (eco setting).");
        this.temperature = 26; // Set to a standard eco temp
    }

    @Override
    public double getCurrentPowerConsumption() {
        if (isOn) {
            // High consumption when cooling heavily (temp below 22)
            if (temperature < 22) return 1.5;
            // Medium consumption
            return 1.0; 
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
        if (currentTime.equals(LocalTime.of(7, 0))) {
            turnOn();
            System.out.println(id + " SCHEDULE TRIGGERED: Turn On at " + currentTime);
            return true;
        }
        return false;
    }
}

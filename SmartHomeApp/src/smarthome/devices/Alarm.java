package smarthome.devices;

import smarthome.interfaces.Schedulable;
import java.time.LocalTime;

public class Alarm extends SmartDevice implements Schedulable {

    private LocalTime alarmTime;

    public Alarm(String id) {
        super(id);
    }

    @Override
    public String getDeviceType() {
        return "Alarm";
    }

    @Override
    public String getInfo() {
        return "Alarm{id=" + id + ", alarmTime=" + alarmTime + ", status=" + getStatus() + "}";
    }

    @Override
    public void activateLowPowerMode() {
        System.out.println("Alarm " + id + " is now in low-power mode.");
    }

    @Override
    public double getCurrentPowerConsumption() {
        return 0.05; 
    }

    // Schedulable methods
    @Override
    public void scheduleTask(LocalTime time, String action) {
        if ("ON".equalsIgnoreCase(action)) {
            alarmTime = time;
            System.out.println(id + " alarm set for " + time);
        } else {
            System.out.println(id + " unknown action: " + action);
        }
    }

    @Override
    public boolean cancelTask(String taskId) {
        if ("ON".equalsIgnoreCase(taskId) && alarmTime != null) {
            System.out.println(id + " alarm canceled for " + alarmTime);
            alarmTime = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean executeScheduledTask(LocalTime currentTime) {
        if (alarmTime != null && currentTime.equals(alarmTime)) {
            turnOn(); 
            alarmTime = null; 
            System.out.println(id + " ALARM TRIGGERED at " + currentTime);
            return true;
        }
        return false;
    }

    public LocalTime getAlarmTime() {
        return alarmTime;
    }
}
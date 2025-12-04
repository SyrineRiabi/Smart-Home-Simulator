/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    // Controllable methods are inherited: turnOn(), turnOff(), getStatus()

    // EnergyConsumer methods
    @Override
    public void activateLowPowerMode() {
        System.out.println("Alarm " + id + " is now in low-power mode.");
        // No power usage adjustment needed for a simple alarm
    }

    @Override
    public double getCurrentPowerConsumption() {
        return 0.05; // arbitrary small value for standby
    }

    // Schedulable methods
    @Override
    public void scheduleTask(LocalTime time, String action) {
        if ("ON".equalsIgnoreCase(action)) {
            alarmTime = time;
            System.out.println(id + " alarm set for " + time);
        } else if ("OFF".equalsIgnoreCase(action)) {
            System.out.println(id + " cannot schedule OFF (alarm is one-shot)");
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

    public LocalTime getAlarmTime() {
        return alarmTime;
    }
}

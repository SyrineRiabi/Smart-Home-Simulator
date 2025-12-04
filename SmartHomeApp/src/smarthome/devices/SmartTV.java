/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.interfaces.Schedulable;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SmartTV extends SmartDevice implements Schedulable {

    private int volume;
    private final Map<String, LocalTime> schedule; 

    public SmartTV(String id) {
        super(id);
        this.volume = 20;
        this.schedule = new HashMap<>();
    }

    @Override
    public String getDeviceType() {
        return "SmartTV";
    }

    @Override
    public String getInfo() {
        return "SmartTV{id=" + id + ", volume=" + volume + ", status=" + getStatus() + "}";
    }

    // Volume control methods
    public void increase() {
        if (volume < 100) volume += 5;
        System.out.println(id + " volume increased to " + volume);
    }

    public void decrease() {
        if (volume > 0) volume -= 5;
        System.out.println(id + " volume decreased to " + volume);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(volume, 100));
    }

    // EnergyConsumer methods
    @Override
    public void activateLowPowerMode() {
        System.out.println("SmartTV " + id + " is now in low-power mode.");
        volume = Math.max(5, volume / 2); // reduce volume to save energy
    }

    @Override
    public double getCurrentPowerConsumption() {
        return 0.2 + (volume * 0.01); // base + per volume unit
    }

    // Schedulable methods
    @Override
    public void scheduleTask(LocalTime time, String action) {
        schedule.put(action, time);
        System.out.println(id + " scheduled " + action + " at " + time);
    }

    @Override
    public boolean cancelTask(String taskId) {
        if (schedule.containsKey(taskId)) {
            schedule.remove(taskId);
            System.out.println(id + " canceled task: " + taskId);
            return true;
        }
        return false;
    }

    public Map<String, LocalTime> getSchedule() {
        return schedule;
    }
}
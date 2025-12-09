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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(volume, 100));
    }

    @Override
    public void activateLowPowerMode() {
        System.out.println("SmartTV " + id + " is now in low-power mode.");
        volume = Math.max(5, volume / 2); 
    }

    @Override
    public double getCurrentPowerConsumption() {
        return 0.2 + (volume * 0.01); 
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
    
    @Override
    public boolean executeScheduledTask(LocalTime currentTime) {
        for (Map.Entry<String, LocalTime> entry : schedule.entrySet()) {
            if (currentTime.equals(entry.getValue())) {
                String action = entry.getKey();
                System.out.println(id + " SCHEDULE TRIGGERED for action: " + action);
                
                switch (action.toLowerCase()) {
                    case "turnon" -> turnOn();
                    case "turnoff" -> turnOff();
                    case "increasevolume" -> setVolume(getVolume() + 10);
                    default -> System.out.println("Unknown scheduled action for TV: " + action);
                }
                
                schedule.remove(action);
                return true;
            }
        }
        return false;
    }
}
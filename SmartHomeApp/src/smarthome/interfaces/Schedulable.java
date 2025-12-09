package smarthome.interfaces;

import java.time.LocalTime;

public interface Schedulable {
    
    void scheduleTask(LocalTime time, String action);
    
    boolean cancelTask(String taskId);
    
    /**
     * Executes the task if the current time matches the scheduled time.
     */
    boolean executeScheduledTask(LocalTime currentTime);
}
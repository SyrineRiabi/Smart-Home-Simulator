/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package smarthome.interfaces;

import java.time.LocalTime; // You might need to import this utility

public interface Schedulable {
    void scheduleTask(LocalTime time, String action);
    boolean cancelTask(String taskId);
}

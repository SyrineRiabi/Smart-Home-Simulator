/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sensors;

public abstract class Sensor {

    private String id;
    private boolean status; // This will track whether the sensor is on or off

    public Sensor(String id) {
        this.id = id;
        this.status = false; // Sensors are off by default
    }

    // Activate the sensor (turn it on)
    public void activate() {
        status = true;
        System.out.println("Sensor " + getDeviceId() + " activated.");
    }

    // Deactivate the sensor (turn it off)
    public void deactivate() {
        status = false;
        System.out.println("Sensor " + getDeviceId() + " deactivated.");
    }

    // Return the status of the sensor
    public boolean getStatus() {
        return status;
    }

    // Return the ID of the sensor (inherited from SmartDevice)
    public String getDeviceId() {
        return id;
    }

    // Abstract method that must be implemented by subclasses to define detection behavior
    public abstract void detect();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

public abstract class SmartDevice {
    private String deviceId;
    private String name;
    protected boolean isOn;

    public SmartDevice(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
        this.isOn = false;
    }

    public String getDeviceId() { return deviceId; }
    public String getName() { return name; }
    public boolean isDeviceOn() { return isOn; }

    // Mandatory abstract methods
    public abstract void performSelfCheck();
    public abstract double getCurrentPowerConsumption();
}

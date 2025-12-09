package smarthome.devices;

import smarthome.interfaces.Controllable;
import smarthome.interfaces.EnergyConsumer;

public abstract class SmartDevice implements Controllable, EnergyConsumer {
    
    protected String id;
    protected boolean isOn;

    public SmartDevice(String id) {
        this.id = id;
        this.isOn = false;
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println(id + " is now ON");
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println(id + " is now OFF");
    }

    @Override
    public String getStatus() {
        return id + " Status: " + (isOn ? "ON" : "OFF");
    }

    public String getId() {
        return id;
    }

    // --- EnergyConsumer Implementation (CRITICAL FIX) ---
    
    /**
     * Energy usage is 0 if OFF, otherwise, it uses the device's potential consumption.
     */
    @Override
    public double getEnergyUsage() {
        return isOn ? getCurrentPowerConsumption() : 0.0;
    }
    
    // Mandatory abstract methods
    @Override
    public abstract void activateLowPowerMode();

    @Override
    public abstract double getCurrentPowerConsumption();
    
    public abstract String getDeviceType();
    public abstract String getInfo();
}
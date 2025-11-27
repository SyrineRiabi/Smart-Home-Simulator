/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        return isOn ? "Device is ON" : "Device is OFF";
    }

    public String getId() {
        return id;
    }

    // Mandatory abstract methods for subclasses
    public abstract String getDeviceType();
    public abstract String getInfo();

    @Override
    public abstract void activateLowPowerMode();

    @Override
    public abstract double getCurrentPowerConsumption();
}
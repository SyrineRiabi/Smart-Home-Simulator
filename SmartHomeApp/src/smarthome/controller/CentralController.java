/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

/**
 *
 * @author MSI
 */
package controller;

import home.Home;
import devices.SmartDevice;
import exceptions.DeviceNotFoundException;
import java.util.ArrayList;

public class CentralController {

    private Home home;
    private ArrayList<AutomationRule> rules;

    public CentralController(Home home) {
        this.home = home;
        this.rules = new ArrayList<>();
    }

    // Add automation rules
    public void addRule(AutomationRule rule) {
        rules.add(rule);
    }

    // Apply all automation rules
    public void applyRules() {
        for (AutomationRule rule : rules) {
            rule.execute(home);
        }
    }

    // Search a device globally
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        SmartDevice device = home.searchDevice(id);
        if (device == null) throw new DeviceNotFoundException("Device " + id + " not found!");
        return device;
    }

    // Example: Turn all lights on
    public void turnAllLightsOn() {
        home.getAllDevices().forEach(device -> {
            if (device.getClass().getSimpleName().equals("Light")) {
                device.turnOn();
            }
        });
    }

    // Example: Turn all devices off
    public void turnAllDevicesOff() {
        home.getAllDevices().forEach(SmartDevice::turnOff);
    }
}


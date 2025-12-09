package smarthome.home;

import java.util.ArrayList;
import java.util.List;

import smarthome.devices.SmartDevice;

public class Bedroom extends HomeStructure {

    private final List<SmartDevice> devices;

    public Bedroom() {
        super("Bedroom");
        this.devices = new ArrayList<>();
    }

    @Override
    public List<SmartDevice> getRoomDevices() {
        return devices;
    }
}
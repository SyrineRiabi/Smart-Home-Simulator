package smarthome.home;

import java.util.ArrayList;
import java.util.List;

import smarthome.devices.SmartDevice;

public class Bathroom extends HomeStructure {

    private final List<SmartDevice> devices;

    public Bathroom() {
        super("Bathroom");
        this.devices = new ArrayList<>();
    }

    @Override
    public List<SmartDevice> getRoomDevices() {
        return devices;
    }
}
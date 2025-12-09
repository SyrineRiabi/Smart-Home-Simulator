package smarthome.home;

import java.util.ArrayList;
import java.util.List;

import smarthome.devices.SmartDevice;

public class Kitchen extends HomeStructure {

    private final List<SmartDevice> devices;

    public Kitchen() {
        super("Kitchen");
        this.devices = new ArrayList<>();
    }

    @Override
    public List<SmartDevice> getRoomDevices() {
        return devices;
    }
}

package smarthome.home;

import java.util.ArrayList;
import java.util.List;

import smarthome.devices.SmartDevice;

public class LivingRoom extends HomeStructure {

    private List<SmartDevice> devices;

    public LivingRoom() {
        super("Living Room");
        devices = new ArrayList<>();
    }

    @Override
    public List<SmartDevice> getRoomDevices() {
        return devices;
    }
}

package smarthome.home;

import java.util.List;
import smarthome.devices.SmartDevice;

public abstract class HomeStructure {

    private final String name;

    public HomeStructure(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract List<SmartDevice> getRoomDevices();
}


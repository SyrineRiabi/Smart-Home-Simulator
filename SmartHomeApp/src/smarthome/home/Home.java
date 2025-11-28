package smarthome.home;

import java.util.ArrayList;
import java.util.List;

import smarthome.devices.SmartDevice;
import smarthome.exceptions.DeviceNotFoundException;

public class Home {

    private LivingRoom livingRoom;
    private Kitchen kitchen;
    private Bedroom bedroom;
    private Bathroom bathroom;

    // GLOBAL list of all devices
    private List<SmartDevice> allDevices;

    public Home() {
        livingRoom = new LivingRoom();
        kitchen = new Kitchen();
        bedroom = new Bedroom();
        bathroom = new Bathroom();

        allDevices = new ArrayList<>();
    }

    // ---------- Add device to Home AND Room ----------
    public void addDeviceToRoom(SmartDevice device, HomeStructure room) {
        // add to room
        room.getRoomDevices().add(device);

        // add to global list
        allDevices.add(device);
    }

    // ---------- Getters ----------
    public LivingRoom getLivingRoom() { return livingRoom; }
    public Kitchen getKitchen() { return kitchen; }
    public Bedroom getBedroom() { return bedroom; }
    public Bathroom getBathroom() { return bathroom; }

    // ---------- Global search ----------
    public SmartDevice findDeviceById(String id) throws DeviceNotFoundException {
        for (SmartDevice d : allDevices) {
            if (d.getId().equals(id)) return d;
        }
        throw new DeviceNotFoundException("Device " + id + " not found in Home.");
    }

    public List<SmartDevice> getAllDevices() {
        return new ArrayList<>(allDevices);
    }
}

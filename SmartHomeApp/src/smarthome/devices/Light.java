package smarthome.devices;

// Note: SmartDevice already implements Controllable and EnergyConsumer
public class Light extends SmartDevice {

    private int brightness; // 0 to 100

    public Light(String id) {
        super(id);
        this.brightness = 50; // default brightness
    }

    @Override
    public String getDeviceType() {
        return "Light";
    }

    @Override
    public String getInfo() {
        return "Light{id=" + id + ", brightness=" + brightness + ", status=" + getStatus() + "}";
    }

    // --- EnergyConsumer methods ---
    
    @Override
    public void activateLowPowerMode() {
        brightness = 20;
        System.out.println("Light " + id + " is now in low-power mode.");
    }

    @Override
    public double getCurrentPowerConsumption() {
        // The power consumed at the current brightness level (if ON)
        return brightness * 0.12; // arbitrary formula
    }

    /**
     * CRITICAL FIX: The method called by the CentralController.
     * Only consumes power if the device is turned ON (inherited 'isOn' boolean).
     * @return 
     */
    @Override
    public double getEnergyUsage() {
        if (isOn) {
            return getCurrentPowerConsumption();
        }
        return 0.0; // Consumes 0 energy when OFF
    }

    // --- Additional methods ---
    
    public void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(brightness, 100));
    }

    public int getBrightness() {
        return brightness;
    }
}
package smarthome.devices;

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

    // EnergyConsumer methods
    @Override
    public void activateLowPowerMode() {
        brightness = 20;
        System.out.println("Light " + id + " is now in low-power mode.");
    }

    @Override
    public double getCurrentPowerConsumption() {
        return brightness * 0.12; // arbitrary formula
    }
    
    // NOTE: getEnergyUsage() is now inherited from SmartDevice!

    // Additional methods
    public void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(brightness, 100));
    }

    public int getBrightness() {
        return brightness;
    }
}
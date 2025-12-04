package smarthome.devices;

public class Thermostat extends SmartDevice {

    private double temperature; // Changed from int to double for compatibility

    public Thermostat(String id) {
        super(id);
        this.temperature = 22.0; // default temperature (as a double)
    }

    @Override
    public String getDeviceType() {
        return "Thermostat";
    }

    @Override
    public String getInfo() {
        return "Thermostat{id=" + id + ", temperature=" + temperature + ", status=" + getStatus() + "}";
    }
    
    // --- EnergyConsumer Fix (Required by CentralController) ---

    @Override
    public void activateLowPowerMode() {
        System.out.println("Thermostat " + id + " is now in low-power mode.");
        temperature -= 2.0; 
    }

    @Override
    public double getCurrentPowerConsumption() {
        // Example: each degree above 20 consumes 0.5 units/hour
        return isOn ? Math.max(0, (temperature - 20) * 0.5) : 0.0;
    }
    
    // CRITICAL FIX: Required by the CentralController's getTotalEnergyUsage()
    public double getEnergyUsage() {
        return getCurrentPowerConsumption(); 
    }
    
    // --- Thermostat-specific methods ---
    
    // Updated parameter type to double
    public void setTemperature(double temperature) { 
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    // Other methods remain the same (increase, decrease)
    public void increase() {
        temperature++;
        System.out.println(id + " temperature increased to " + temperature);
    }

    public void decrease() {
        temperature--;
        System.out.println(id + " temperature decreased to " + temperature);
    }
}
package smarthome.interfaces;

public interface EnergyConsumer {
    
    double getCurrentPowerConsumption();
    
    void activateLowPowerMode();
    
    // CRITICAL FIX: The CentralController calls this method to get the usage.
    double getEnergyUsage(); 
}
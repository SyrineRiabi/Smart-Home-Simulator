/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

public class Thermostat extends SmartDevice {

    private int temperature;

    public Thermostat(String id) {
        super(id);
        this.temperature = 22; // default temperature
    }

    @Override
    public String getDeviceType() {
        return "Thermostat";
    }

    @Override
    public String getInfo() {
        return "Thermostat{id=" + id + ", temperature=" + temperature + ", status=" + getStatus() + "}";
    }

    // Controllable methods are inherited: turnOn(), turnOff(), getStatus()

    // EnergyConsumer methods
    @Override
    public void activateLowPowerMode() {
        System.out.println("Thermostat " + id + " is now in low-power mode.");
        // For demonstration, reduce temperature by 2 degrees
        temperature -= 2;
    }

    @Override
    public double getCurrentPowerConsumption() {
        // Example: each degree above 20 consumes 0.5 units/hour
        return Math.max(0, (temperature - 20) * 0.5);
    }

    // Thermostat-specific methods
    public void increase() {
        temperature++;
        System.out.println(id + " temperature increased to " + temperature);
    }

    public void decrease() {
        temperature--;
        System.out.println(id + " temperature decreased to " + temperature);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sensors;

public class TemperatureSensor extends Sensor {

    private double temperature;

    public TemperatureSensor(String id) {
        super(id);
    }

    public void setTemperature(double temp) {
        this.temperature = temp;
        detect();
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public void detect() {
        System.out.println("Temperature sensor " + getDeviceId() + " reads " + temperature + "°C");
    }
}

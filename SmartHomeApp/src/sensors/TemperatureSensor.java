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
        if (getStatus()) {
            System.out.println("Temperature sensor " + getDeviceId() + " reads " + temperature + "°C");
        } else {
            System.out.println("Temperature sensor " + getDeviceId() + " is OFF");
        }
    }
}
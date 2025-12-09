package sensors;

public abstract class Sensor {

    private String id;
    private boolean status; 

    public Sensor(String id) {
        this.id = id;
        this.status = false; 
    }

    public void activate() {
        status = true;
        System.out.println("Sensor " + getDeviceId() + " activated.");
    }

    public void deactivate() {
        status = false;
        System.out.println("Sensor " + getDeviceId() + " deactivated.");
    }

    public boolean getStatus() {
        return status;
    }

    public String getDeviceId() {
        return id;
    }

    public abstract void detect();
}

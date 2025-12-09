package sensors;

public class DoorSensor extends Sensor {

    private boolean doorOpen;

    public DoorSensor(String id) {
        super(id);
        this.doorOpen = false;
    }

    public void setDoorState(boolean isOpen) {
        this.doorOpen = isOpen;
        detect();
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    @Override
    public void detect() {
        if (getStatus()) {
            if (doorOpen) {
                System.out.println("Door sensor " + getDeviceId() + ": Door is OPEN");
            } else {
                System.out.println("Door sensor " + getDeviceId() + ": Door is CLOSED");
            }
        } else {
            System.out.println("Door sensor " + getDeviceId() + " is OFF");
        }
    }
}
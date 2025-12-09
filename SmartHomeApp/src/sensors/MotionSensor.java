package sensors;

public class MotionSensor extends Sensor {

    private boolean motionDetected;

    public MotionSensor(String id) {
        super(id); 
        this.motionDetected = false;
    }

    @Override
    public void detect() {
        if (getStatus()) {
            this.motionDetected = true;
            System.out.println("Motion detected by sensor " + getDeviceId());
        } else {
            System.out.println("Motion sensor " + getDeviceId() + " is OFF");
        }
    }

    public boolean isMotionDetected() {
        return this.motionDetected;
    }

    public void reset() {
        this.motionDetected = false;
        System.out.println("Motion sensor " + getDeviceId() + " state reset.");
    }
}
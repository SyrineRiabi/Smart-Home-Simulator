/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


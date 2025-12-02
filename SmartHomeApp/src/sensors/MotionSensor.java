/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sensors;

public class MotionSensor extends Sensor {

    public MotionSensor(String id) {
        super(id); // Calls Sensor constructor
    }

    @Override
    public void detect() {
        if (getStatus()) {
            System.out.println("Motion detected by sensor " + getDeviceId());
        } else {
            System.out.println("Motion sensor " + getDeviceId() + " is OFF");
        }
    }
}


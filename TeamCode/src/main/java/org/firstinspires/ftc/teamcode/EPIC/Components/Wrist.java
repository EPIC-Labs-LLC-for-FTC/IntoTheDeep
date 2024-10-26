package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IWristListener; // Import the new interface
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.WristEventObject; // Import the event object
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

import java.util.ArrayList;
import java.util.List;

public class Wrist extends AComponents {
    private Servo jointR;
    private Servo jointL;
    private WristStates stateWrist;

    // New list to hold wrist listeners
    private List<IWristListener> listeners;

    public Wrist(HardwareMap hardwareMap) {
        jointR = hardwareMap.get(Servo.class, "JR");
        jointL = hardwareMap.get(Servo.class, "JL");
        listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        jointR.setDirection(Servo.Direction.FORWARD);
        jointL.setDirection(Servo.Direction.REVERSE);
        jointR.scaleRange(0, 0.5);
        jointL.scaleRange(0, 0.5);
        jointR.setPosition(0);
        jointL.setPosition(0);
        stateWrist = WristStates.INITIALIZING;

        // Notifies listeners about the initialization
        notifyWristStateChange(new WristEventObject(this, stateWrist));
    }

    // New method to notify listeners of wrist state changes
    private void notifyWristStateChange(WristEventObject event) {
        for (IWristListener listener : listeners) {
            listener.onWristMove(event);
        }
    }

    public void setPos(double position) {
        jointR.setPosition(position);
        jointL.setPosition(position);
        checkPos();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Wrist State", stateWrist.toString());
        telemetry.update();
    }

    public void addWristListener(IWristListener listener) {
        listeners.add(listener);
    }

    public void removeWristListener(IWristListener listener) {
        listeners.remove(listener);
    }

    public void checkPos() {
        if (jointR.getPosition() == 0 && jointL.getPosition() == 0) {
            stateWrist = WristStates.ROTATED_BACKWARDS;
        } else if (jointR.getPosition() == 1.0 && jointL.getPosition() == 1.0) {
            stateWrist = WristStates.ROTATED_FORWARDS;
        } else {
            stateWrist = WristStates.IDLE;
        }

        // Notifies listeners about the state change
        notifyWristStateChange(new WristEventObject(this, stateWrist));
    }
}

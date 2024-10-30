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
        jointR.scaleRange(0, 0.5);
        jointL.scaleRange(0, 0.5);
        setPos(WristStates.INITIALIZING);
    }

    // New method to notify listeners of wrist state changes
    private void notifyWristStateChange(WristEventObject event) {
        for (IWristListener listener : listeners) {
            listener.onWristMove(event);
        }
    }

    public void setPos(WristStates state) {
        double targetPos = state.getPos();
        jointR.setPosition(targetPos);
        jointL.setPosition(targetPos);
        stateWrist = state;
        this.notifyWristStateChange(new WristEventObject(this, stateWrist));
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

    public double getJointRPos () {
        return jointR.getPosition();
    }

    public double getJointLPos () {
        return jointL.getPosition();
    }
}

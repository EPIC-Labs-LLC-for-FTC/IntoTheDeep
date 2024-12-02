package org.firstinspires.ftc.teamcode.EPIC.Components;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IWristListener; // Import the new interface
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.WristEventObject; // Import the event object
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

import java.util.ArrayList;
import java.util.List;

public class Wrist extends AComponents implements IWrist {
    public Servo jointR;
    public Servo jointL;
    public WristStates stateWrist;

    // New list to hold wrist listeners
    private List<IWristListener> listeners;

    public Wrist(HardwareMap hardwareMap) {
        jointR = hardwareMap.get(Servo.class, "JR");
        jointL = hardwareMap.get(Servo.class, "JL");
        jointL.setDirection(Servo.Direction.REVERSE);
        listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        setPos(WristStates.INITIALIZING);

        setPos(0);
    }

    // New method to notify listeners of wrist state changes
    private void notifyWristStateChange(WristEventObject event) {
        for (IWristListener listener : listeners) {
            listener.onWristMove(event);
        }
    }

    @Override
    public void setPos(WristStates state) {
        double targetPos = state.getPos();
        jointR.setPosition(1-targetPos);
        jointL.setPosition(targetPos);
        this.stateWrist = state;
        this.notifyWristStateChange(new WristEventObject(this, this.stateWrist));
    }

    public void setPos(double tPos) {
        jointR.setPosition(1-tPos);
        jointL.setPosition(tPos);
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Wrist State", this.stateWrist.toString());
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

package org.firstinspires.ftc.teamcode.EPIC.Components;

import static org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ClawEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IClawListener;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

import java.util.ArrayList;
import java.util.List;

public class Claw extends AComponents implements IClaw{

    private Servo leftFinger;
    private Servo rightFinger;
    public double reset = 0;
    public double leftMaxPos = 1;
    public double leftMinPos = 0;
    public double rightMaxPos = 1;
    public double rightMinPos = 0;
    private List<IClawListener> listeners;
    public ClawStates stateClaw = null;

    //Declare your servos, motors, sensors, other devices here

    public Claw(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        leftFinger = hardwareMap.get(Servo.class, "LF");
        rightFinger = hardwareMap.get(Servo.class, "RF");
        this.listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        leftFinger.setDirection(Servo.Direction.FORWARD);
        rightFinger.setDirection(Servo.Direction.FORWARD);

        leftFinger.setPosition(0.5);
        rightFinger.setPosition(0.5);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Claw State", stateClaw.toString());
        telemetry.update();
    }

    @Override
    public void move(ClawStates state) {
        double targetPos = state.getClawPos();
        leftFinger.setPosition(targetPos);
        rightFinger.setPosition(1-targetPos);
        stateClaw = state;
        fireClaw(new ClawEventObject(this, stateClaw));
    }

    public double getLeftFingerPosition(){
        return leftFinger.getPosition();
    }

    public double getRightFingerPosition() {
        return rightFinger.getPosition();
    }

    public void addClawListener(IClawListener listener) {
        listeners.add(listener);
    }

    public void removeClawListener(IClawListener listener) {
        listeners.remove(listener);
    }

    private void fireClaw(ClawEventObject event) {
        for (IClawListener listener : listeners) {
            listener.runClaw(event);
        }
    }
}

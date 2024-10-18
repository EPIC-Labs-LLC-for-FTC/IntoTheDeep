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
        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        this.listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        leftFinger.setDirection(Servo.Direction.REVERSE);
        rightFinger.setDirection(Servo.Direction.FORWARD);

        leftFinger.scaleRange(0, 0.5);
        rightFinger.scaleRange(0, 0.5);
        //setPosition(1.0) will open the claws 45 degrees outwards
        leftFinger.setPosition(reset);
        rightFinger.setPosition(reset);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
        stateClaw = CLAW_INITIALIZED;
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Claw State", stateClaw.toString());
        telemetry.update();
    }

    @Override
    public void open(double position) {
        leftFinger.setPosition(leftFinger.getPosition()+position);
        rightFinger.setPosition(rightFinger.getPosition()+position);
        stateClaw = CLAW_OPEN;
        ClawEventObject ceo = new ClawEventObject(this,leftFinger.getPosition(),rightFinger.getPosition());
        fireOpenClaw(ceo);
    }
    @Override
    public void open() {
        double position = 1.0;
        leftFinger.setPosition(position);
        rightFinger.setPosition(position);
        stateClaw = CLAW_OPEN;
        ClawEventObject ceo = new ClawEventObject(this,leftFinger.getPosition(),rightFinger.getPosition());
        fireOpenClaw(ceo);
    }

    @Override
    public void close(double position) {
        leftFinger.setPosition(leftFinger.getPosition()-position);
        rightFinger.setPosition(rightFinger.getPosition()-position);
        ClawEventObject ceo = new ClawEventObject(this,leftFinger.getPosition(),rightFinger.getPosition());
        fireCloseClaw(ceo);
    }



    @Override
    public void close() {
        double position = 0;
        leftFinger.setPosition(position);
        rightFinger.setPosition(position);
        stateClaw = CLAW_CLOSED;
        ClawEventObject ceo = new ClawEventObject(this,leftFinger.getPosition(),rightFinger.getPosition());
        fireCloseClaw(ceo);
    }

    public void moveRelative(double positionR, double positionL) {
        //Negative value for closing, positive for opening
        leftFinger.setPosition(leftFinger.getPosition() + positionL);
        rightFinger.setPosition(rightFinger.getPosition() + positionR);
        stateClaw = null;
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

    private void fireOpenClaw(ClawEventObject event) {
        for (IClawListener listener : listeners) {
            listener.openClaw(event);
        }
    }

    private void fireCloseClaw(ClawEventObject event) {
        for (IClawListener listener : listeners) {
            listener.closeClaw(event);
        }
    }
}

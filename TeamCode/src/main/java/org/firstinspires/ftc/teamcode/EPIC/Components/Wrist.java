package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

public class Wrist extends AComponents implements IWrist{
    //Declare your servos, motors, sensors, other devices here
    private Servo jointR;
    private Servo jointL;
    private final double reset = 0;
    public WristStates stateWrist;

    public Wrist(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        jointR = hardwareMap.get(Servo.class, "jointR");
        jointL = hardwareMap.get(Servo.class, "jointL");
    }

    @Override
    public void initialize() {

        jointR.setDirection(Servo.Direction.FORWARD);
        jointL.setDirection(Servo.Direction.REVERSE);
        //Servos should rotate 180 degrees max
        jointR.scaleRange(0, 0.5);
        jointL.scaleRange(0, 0.5);

        jointR.setPosition(0);
        jointL.setPosition(0);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
        stateWrist = WristStates.INITIALIZED;
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Wrist State",stateWrist.toString());
        telemetry.update();
    }

    //Position should be from 0 to 1
    public void setPos(double position) {
        jointR.setPosition(position);
        jointL.setPosition(position);
        this.checkPos();
    }

    @Override
    public void move(double position) {
        jointR.setPosition(jointR.getPosition() + position);
        jointL.setPosition(jointL.getPosition() + position);
        this.checkPos();
    }

    public void checkPos() {
        if (jointR.getPosition() == 0 && jointL.getPosition() == 0) {
            stateWrist = WristStates.BACKWARDS;
        } else if (jointR.getPosition() == 1.0 && jointL.getPosition() == 1.0) {
            stateWrist = WristStates.FORWARDS;
        } else {
            stateWrist = WristStates.IDLE;
        }
    }
}

package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm{

    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;

    //Declare your servos, motors, sensors, other devices here

    public Servo arm1 = null;
    public Servo arm2 = null;

    public Arm(HardwareMap hardwareMap) {

        //Instantiate your servos, motors, sensors, other devices here

        arm1 = hardwareMap.get(Servo.class,"arm1");
        arm2 = hardwareMap.get(Servo.class,"arm2");
    }
    @Override
    public void initialize() {

        arm2.setDirection(Servo.Direction.REVERSE);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Arm","Object Initialized");
        telemetry.update();
    }

    @Override
    public void setParent(LinearOpMode parent) {
        this.parent = parent;
    }

    @Override
    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void setIsAutonomous(boolean isAutonomous) {
        this.IsAutonomous = isAutonomous;
    }

    @Override
    public void liftUp() {

        arm1.setPosition(arm1.getPosition() +0.1);
        arm2.setPosition(arm2.getPosition() +0.1);
    }

    @Override
    public void putDown() {

        arm1.setPosition(arm1.getPosition() -0.1);
        arm2.setPosition(arm2.getPosition() -0.1);

    }

    @Override
    public void Horizontal() {

        arm1.setPosition(0);
        arm2.setPosition(0);

    }

    @Override
    public void angle() {

        arm1.setPosition(0);
        arm2.setPosition(0);

    }

    @Override
    public void start() {

        arm1.setPosition(0);
        arm2.setPosition(0);

    }

}

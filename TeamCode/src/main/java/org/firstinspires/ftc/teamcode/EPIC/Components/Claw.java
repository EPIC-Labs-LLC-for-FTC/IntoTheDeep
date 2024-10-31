package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw implements IComponents,IClaw{
    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;

    //Declare your servos, motors, sensors, other devices here

    public Servo claw1 = null;
    public Servo claw2 = null;

    public Claw(HardwareMap hardwareMap) {

        //Instantiate your servos, motors, sensors, other devices here

        claw1 = hardwareMap.get(Servo.class,"claw1");
        claw2 = hardwareMap.get(Servo.class,"claw2");
    }
    @Override
    public void initialize() {

        claw2.setDirection(Servo.Direction.REVERSE);

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
    public void open() {

        claw1.setPosition(0);
        claw2.setPosition(0);

    }

    @Override
    public void SemiOpen() {

        claw1.setPosition(0);
        claw2.setPosition(0);

    }

    @Override
    public void close() {

        claw1.setPosition(0);
        claw2.setPosition(0);

    }

    @Override
    public void open1() {

        claw1.setPosition(claw1.getPosition() +0.1);
        claw2.setPosition(claw2.getPosition() +0.1);

    }

    @Override
    public void close1() {

        claw1.setPosition(claw1.getPosition() -0.1);
        claw2.setPosition(claw2.getPosition() -0.1);

    }

}

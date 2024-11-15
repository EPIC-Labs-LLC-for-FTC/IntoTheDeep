package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo arm1 = null;
    public Servo arm2 = null;

    public Arm(HardwareMap hardwareMap) {

        arm1 = hardwareMap.get(Servo.class,"arm1");
        arm2 = hardwareMap.get(Servo.class,"arm2");
    }
    @Override
    public void initialize() {

        arm2.setDirection(Servo.Direction.REVERSE);
        start();

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
    public void liftUp() {

        arm1.setPosition(arm1.getPosition() +0.001);
        arm2.setPosition(arm2.getPosition() +0.001);
    }

    @Override
    public void putDown() {

        arm1.setPosition(arm1.getPosition() -0.001);
        arm2.setPosition(arm2.getPosition() -0.001);

    }

    @Override
    public void Horizontal() {

        arm1.setPosition(0.694);
        arm2.setPosition(0.694);

    }

    @Override
    public void angle() {

        arm1.setPosition(0.264);
        arm2.setPosition(0.264);

    }

    @Override
    public void start() {

        arm1.setPosition(0);
        arm2.setPosition(0);

    }

}
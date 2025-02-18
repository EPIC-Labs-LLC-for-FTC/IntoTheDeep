package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw implements IComponents,IClaw{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo claw;

    public Claw(HardwareMap hardwareMap) {

        claw = hardwareMap.get(Servo.class,"claw");

    }
    @Override
    public void initialize() {

        close();

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
    public void open() {

        claw.setPosition(0.5711);

    }

    @Override
    public void close() {

        claw.setPosition(0.29);

    }

    @Override
    public void open1() {

        claw.setPosition(claw.getPosition() +0.001);

    }

    @Override
    public void close1() {

        claw.setPosition(claw.getPosition() -0.001);

    }
}

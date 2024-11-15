package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist implements IComponents,IWrist{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo wrist = null;

    public Wrist(HardwareMap hardwareMap) {

        wrist = hardwareMap.get(Servo.class,"wrist");

    }

    @Override
    public void initialize() {

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
    public void plus() {

        wrist.setPosition(wrist.getPosition() +0.1);

    }

    @Override
    public void minus() {

        wrist.setPosition(wrist.getPosition() +0.1);


    }

    @Override
    public void vertical1() {

        wrist.setPosition(0);

    }

    @Override
    public void vertical2() {

        wrist.setPosition(0);

    }

    @Override
    public void horizontal() {

        wrist.setPosition(0);

    }
}

package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist implements IComponents,IWrist{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo wrist1;
    public Servo wrist2;

    public Wrist(HardwareMap hardwareMap) {

        wrist1 = hardwareMap.get(Servo.class,"wrist1");
        wrist2 = hardwareMap.get(Servo.class,"wrist2");

    }

    @Override
    public void initialize() {

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
    public void start() {

        wrist1.setPosition(1);
        wrist2.setPosition(0);

    }

    @Override
    public void rest() {

        wrist1.setPosition(0.18);
        wrist2.setPosition(0.5072);

    }

    @Override
    public void wristPick() {

        wrist1.setPosition(0.5044);
        wrist2.setPosition(0.8283);

    }

    @Override
    public void wristAutoPick() {

        wrist1.setPosition(0.1744);
        wrist2.setPosition(0.8283);

    }

    @Override
    public void wristDrop() {

        wrist1.setPosition(0.18);
        wrist2.setPosition(0.5072);

    }

    @Override
    public void specimenPick() {

        wrist1.setPosition(0.1539);
        wrist2.setPosition(0.4811);

    }

    @Override
    public void specimenDrop() {

//        wrist1.setPosition(0.1706);
        wrist1.setPosition(0.8644);
        wrist2.setPosition(0.5189);

    }

    @Override
    public void specimenAutoDrop() {

        wrist1.setPosition(1);
        wrist2.setPosition(1);

    }

    @Override
    public void plus1() {

        wrist1.setPosition(wrist1.getPosition() +0.001);

    }

    @Override
    public void minus1() {

        wrist1.setPosition(wrist1.getPosition() -0.001);

    }

    @Override
    public void plus2() {

        wrist2.setPosition(wrist2.getPosition() +0.001);

    }

    @Override
    public void minus2() {

        wrist2.setPosition(wrist2.getPosition() -0.001);

    }
}

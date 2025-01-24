package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw implements IComponents,IClaw{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo claw1 = null;
    public Servo claw2 = null;

    public Claw(HardwareMap hardwareMap) {

        claw1 = hardwareMap.get(Servo.class,"claw1");
        claw2 = hardwareMap.get(Servo.class,"claw2");

    }
    @Override
    public void initialize() {

        claw2.setDirection(Servo.Direction.REVERSE);
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

        claw1.setPosition(0.629);
        claw2.setPosition(1);

    }

    @Override
    public void close() {

        claw1.setPosition(0.3);
        claw2.setPosition(0.79);

    }

    @Override
    public void open1() {

        claw1.setPosition(claw1.getPosition() +0.001);

    }

    @Override
    public void close1() {

        claw1.setPosition(claw1.getPosition() -0.001);

    }

    @Override
    public void open2() {

        claw2.setPosition(claw2.getPosition() +0.001);

    }

    @Override
    public void close2() {

        claw2.setPosition(claw2.getPosition() -0.001);

    }
}

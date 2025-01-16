package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo armBase1;
    public Servo armBase2;

    public Arm(HardwareMap hardwareMap) {

        armBase1 = hardwareMap.get(Servo.class,"armBase1");
        armBase2 = hardwareMap.get(Servo.class,"armBase2");
    }

    @Override
    public void initialize() {

        armStart();

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
    public void armStart() {

        armBase1.setPosition(0);
        armBase2.setPosition(1);


    }

    @Override
    public void armRest() {

        armBase1.setPosition(0.9017);
        armBase2.setPosition(0.0883);

    }

    @Override
    public void armRest2() {

        armBase1.setPosition(0);
        armBase2.setPosition(0);

    }

    @Override
    public void armPick() {

        armBase1.setPosition(0.955);
        armBase2.setPosition(0.0306);

    }

    @Override
    public void armDrop() {

        armBase1.setPosition(0.1817);
        armBase2.setPosition(0.8089);

    }

    @Override
    public void specimenPick() {

        armBase1.setPosition(0);
        armBase2.setPosition(1);

    }

    @Override
    public void specimenDrop() {

        armBase1.setPosition(0.9344);
        armBase2.setPosition(0.0594);

    }

    @Override
    public void specimenAutoDrop() {

        armBase1.setPosition(0.9344);
        armBase2.setPosition(0.0594);

    }

    @Override
    public void armBaseUp() {

        armBase1.setPosition(armBase1.getPosition() + 0.001);
        armBase2.setPosition(armBase2.getPosition() - 0.001);

    }

    @Override
    public void armBaseDown() {

        armBase1.setPosition(armBase1.getPosition() - 0.001);
        armBase2.setPosition(armBase2.getPosition() + 0.001);

    }
}
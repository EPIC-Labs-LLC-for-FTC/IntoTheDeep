package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm {

    private LinearOpMode parent;
    private Telemetry telemetry;

    public Servo armBase1;
    public Servo armBase2;

    public Arm(HardwareMap hardwareMap) {

        armBase1 = hardwareMap.get(Servo.class, "armBase1");
        armBase2 = hardwareMap.get(Servo.class, "armBase2");
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

        armBase1.setPosition(0.3533);
        armBase2.setPosition(0.635);

    }

    @Override
    public void armRest() {

        armBase1.setPosition(0.9578);
        armBase2.setPosition(0.0306);

    }

    @Override
    public void armPick() {

        armBase1.setPosition(0.9578);
        armBase2.setPosition(0.0306);

    }

    @Override
    public void armDrop() {

        armBase1.setPosition(0.5317);
        armBase2.setPosition(0.4528);

    }

    @Override
    public void specimenPick() {

        armBase1.setPosition(0.45);
        armBase2.setPosition(0.5378);

    }

    @Override
    public void specimenDrop() {

        armBase1.setPosition(0.7567);
        armBase2.setPosition(0.2317);

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
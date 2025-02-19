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

        armBase1.setPosition(0.8156);
        armBase2.setPosition(0.1556);

    }

    @Override
    public void armRest() {

        armBase1.setPosition(0.1544);
        armBase2.setPosition(0.8444);

    }

    @Override
    public void armPick() {

        armBase1.setPosition(0.1544);
        armBase2.setPosition(0.8444);

    }

    @Override
    public void armDrop() {

        armBase1.setPosition(0.6161);
        armBase2.setPosition(0.3672);

    }

    @Override
    public void specimenPick() {

        armBase1.setPosition(0.7372);
        armBase2.setPosition(0.2606867529);

    }

    @Override
    public void specimenDrop() {

        armBase1.setPosition(0.3767);
        armBase2.setPosition(0.6039);

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
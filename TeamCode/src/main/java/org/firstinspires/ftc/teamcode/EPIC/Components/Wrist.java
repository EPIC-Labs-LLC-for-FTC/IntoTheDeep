package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist implements IComponents,IWrist{
    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    //Declare your servos, motors, sensors, other devices here

    public Servo wrist = null;

    public Wrist(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        wrist = hardwareMap.get(Servo.class, "wrist");
    }
    @Override
    public void initialize() {

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
    public void moveUp() {
        wrist.setPosition(wrist.getPosition() + 0.01);
        telemetry.addData("Wrist Pos", wrist.getPosition());
    }
    public void moveDown() {
        wrist.setPosition(wrist.getPosition() - 0.01);
        telemetry.addData("Wrist Pos", wrist.getPosition());
    }
}

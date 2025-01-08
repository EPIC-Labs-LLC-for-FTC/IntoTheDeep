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
    public Servo clawRight = null;
    public Servo clawLeft = null;

    public Claw(HardwareMap hardwareMap) {

        //Instantiate your servos, motors, sensors, other devices here

        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
    }
    @Override
    public void initialize() {

        clawLeft.setDirection(Servo.Direction.REVERSE);
        close();
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

    public void open() {
        clawRight.setPosition(0.7);
        clawLeft.setPosition(0.7);

    }

    public void close() {
        clawRight.setPosition(0.5);
        clawLeft.setPosition(0.5);

    }
}



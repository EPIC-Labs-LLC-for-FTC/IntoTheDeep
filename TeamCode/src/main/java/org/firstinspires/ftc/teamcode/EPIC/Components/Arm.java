package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm extends AComponents implements IArm{

    //Declare your servos, motors, sensors, other devices here

    public Arm(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
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
    public void liftUp(int position) {

    }

    @Override
    public void putDown(int position) {

    }

    @Override
    public void move(int position) {

    }
}

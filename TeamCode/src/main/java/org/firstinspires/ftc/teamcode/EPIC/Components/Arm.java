package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm extends AComponents implements IArm{

    //Declare your servos, motors, sensors, other devices here
    private DcMotorEx armMotorR;
    private DcMotorEx armMotorL;

    public Arm(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        armMotorR = hardwareMap.get(DcMotorEx.class, "armMotorR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "armMotorL");
    }
    @Override
    public void initialize() {
        double reset = 0;
        armMotorR.setPower(reset);
        armMotorL.setPower(reset);

        armMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
            armMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.displayComponentValues();
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

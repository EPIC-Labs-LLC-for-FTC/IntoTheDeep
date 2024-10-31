package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slides implements IComponents, ISlide{

    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;

    //Declare your servos, motors, sensors, other devices here

    public DcMotorEx slide1 = null;
    public DcMotorEx slide2 = null;

    public Slides(HardwareMap hardwareMap) {

        //Instantiate your servos, motors, sensors, other devices here

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");
    }
    @Override
    public void initialize() {

        slide2.setDirection(DcMotorSimple.Direction.REVERSE);

        slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Slide","Object Initialized");
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
    public void start() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }

    @Override
    public void lBar() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }

    @Override
    public void hBar() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }

    @Override
    public void lBucket() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }

    @Override
    public void hBucket() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }

    @Override
    public void hang() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

    }
}

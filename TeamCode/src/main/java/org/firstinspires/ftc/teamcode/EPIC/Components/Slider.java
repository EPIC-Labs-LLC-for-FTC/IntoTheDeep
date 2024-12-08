package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slider implements IComponents, ISlider{

    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    private ElapsedTime runtime = new ElapsedTime();


    public DcMotorEx slideLeft = null;
    public DcMotorEx slideRight = null;
    public Slider (HardwareMap hardwareMap){

        slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
    }
    @Override
    public void initialize() {

        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
    public void up(int position) {


    }

    @Override
    public void down(int position) {

    }

    @Override
    public void move(int position) {
        slideLeft.setTargetPosition(position);
        slideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideRight.setTargetPosition(position);
        slideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideLeft.setPower(0.6);
        slideRight.setPower(0.6);
        parent.sleep(2000);
        slideLeft.setPower(0.2);
        slideRight.setPower(0.2);

    }

    public int getCurrentPosition() {
        return slideRight.getCurrentPosition();
    }

    public void setPower(double power) {
        slideRight.setPower(power);
        slideLeft.setPower(power);
    }
}

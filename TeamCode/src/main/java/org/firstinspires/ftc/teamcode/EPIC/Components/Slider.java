package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.arcrobotics.ftclib.controller.PIDController;
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
    private PIDController controller2;
    public static double p2 = 0.017, i2 = 0, d2 = 0.0001;
    public static double f2 = -0.02;
    public static int target2 = 0;
    private final double tick_in_degrees2 = 537.7/360;

    public Slider (HardwareMap hardwareMap){

        slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
    }
    @Override
    public void initialize() {

        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);
        controller2 = new PIDController(p2, i2, d2);

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

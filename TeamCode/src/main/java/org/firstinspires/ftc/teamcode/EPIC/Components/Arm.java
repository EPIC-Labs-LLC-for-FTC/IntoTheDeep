package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm{

    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    private ElapsedTime runtime = new ElapsedTime();
    //Declare your servos, motors, sensors, other devices here

    private DcMotorEx armRight;
    private DcMotorEx armLeft;
    private PIDController controller;
    public double p1 = 0.013, i1 = 0, d1 = 0.00075;
    public double f1 = -0.2;
    private int target1 = 0;
    private final double tick_in_degrees1 = 2786.2/360;
    public Arm(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
    }
    @Override
    public void initialize() {

        armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new PIDController(p1, i1, d1);

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
    public void liftUp(int position) {

    }

    @Override
    public void putDown(int position) {

    }

    @Override
    public void move(int position) {
        armLeft.setTargetPosition(position);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setTargetPosition(position);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLeft.setPower(0.8);
        armRight.setPower(0.8);
//        while (parent.opModeIsActive() &&
//                ((runtime.seconds() < 3.0) ||
//                armRight.isBusy() || armLeft.isBusy())) {
//
//        }
        parent.sleep(2000);
        armLeft.setPower(0.2);
        armRight.setPower(0.2);
    }

    public int getCurrentPosition() {
       return armRight.getCurrentPosition();
    }

    public void setPower(double power) {
        armRight.setPower(power);
        armLeft.setPower(power);
    }

    public void resetEncoder() {
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}

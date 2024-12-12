package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slides implements IComponents, ISlide{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public DcMotorEx slide1;
    public DcMotorEx slide2;

    public static final double P = 15.0;
    public static final double I = 0.3;
    public static final double D = 2.0;
    public static final double F = 14.0;

    public PIDFController slide1PID, slide2PID;

    public double targetPosition = 0;

    public Slides(HardwareMap hardwareMap) {

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        slide1PID = new PIDFController(P, I, D, F);
        slide2PID = new PIDFController(P, I, D, F);

    }
    @Override
    public void initialize() {

        slide2.setDirection(DcMotorSimple.Direction.REVERSE);

        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
    public void start() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(0);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

//        moveToPosition(0);

    }

    @Override
    public void lBar() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(-1000);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(-1000);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

//        moveToPosition(-2000);

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

//        moveToPosition(0);

    }

    @Override
    public void lBucket() {

        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide1.setTargetPosition(-2300);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide1.setPower(1);

        slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide2.setTargetPosition(-2300);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setPower(1);

//        moveToPosition(0);

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


//        moveToPosition(0);

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

//        moveToPosition(0);

    }

    public void slideControl(double joystick) {

        double power = -joystick;

        if (Math.abs(power) < 0.1) {
            setMotorPower(0, DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            power = clipPower(power);
            setMotorPower(power, DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    public void setMotorPower(double power, DcMotor.ZeroPowerBehavior behavior) {
        slide1.setZeroPowerBehavior(behavior);
        slide2.setZeroPowerBehavior(behavior);
        slide1.setPower(power);
        slide2.setPower(power);
    }

    public double clipPower(double power) {
        return Math.max(-1, Math.min(1, power));
    }

    public void moveToPosition(double position) {
        targetPosition = position;

        double encoderPositionLeft = slide2.getCurrentPosition();
        double encoderPositionRight = slide1.getCurrentPosition();

        double leftPower = slide2PID.calculate(targetPosition, encoderPositionLeft);
        double rightPower = slide1PID.calculate(targetPosition, encoderPositionRight);

        slide2.setPower(leftPower);
        slide1.setPower(rightPower);
    }

    public static class PIDFController {
        private double p, i, d, f;
        private double integral = 0;
        private double previousError = 0;

        public PIDFController(double p, double i, double d, double f) {
            this.p = p;
            this.i = i;
            this.d = d;
            this.f = f;
        }

        public double calculate(double target, double currentPosition) {
            double error = target - currentPosition;
            integral += error;
            double derivative = error - previousError;
            previousError = error;

            return (p * error) + (i * integral) + (d * derivative) + (f * target);
        }
    }
}
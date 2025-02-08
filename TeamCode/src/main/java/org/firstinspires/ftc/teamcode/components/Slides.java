package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Slides implements IComponents, ISlide{

    private LinearOpMode parent;
    private Telemetry telemetry;

    public DcMotorEx slide1;
    public DcMotorEx slide2;

    public PIDController controller;

    public static double p = 0.02, i = 0, d = 0.0001;
    public static double f = 0.3;

    public static int target = 0;

    public final double tick_in_degrees = 537.7/360;

    public Slides(HardwareMap hardwareMap) {

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        controller = new PIDController(p,i,d);

    }
    @Override
    public void initialize() {

        slide2.setDirection(DcMotorSimple.Direction.REVERSE);

        slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
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
    public void moveTo() {
        controller.setPID(p,i,d);

        int slidePosition1 = slide1.getCurrentPosition();

        double pid1 = controller.calculate(slidePosition1, target);

        double f1 = Math.cos(Math.toRadians(target / tick_in_degrees)) * f;

        double power1 = pid1 + f1;

        slide1.setPower(power1);
        slide2.setPower(power1);
    }

    public void slidesUp(){
        target = target + 15;
    }

    public void slidesDown(){
        target = target - 15;
    }

    public void slidesGo(int position){
        target = position;
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
}
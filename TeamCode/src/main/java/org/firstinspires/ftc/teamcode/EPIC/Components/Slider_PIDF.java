package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

public class Slider_PIDF extends AComponents implements ISlider, IPIDF{
    public PIDController sliderController;

    private final double reset = 0;

    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;
    public SliderStates stateSlider;

    public Slider_PIDF(HardwareMap hardwareMap) {
        slideMotorR = hardwareMap.get(DcMotorEx.class, "SMR");
        slideMotorL = hardwareMap.get(DcMotorEx.class, "SML");
    }

    public void initialize(double p, double i, double d) {
        sliderController = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotorR.setPower(reset);
        slideMotorL.setPower(reset);

        slideMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateSlider = SliderStates.RETRACTED;
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Slider State", "INITIALIZED");
        telemetry.update();
    }

    @Override
    public void runPIDF(double p, double i, double d, double f, int target) {
        sliderController.setPID(p, i, d);
        int sliderPos = slideMotorR.getCurrentPosition();
        double pid = sliderController.calculate(sliderPos, target);

        double power = pid + f;

        slideMotorR.setPower(power);
        slideMotorL.setPower(power);

        telemetry.addData("SliderPos: ", sliderPos);
        telemetry.addData("SliderTargetPos: ", target);
        telemetry.addData("Gravity FeedForward: ", f);
        telemetry.update();
    }

    @Override
    public void slide(SliderStates state, double timeOutS) {

    }
}
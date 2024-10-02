package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slider extends AComponents implements ISlider{

    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;
    private final double inchesPerRotationSlider = 1;
    //inchesPerRotationSlider will be changed to reflect actual slider movement after testing.
    public double speed = 1.0;
    public double errorAdjustmentR = 1.0;
    public double errorAdjustmentL = 1.0;

    public Slider(HardwareMap hardwareMap){
        //define devices here
        slideMotorR = hardwareMap.get(DcMotorEx.class, "slideMotorR");
        slideMotorL = hardwareMap.get(DcMotorEx.class, "slideMotorL");
    }

    @Override
    public void initialize() {
        double reset = 0;
        slideMotorR.setPower(reset);
        slideMotorL.setPower(reset);

        slideMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        if (IsAutonomous) {
            slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Slider", "Object Initialized");
        telemetry.update();
    }

    @Override
    public void goUp(double position) {
        int targetPosR;
        int targetPosL;
        double ticksPerInchSlider = 537.7 / inchesPerRotationSlider;

        if (parent.opModeIsActive()) {
            targetPosR = slideMotorR.getCurrentPosition() + (int) (ticksPerInchSlider * position);
            targetPosL = slideMotorL.getCurrentPosition() + (int) (ticksPerInchSlider * position);

            slideMotorR.setTargetPosition(targetPosR);
            slideMotorL.setTargetPosition(targetPosL);

            slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            slideMotorR.setPower(speed * errorAdjustmentR);
            slideMotorL.setPower(speed * errorAdjustmentL);

            while (parent.opModeIsActive() &&
                    (slideMotorR.isBusy() || slideMotorL.isBusy())) {
                telemetry.addData("Running up to", "sliderR: %7.3d  sliderL: %7.3d", targetPosR, targetPosL);
                telemetry.addData("Progress", "sliderR: %7.3d  sliderL: %7.3d",
                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
                telemetry.update();
            }
        }

        slideMotorR.setPower(0);
        slideMotorL.setPower(0);

        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void goDown(double position) {
        int targetPosR;
        int targetPosL;
        double ticksPerInchSlider = 537.7 / inchesPerRotationSlider;

        if (parent.opModeIsActive()) {
            targetPosR = slideMotorR.getCurrentPosition() + (int) (ticksPerInchSlider * -position);
            targetPosL = slideMotorL.getCurrentPosition() + (int) (ticksPerInchSlider * -position);

            slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            slideMotorR.setPower(speed * errorAdjustmentR);
            slideMotorL.setPower(speed * errorAdjustmentL);

            while (parent.opModeIsActive() &&
                    (slideMotorR.isBusy() || slideMotorL.isBusy())) {
                telemetry.addData("Running down to", "sliderR: %7.3d  sliderL: %.3d", targetPosR, targetPosL);
                telemetry.addData("Progress", "sliderR: %.3d  sliderL: %.3d",
                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
                telemetry.update();
            }
        }

        slideMotorR.setPower(0);
        slideMotorL.setPower(0);

        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}

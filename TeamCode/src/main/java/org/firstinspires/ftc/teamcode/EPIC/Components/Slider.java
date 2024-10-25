package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

public class Slider extends AComponents implements ISlider{

    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;
    public double speed = 1.0;
    public double errorAdjustmentR = 1.0;
    public double errorAdjustmentL = 1.0;
    public SliderStates stateSlider;

    public Slider(HardwareMap hardwareMap){
        //define devices here
        slideMotorR = hardwareMap.get(DcMotorEx.class, "SMR");
        slideMotorL = hardwareMap.get(DcMotorEx.class, "SML");

    }

    @Override
    public void initialize() {

        double reset = 0;
        slideMotorR.setPower(reset);
        slideMotorL.setPower(reset);

        slideMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (IsAutonomous) {

        }

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateSlider = SliderStates.RETRACTED;
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Slider State", "INITIALIZED");
        telemetry.update();
    }

    @Override
    public void slide(SliderStates state) {
        //A negative position should make the slider move down. Positive makes it move upwards.
        double position = state.getStateHeight() - stateSlider.getStateHeight();
        int targetPosR;
        int targetPosL;
        double inchesPerRotationSlider = 537.7;
        // inchesPerRotationSlider is a placeholder until we test the inches moved per full rotation
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
//                telemetry.addData("Slider running to", "sliderR: %1$7.3d  sliderL: %2$7.3d", targetPosR, targetPosL);
//                telemetry.addData("Slider progress", "sliderR: %1$7.3d  sliderL: %2$7.3d",
//                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
                telemetry.update();
            }
        }

        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideMotorR.setPower(0);
        slideMotorL.setPower(0);

        stateSlider = state;

        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void slide(double position) {
        //A negative position should make the slider move down. Positive makes it move upwards.
        int targetPosR;
        int targetPosL;
        double inchesPerRotationSlider = 537.7;
        // inchesPerRotationSlider is a placeholder until we test the inches moved per full rotation
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
                telemetry.addData("Slider running to", "sliderR: %1$7.3d  sliderL: %2$7.3d", targetPosR, targetPosL);
                telemetry.addData("Slider progress", "sliderR: %1$7.3d  sliderL: %2$7.3d",
                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
//                telemetry.update();
            }
        }

        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideMotorR.setPower(0);
        slideMotorL.setPower(0);

        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}

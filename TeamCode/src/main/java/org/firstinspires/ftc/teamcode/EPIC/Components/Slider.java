package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ISliderListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.SliderEventObject;

import java.util.ArrayList;
import java.util.List;

public class Slider extends AComponents implements ISlider {

    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;
    public double speed = 1.0;
    public double errorAdjustmentR = 1.0;
    public double errorAdjustmentL = 1.0;
    public SliderStates stateSlider;
    private ElapsedTime runtime = new ElapsedTime();
    private double holdPower = 0.2;

    // List to store slider listeners
    private List<ISliderListener> listeners = new ArrayList<>();

    public Slider(HardwareMap hardwareMap) {
        // Define devices here
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

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateSlider = SliderStates.RETRACTED;
        this.displayComponentValues();
    }

    public void setPower(double power){
        slideMotorL.setPower(power);
        slideMotorR.setPower(power);
    }
    @Override
    public void displayComponentValues() {
        telemetry.addData("Slider State", "INITIALIZED");
        telemetry.update();
    }

    // Method to add slider listener
    public void addSliderListener(ISliderListener listener) {
        listeners.add(listener);
    }

    // Method to remove slider listener
    public void removeSliderListener(ISliderListener listener) {
        listeners.remove(listener);
    }

    // Notify all listeners of a slider state change
    private void fireSliderEvent(SliderStates newState) {
        SliderEventObject event = new SliderEventObject(this, newState);
        for (ISliderListener listener : listeners) {
            listener.onSliderMove(event);
        }
    }

    @Override
    public void slide(SliderStates state, double timeOutS) {
        double position = state.getStateHeight() - stateSlider.getStateHeight();
        int targetPosR;
        int targetPosL;
        double inchesPerRotationSlider = 537.7;
        double ticksPerInchSlider = 537.7 / inchesPerRotationSlider;

        if (parent.opModeIsActive()) {
            targetPosR = slideMotorR.getCurrentPosition() + (int) (ticksPerInchSlider * position);
            targetPosL = slideMotorL.getCurrentPosition() + (int) (ticksPerInchSlider * position);

            slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            slideMotorR.setTargetPosition(targetPosR);
            slideMotorL.setTargetPosition(targetPosL);

            slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            slideMotorR.setPower(speed * errorAdjustmentR);
            slideMotorL.setPower(speed * errorAdjustmentL);
            runtime.reset();
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < 2.0) &&
                    (slideMotorR.isBusy() || slideMotorL.isBusy())) {
//                telemetry.addData("Slider running to", "sliderR: %1$7.3d  sliderL: %2$7.3d", targetPosR, targetPosL);
//                telemetry.addData("Slider progress", "sliderR: %1$7.3d  sliderL: %2$7.3d",
//                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
                telemetry.update();
            }
        }
        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideMotorR.setPower(holdPower);
        slideMotorL.setPower(holdPower);
    }

    public int getLeftPosition(){
        return slideMotorL.getCurrentPosition();
    }

    public int getRightPosition(){
        return slideMotorR.getCurrentPosition();
    }
    public void slide(double position) {
        //A negative position should make the slider move down. Positive makes it move upwards.
        int targetPosR;
        int targetPosL;
        //double inchesPerRotationSlider = 537.7;
        // inchesPerRotationSlider is a placeholder until we test the inches moved per full rotation
        //double ticksPerInchSlider = 537.7 / inchesPerRotationSlider;
        if (parent.opModeIsActive()) {
            //targetPosR = slideMotorR.getCurrentPosition() + (int) (ticksPerInchSlider * position);
            //targetPosL = slideMotorL.getCurrentPosition() + (int) (ticksPerInchSlider * position);

            slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            slideMotorR.setTargetPosition((int)position);
            slideMotorL.setTargetPosition((int)position);

            slideMotorR.setPower(speed * errorAdjustmentR);
            slideMotorL.setPower(speed * errorAdjustmentL);
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < 2.0) &&
                    (slideMotorR.isBusy() || slideMotorL.isBusy())) {
                //telemetry.addData("Slider running to", "sliderR: %1$7.3d  sliderL: %2$7.3d", targetPosR, targetPosL);
                //telemetry.addData("Slider progress", "sliderR: %1$7.3d  sliderL: %2$7.3d",
                  //      slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
//                telemetry.update();
                }
            }

            slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            slideMotorR.setPower(holdPower);
            slideMotorL.setPower(holdPower);
        }
}


package org.firstinspires.ftc.teamcode.EPIC.Components;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ISliderListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.SliderEventObject;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

import java.util.ArrayList;
import java.util.List;

public class Slider_PIDF extends AComponents implements ISlider, IPIDF{
    public PIDController sliderController;

    private final double reset = 0;
    private int sliderPos;
    public int targetPos;

    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;
    public SliderStates stateSlider;

    private List<ISliderListener> listeners;

    public Slider_PIDF(HardwareMap hardwareMap) {
        slideMotorR = hardwareMap.get(DcMotorEx.class, "SMR");
        slideMotorL = hardwareMap.get(DcMotorEx.class, "SML");

        listeners = new ArrayList<>();
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

    public void addSliderListener(ISliderListener listener) {
        listeners.add(listener);
    }

    // Method to remove slider listener
    public void removeSliderListener(ISliderListener listener) {
        listeners.remove(listener);
    }

    // Notify all listeners of a slider state change
    private void fireSliderEvent(SliderStates newState) {
        SliderEventObject seo = new SliderEventObject(this, newState);
        for (ISliderListener listener : listeners) {
            listener.onSliderMove(seo);
        }
    }

    @Override
    public void runPIDF(double p, double i, double d, double f) {
        sliderController.setPID(p, i, d);
        sliderPos = slideMotorR.getCurrentPosition();
        double pid = sliderController.calculate(sliderPos, targetPos);

        double power = (pid/2) + f;

        slideMotorR.setPower(power);
        slideMotorL.setPower(power);

        telemetry.addData("SliderPos: ", sliderPos);
        telemetry.addData("SliderTargetPos: ", targetPos);
        telemetry.update();
    }

    @Override
    public void slide(SliderStates state) {
        this.targetPos = (int) state.getStateHeight();
        this.stateSlider = state;
        this.fireSliderEvent(stateSlider);
    }

    public Action slide(SliderStates state, boolean isAction) {
        Slider_PIDF slide = this;
        Action action = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                slide.targetPos = (int) state.getStateHeight();
                slide.stateSlider = state;
                slide.fireSliderEvent(stateSlider);
                return false;
            }
        };
        return action;
    }
}

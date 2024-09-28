package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slider implements IComponents, ISlider{
    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;

    public Slider(HardwareMap hardwareMap){
        //define devices here
    }

    @Override
    public void initialize() {

    }

    @Override
    public void displayComponentValues() {

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
    public void goUp(double position) {

    }

    @Override
    public void goDown(double position) {

    }
}

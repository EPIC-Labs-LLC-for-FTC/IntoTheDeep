package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class AComponents {
    protected boolean IsAutonomous = false;
    public LinearOpMode parent;
    public Telemetry telemetry;

    public void initialize() {

    }

    public void displayComponentValues() {
        telemetry.update();
    }

    //Do not override these methods in the component classes
    public void setParent(LinearOpMode parent) {
        this.parent = parent;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void setIsAutonomous(boolean isAutonomous) {
        this.IsAutonomous = isAutonomous;
    }
}

package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class AComponents {
    protected boolean IsAutonomous = false;
    protected LinearOpMode parent;
    protected Telemetry telemetry;

    public void initialize() {

    }

    public void displayComponentValues() {
        telemetry.update();
    }

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

package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface IComponents {
    void initialize();

    void displayComponentValues();

    void setParent(LinearOpMode parent);

    void setTelemetry (Telemetry telemetry);

    void setIsAutonomous(boolean isAutonomous);
}

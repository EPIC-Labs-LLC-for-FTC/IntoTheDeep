package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface IComponents {
    void initialize();

    void setParent(LinearOpMode parent);

    void setTelemetry (Telemetry telemetry);
}

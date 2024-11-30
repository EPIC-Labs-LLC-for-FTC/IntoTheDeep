package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@TeleOp(name = "Wrist Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {
    public static double targetPosR = 0;
    public static double targetPosL = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);

        waitForStart();

        while (opModeIsActive()) {
            wrist.jointR.setPosition(targetPosR);
            wrist.jointL.setPosition(targetPosL);
        }
    }
}

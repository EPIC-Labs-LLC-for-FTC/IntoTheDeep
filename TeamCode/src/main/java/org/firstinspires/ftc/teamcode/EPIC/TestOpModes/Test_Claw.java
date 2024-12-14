package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;

public class Test_Claw extends LinearOpMode {
    public static double posR = 0;
    public static double posL = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(telemetry);
        claw.initialize();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                claw.rightFinger.setPosition(posR);
                claw.leftFinger.setPosition(posL);
            }
        }
    }
}

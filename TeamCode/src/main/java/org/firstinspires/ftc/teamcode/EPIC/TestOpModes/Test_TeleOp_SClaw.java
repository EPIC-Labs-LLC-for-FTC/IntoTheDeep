package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.SpecimenClaw;

@TeleOp(name = "SClaw_Tester")
public class Test_TeleOp_SClaw extends LinearOpMode {
    public double rPos = 0;
    public double lPos = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        SpecimenClaw sClaw = new SpecimenClaw(hardwareMap);
        sClaw.setParent(this);
        sClaw.setTelemetry(telemetry);
        sClaw.initialize();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                sClaw.rightSpecFinger.setPosition(rPos);
                sClaw.leftSpecFinger.setPosition(lPos);
                sleep(60);
            } else if (gamepad1.x) {
                rPos += 0.1;
            } else if (gamepad1.y) {
                rPos -= 0.1;
            } else if (gamepad1.a) {
                lPos += 0.1;
            } else if (gamepad1.b) {
                lPos -= 0.1;
            }
        }
    }
}

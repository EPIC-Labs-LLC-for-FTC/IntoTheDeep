package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@TeleOp(name = "Wrist_Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                wrist.setPos(0);
            } else if (gamepad1.b) {
                wrist.setPos(0.5);
            } else if (gamepad1.x) {
                wrist.setPos(1);
            }
        }
    }
}

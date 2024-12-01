package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@TeleOp(name = "Wrist_Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {
    public static double targetPosR = 0;
    public static double targetPosL = 0;

    Thread wristing = new Thread() {
        public void run() {
            while (opModeIsActive()) {
                if (gamepad1.a) {
                    targetPosR += 0.1;
                } else if (gamepad1.b) {
                    targetPosR -= 0.1;
                } else if (gamepad1.x) {
                    targetPosL += 0.1;
                } else if (gamepad1.y) {
                    targetPosL -= 0.1;
                }
            }
        }
    };

    @Override
    public void runOpMode() throws InterruptedException {
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);

        waitForStart();

        wristing.start();

        wrist.setPos(0);

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                wrist.jointR.setPosition(targetPosR);
                wrist.jointL.setPosition(targetPosL);
            }
            telemetry.addData("JointR", targetPosR);
            telemetry.addData("JointL", targetPosL);
            telemetry.update();
        }
    }
}

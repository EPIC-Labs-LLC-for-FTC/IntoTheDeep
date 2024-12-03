package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@TeleOp(name = "Wrist_Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {

    public double targetPosR = 0;
    public double targetPosL = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        int sleepval = 1000;
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);
        wrist.initialize();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                wrist.setPos(0);
                sleep(sleepval);
            } else if (gamepad1.b) {
                wrist.setPos(0.5);
                sleep(sleepval);
            } else if (gamepad1.x) {
                wrist.setPos(1);
                sleep(sleepval);
            }

            //wrist.setPos(targetPosL);
            //sleep(500);


            telemetry.addData("JointR", wrist.jointR.getPosition());
            telemetry.addData("JointL", wrist.jointL.getPosition());
            telemetry.update();

        }
    }
}

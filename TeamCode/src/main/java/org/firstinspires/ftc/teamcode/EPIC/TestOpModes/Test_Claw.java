package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

@TeleOp(name = "Test_Claw")
@Config
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
            telemetry.addData("RF", claw.rightFinger.getPosition());
            telemetry.addData("LF", claw.leftFinger.getPosition());
            telemetry.update();
        }
    }
}

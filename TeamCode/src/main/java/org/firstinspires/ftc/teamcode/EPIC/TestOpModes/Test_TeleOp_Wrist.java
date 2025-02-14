package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

@Config
@TeleOp(name = "Wrist_Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {

    public static double wristPos = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        int sleepval = 1000;
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);
        wrist.initialize();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("JointL", wrist.jointL.getPosition());
            telemetry.update();
        }
    }
}

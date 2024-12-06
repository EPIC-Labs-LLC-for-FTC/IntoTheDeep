package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

@TeleOp
@Config
public class Test_TeleOp_WristAndArm extends LinearOpMode {
    public static double ap = 0.02, ai = 0, ad = 0.0015, af = 0.08;
    public static double atarget;
    public static double wristPos;
    @Override
    public void runOpMode() throws InterruptedException {
        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        arm.setParent(this);
        wrist.setParent(this);
        arm.setTelemetry(telemetry);
        wrist.setTelemetry(telemetry);
        arm.initialize(ap, ai, ad);
        wrist.initialize();

        Thread pidf = new Thread() {
            @Override
            public void run() {
                while (opModeIsActive()) {
                    arm.runPIDF(ap, ai, ad, af);
                }
            }
        };

        waitForStart();
        pidf.start();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                wrist.setPos(wristPos);
            } else if (gamepad1.left_bumper) {
                arm.move(ArmStates.LOWERED);
            } else if (gamepad1.right_bumper) {
                arm.move(ArmStates.READY_TO_DEPOSIT);
            } else if (gamepad1.dpad_up) {
                arm.move(ArmStates.DEPOSITING_SPECIMEN);
            } else if (gamepad1.b) {
                wrist.setPos(WristStates.DEPOSITING_SPECIMEN);
            }
        }
    }
}

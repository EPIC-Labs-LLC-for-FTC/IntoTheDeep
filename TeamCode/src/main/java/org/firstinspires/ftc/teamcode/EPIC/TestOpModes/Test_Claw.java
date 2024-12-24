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
    public static double wristPos = 0;
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static int atarget;
    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(telemetry);
        claw.initialize();
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);
        wrist.initialize();
        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(telemetry);
        arm.initialize(ap, ai, ad);

        Thread pidf = new Thread() {
            public void run() {
                while(opModeIsActive()) {
                    arm.targetPos = atarget;
                    arm.runPIDF(ap, ai, ad, af);
                }
            }
        };

        waitForStart();

        pidf.start();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                claw.move(ClawStates.OPEN);
            } else if (gamepad1.b) {
                claw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT); 
            } else if (gamepad1.x) {
                wrist.setPos(wristPos);
            }
        }
    }
}

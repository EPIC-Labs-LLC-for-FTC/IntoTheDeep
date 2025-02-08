package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.oldRRcode.SparkFunOTOSDrive;

@Config
@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive
        Robot odyssey = new Robot(this, "Red", true);
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(8.25, -63.85,
                Math.toRadians(90)));
        Pose2d initialPos = new Pose2d(8.25, -63.85, Math.toRadians(90));
        drive.setPoseEstimate(initialPos);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {
            idle();
        }

        Thread coord = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    telemetry.addData("X", drive.pose.position.x);
                    telemetry.addData("Y", drive.pose.position.y);
                    telemetry.addData("Heading (Deg)", Math.toDegrees(drive.pose.heading.toDouble()));
                    telemetry.update();
                }
            }
        };

        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                    odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                }
            }
        };

        TrajectoryActionBuilder tab = drive.actionBuilder(initialPos)

                .lineToY(-40.15)
                //.strafeToConstantHeading(new Vector2d(18, -40.15))

                .splineToConstantHeading(new Vector2d(45, -9), Math.toRadians(90));

        Action tsc1 = tab.build();

//

        waitForStart();
        coord.start();
        //pidf.start();
        Actions.runBlocking(tsc1);
    }
}

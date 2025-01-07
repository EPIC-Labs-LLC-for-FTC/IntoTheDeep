package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.AutonActions.FailoverAction;
import org.firstinspires.ftc.teamcode.EPIC.AutonStates.AutonPose;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

@Config
@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {

    public static double ap = 0.02, ai = 0, ad = 0.0015, af = 0.08;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Red", true);
        SparkFunOTOSDrive drive= new SparkFunOTOSDrive(hardwareMap,new Pose2d(8.25, -63.85,
                Math.toRadians(90)));
        Pose2d initialPos= new Pose2d(8.25, -63.85, Math.toRadians(90));
        drive.setPoseEstimate(initialPos);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

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

                .strafeTo(new Vector2d(8.25, -40.15))
                .strafeTo(new Vector2d(18, -40.15))

                .splineToConstantHeading(new Vector2d(45, -9), Math.toRadians(90));

        Action tsc1 = tab.build();

        waitForStart();
        coord.start();
        pidf.start();
        Actions.runBlocking(tsc1);
        sleep(10000);

        while (opModeIsActive()) {

        }
    }
}

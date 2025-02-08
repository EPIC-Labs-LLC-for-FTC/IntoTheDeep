package org.firstinspires.ftc.teamcode.deprecatedcode.rrtuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.deprecatedcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.deprecatedcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.deprecatedcode.TankDrive;

public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(SparkFunOTOSDrive.class)) {
            SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
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

            waitForStart();
            coord.start();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(22.5, 22.5), Math.PI / 2)
                            .splineTo(new Vector2d(0, 45), Math.PI)
                            .build());
        } else if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}

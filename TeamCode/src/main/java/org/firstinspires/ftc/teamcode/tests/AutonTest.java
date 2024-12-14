package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RRsetup.MecanumDrive;
import org.firstinspires.ftc.teamcode.RRsetup.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.RRsetup.TankDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous
public final class AutonTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

//red right
//        Pose2d beginPose = new Pose2d(14.5, -65, 90);

        //red left
        Pose2d beginPose = new Pose2d(9, -63, Math.toRadians(90));

            SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
//                            .lineToY(-27)
//                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .lineToY(-37.4)
                            .strafeTo(new Vector2d(10,-37.4))
                            .splineToConstantHeading(new Vector2d(42,-9),Math.toRadians(90))
                            .lineToY(-52)
                            .splineTo(new Vector2d(51,-9),Math.toRadians(90))
                            .lineToY(-52)
                            .splineTo(new Vector2d(61,-9),Math.toRadians(90))
                            .lineToY(-52)
                            .build());


            //waitForStart();

            }

}
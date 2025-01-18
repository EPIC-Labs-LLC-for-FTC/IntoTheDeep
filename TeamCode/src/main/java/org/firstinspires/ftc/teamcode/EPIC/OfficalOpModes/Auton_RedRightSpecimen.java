package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

import com.acmerobotics.roadrunner.Pose2d;

@Autonomous(name = "Auton_RedRightSpec")
public class Auton_RedRightSpecimen extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive

        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(8.25, -63.85,
                Math.toRadians(90)));
        Pose2d initialPos = new Pose2d(8.25, -63.85, Math.toRadians(90));
        drive.setPoseEstimate(initialPos);

        Robot odyssey = new Robot(this, "Blue", true);
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

                .stopAndAdd(odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP2, true))
                //.lineToY(-36.5)
                //.waitSeconds(1)
                //.stopAndAdd(performSpecimenDropoff(odyssey))
                //.strafeToConstantHeading(new Vector2d(27, -40.15))
                //.splineToConstantHeading(new Vector2d(45, -9), 0)

                //.lineToY(36.6)
                .lineToY(-41)
                .waitSeconds(0.3)
              //  .turn(Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(28,-41))
               // .lineToX(34).turn(Math.toRadians(90))
                .waitSeconds(0.05)
                //       .lineToY(-7)
                //      .waitSeconds(0.3)
                //    .strafeToConstantHeading(new Vector2d(40,-21))
                .splineToConstantHeading(new Vector2d(40, -21), 0).waitSeconds(0.01)
                .lineToY(-62)
                .waitSeconds(0.0000001)
                .splineToConstantHeading(new Vector2d(49, -21), 0)
                .waitSeconds(0.0000001)
                .lineToY(-60)
                .waitSeconds(0.0000001)
                //.lineToY(-47)
               // .turn(Math.toRadians(180))
               .splineToConstantHeading(new Vector2d(55.8, -21), 0)
                .waitSeconds(0.0000001)
                .lineToY(-61.5)
                .waitSeconds(0.00000001)
               // .splineToConstantHeading(new Vector2d(44.5, -60 ), 0)
               // .turn(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(44.5,-62.5,Math.toRadians(270)),Math.toRadians(90))
        .waitSeconds(0.0000001 )
                .stopAndAdd(performSpecimenPickup(odyssey))
                .waitSeconds(0.0000001)
           //     .turn(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(3,-41,Math.toRadians(90)),Math.toRadians(90));

        Action tsc1 = tab.build();

//

        waitForStart();
        coord.start();
        pidf.start();
        Actions.runBlocking(tsc1);

        // Move forward to position (24, 0, 0)
//        moveToPosition(drive, new Pose2d(24, 0, 0));

        // Perform specimen pickup sequence
//        performSpecimenPickup(odyssey);

        // Perform specimen drop-off sequence
//        performSpecimenDropoff(odyssey);

        // Move backward to position (12, 0, 0)
//        moveToPosition(drive, new Pose2d(12, 0, 0));
    }

    /**
     * Moves the robot to a specific position using the MecanumDrive's trajectory capabilities.
     */


    private void moveToPosition(MecanumDrive mecanumDrive, Pose2d targetPose) {
        /**   try {
         MecanumDrive.FollowTrajectoryAction trajectoryAction = mecanumDrive.new FollowTrajectoryAction(
         [FollowTrajectoryAction]mecanumDrive.actionBuilder(mecanumDrive.pose)
         .lineToX(20)
         .build()
         );

         // Run the trajectory
         while (opModeIsActive() && trajectoryAction.run()) {
         // Wait for trajectory to complete
         }
         } catch (Exception e) {
         // Handle trajectory execution errors
         }*/
    }

    /**
     * Handles the sequence for picking up a specimen.
     */
    private Action performSpecimenPickup(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
        sleep(500);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);

        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);
                return false;
            }
        };
    }

    /**
     * Handles the sequence for dropping off a specimen.
     */
    private Action performSpecimenDropoff(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
                sleep(500);

                odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
                //odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_PICK);
                sleep(500);

                //odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                //sleep(500);

                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(500);

                //odyssey.odysseyArm.move(ArmStates.INITIALIZED);
                //sleep(1000);
                return false;
            }
        };

    }
}

package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.AutonStates.AutonPose;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

import com.acmerobotics.roadrunner.Pose2d;

@Autonomous(name = "Auton_RedRightBucket")
public class Auton_RedRightBucket extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive

        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(8.25, -63.85,
                Math.toRadians(90)));

        Pose2d initialPos = new Pose2d(-8.25, -63.85, Math.toRadians(90));
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

                // .stopAndAdd(odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP2, true))
                .strafeToConstantHeading(new Vector2d(8,-35.5))
                .waitSeconds(1)
                .stopAndAdd(performSpecimenDropoff(odyssey))
                .waitSeconds(1)
                .lineToY(-40)
                .splineToConstantHeading(new Vector2d(-28, -41.0), 0, drive.fastVelConstraint, drive.defaultAccelConstraint).waitSeconds(0.01)
                .waitSeconds(1)
                .stopAndAdd(performSpecimenPickupFloor(odyssey))
                .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(-30, -44.5, Math.toRadians(55)), Math.toRadians(90), drive.fastVelConstraint, drive.defaultAccelConstraint)
                .waitSeconds(0.01)
                .stopAndAdd(performSlideUp(odyssey))
                .waitSeconds(2.8)
                .lineToY(-54.4)
                .splineToLinearHeading(new Pose2d(-37, -44.5, Math.toRadians(90)), Math.toRadians(90), drive.fastVelConstraint, drive.defaultAccelConstraint)
                .stopAndAdd(performSlideDown(odyssey))
                .waitSeconds(1)
                .stopAndAdd(performSpecimenPickupFloor(odyssey))
                .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(-30, -44.5, Math.toRadians(55)), Math.toRadians(90), drive.fastVelConstraint, drive.defaultAccelConstraint)
                .waitSeconds(0.01)
                .stopAndAdd(performSlideUp(odyssey))
                .waitSeconds(2.8)
                .lineToY(-54.4)
                .waitSeconds(0.1)
                .lineToY(-46);
               // .lineToY(-20 )
               // .splineToLinearHeading(new Pose2d(-45, -47.6, Math.toRadians(90)), Math.toRadians(90), drive.fastVelConstraint, drive.defaultAccelConstraint)
               //   .stopAndAdd(performSlideDown(odyssey));
                //.waitSeconds(1)
               // .stopAndAdd(performSpecimenPickupFloor(odyssey));


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
                sleep(1000);

                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
                sleep(500);
                return false;
            }
        };
    }



    private Action performSlideUp(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET);

                return false;
            }
        };
    }

    private Action performSlideDown(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                odyssey.odysseySlider.slide(SliderStates.RETRACTED);
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

                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_LANDSCAPE);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
                sleep(500);

                //odyssey.odysseyArm.move(ArmStates.INITIALIZED);
                //sleep(1000);
                return false;
            }
        };

    }

    private Action performSpecimenPickupFloor(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                odyssey.odysseyArm.move(ArmStates.LOWERED);


                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(500);

                odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
                sleep(500);

                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
                sleep(1000);

                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(500);

                return false;
            }
        };

    }
}
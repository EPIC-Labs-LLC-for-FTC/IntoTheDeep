package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

import com.acmerobotics.roadrunner.Pose2d;

@Autonomous(name = "Auton_BlueLeftHangPidf")
public class Auton_BlueLeftHangPidf extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive
        Robot odyssey = new Robot(this, "Blue", true);
        SparkFunOTOSDrive drive= new SparkFunOTOSDrive(hardwareMap,new Pose2d(8.25, -63.85,
                Math.toRadians(90)));
        Pose2d initialPos= new Pose2d(8.25, -63.85, Math.toRadians(90));
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
                .strafeToConstantHeading(new Vector2d(27, -40.15))
                .splineToConstantHeading(new Vector2d(45, -9), 0);

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
    private void performSpecimenPickup(Robot odyssey) throws InterruptedException {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
        sleep(1000);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(1000);

        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(1000);
    }

    /**
     * Handles the sequence for dropping off a specimen.
     */
    private void performSpecimenDropoff(Robot odyssey) throws InterruptedException {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_PICK);
        sleep(500);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.INITIALIZED);
        sleep(1000);
    }
}

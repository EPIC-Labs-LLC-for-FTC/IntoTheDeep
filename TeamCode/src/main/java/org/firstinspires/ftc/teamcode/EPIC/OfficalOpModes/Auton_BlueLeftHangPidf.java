package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
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

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive
        Robot odyssey = new Robot(this, "Blue");
        SparkFunOTOSDrive drive= new SparkFunOTOSDrive(hardwareMap,new Pose2d(8.25, -63.85, Math.toRadians(90)));
        Pose2d initialPos= new Pose2d(8.25, -63.85, Math.toRadians(90));
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {
            idle();
        }

        TrajectoryActionBuilder tab = drive.actionBuilder(initialPos)

                .lineToY(-32.15)
                .strafeTo(new Vector2d(15, -32.15))

                .splineToConstantHeading(new Vector2d(42, -9), Math.toRadians(90));

        Action tsc1 = tab.build();

//

        waitForStart();
        Actions.runBlocking(tsc1);
        telemetry.addData("x :",  drive.pose.position.x);
        telemetry.addData("x :",  drive.pose.position.y);
        sleep(10000);

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

package org.firstinspires.ftc.teamcode.pedroPathing.examples;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "Auton_RedRightBucket_Pedro")
public class Auton_RedRightBucket_Pedro extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Robot odyssey;
    private int pathState = 0;

    private final Pose startPose = new Pose(-8.25, -63.85, Math.toRadians(90));
    private final Pose specimenDropPose = new Pose(-8.25, -43);
    private final Pose firstPickupPose = new Pose(26, 39);
    private final Pose depositPose = new Pose(31, 44.5, Math.toRadians(55));
    private final Pose parkPose = new Pose(2, 15, Math.toRadians(270));

    private Path moveToSpecimenDrop, moveToFirstPickup, moveToDeposit, moveToPark;

    @Override
    public void runOpMode() throws InterruptedException {

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        odyssey = new Robot(this, "Red", true);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        buildPaths();
        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                    odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                }
            }
        };

        while (opModeInInit()) {
            telemetry.addData("Status", "Waiting for Start");
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.update();
        }

        waitForStart();
        pidf.start();
        opmodeTimer.resetTimer();

        while (opModeIsActive()) {
            follower.update();
            autonomousPathUpdate();

            telemetry.addData("Path State", pathState);
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.update();
        }
    }

    private void buildPaths() {
        moveToSpecimenDrop = new Path(new BezierLine(new Point(startPose), new Point(specimenDropPose)));
        moveToFirstPickup = new Path(new BezierLine(new Point(specimenDropPose), new Point(firstPickupPose)));
        moveToDeposit = new Path(new BezierLine(new Point(firstPickupPose), new Point(depositPose)));
        moveToPark = new Path(new BezierLine(new Point(depositPose), new Point(parkPose)));
    }

    private void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(moveToSpecimenDrop);
                pathState = 1;
                break;
            case 1:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1) { // Fail-safe timeout
                    performSpecimenDropoff(odyssey);
                    pathTimer.resetTimer();
                    pathState = 2;
                }
                break;
            case 2:
                if (pathTimer.getElapsedTimeSeconds() > 1) {
                    follower.followPath(moveToFirstPickup);
                    pathState = 3;
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    performSpecimenPickup(odyssey);
                    pathTimer.resetTimer();
                    pathState = 4;
                }
                break;
            case 4:
                if (pathTimer.getElapsedTimeSeconds() > 1) {
                    performSlideUp(odyssey);
                    follower.followPath(moveToDeposit);
                    pathState = 5;
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    performSlideDown(odyssey);
                    pathTimer.resetTimer();
                    pathState = 6;
                }
                break;
            case 6:
                if (pathTimer.getElapsedTimeSeconds() > 1) {
                    follower.followPath(moveToPark);
                    pathState = 7;
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    pathState = -1;
                }
                break;
        }
    }

    private void performSpecimenDropoff(Robot odyssey) {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);
        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
        sleep(500);
        odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
    }

    private void performSpecimenPickup(Robot odyssey) {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(500);
        odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
    }

    private void performSlideUp(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET);
        sleep(1000);
    }

    private void performSlideDown(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
        sleep(1000);
    }
}

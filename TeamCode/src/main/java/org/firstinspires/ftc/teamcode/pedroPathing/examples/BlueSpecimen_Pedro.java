package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
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

@Autonomous(name = "BlueSpecimen_Pedro")
public class BlueSpecimen_Pedro extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private Robot odyssey;

    private int pathState = 0;

    private final Pose startPose = new Pose(8.0, 64, Math.toRadians(0));
    private final Pose specimenDropPose = new Pose(28.5, 64);
    private final Pose specimenBackPose = new Pose(29, 40);
    private final Pose firstSamplePushStartPose = new Pose(56, 37);
    private final Pose firstSamplePushEndPose = new Pose(10, 24);
    private final Pose secondSamplePushStartPose = new Pose(56, 22);
    private final Pose secondSamplePushEndPose = new Pose(10, 22);
    private final Pose thirdSamplePushStartPose = new Pose(56, 26);
    private final Pose thirdSamplePushEndPose = new Pose(10, 20);
    private final Pose sampleGrab = new Pose(25, 116);
    private final Pose parkPose = new Pose(2, 15, Math.toRadians(270));

    private Path goToPreload, moveToPark;
    private PathChain scorePreload, pushFirstSample, firstSampleObservationPoint, pushSecondSample, pushThirdSample, grabSampleFromPlayer, secondSampleObservationPoint, thirdSampleObservationPoint;

    private void buildPaths() {

        // Path for scoring preload
        goToPreload = new Path(new BezierLine(new Point(startPose), new Point(specimenDropPose)));
        goToPreload.setConstantHeadingInterpolation(0);


        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenDropPose), new Point(specimenBackPose)))
                .setConstantHeadingInterpolation(0)
                .build();
        pushFirstSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenBackPose), new Point(firstSamplePushStartPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        firstSampleObservationPoint = follower.pathBuilder()
                .addPath(new BezierLine(new Point(firstSamplePushStartPose), new Point(firstSamplePushEndPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        pushSecondSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(firstSamplePushEndPose), new Point(firstSamplePushStartPose)))
                .addPath(new BezierLine(new Point(firstSamplePushStartPose), new Point(secondSamplePushStartPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        secondSampleObservationPoint = follower.pathBuilder()
                .addPath(new BezierLine(new Point(secondSamplePushStartPose), new Point(secondSamplePushEndPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        pushThirdSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(secondSamplePushEndPose), new Point(secondSamplePushStartPose)))
                .addPath(new BezierLine(new Point(secondSamplePushStartPose), new Point(thirdSamplePushStartPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        thirdSampleObservationPoint = follower.pathBuilder()
                .addPath(new BezierLine(new Point(thirdSamplePushStartPose), new Point(thirdSamplePushEndPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        grabSampleFromPlayer = follower.pathBuilder()
                .addPath(new BezierLine(new Point(thirdSamplePushEndPose), new Point(sampleGrab)))
                .setLinearHeadingInterpolation(thirdSamplePushEndPose.getHeading(), sampleGrab.getHeading())
                .build();

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sampleGrab), new Point(specimenDropPose)))
                .setConstantHeadingInterpolation(0)
                .build();


        // Curved path for parking
        //     park = new Path(new BezierCurve(new Point(scorePose), new Point(parkControlPose), new Point(parkPose)));
        //    park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }


    @Override
    public void runOpMode() throws InterruptedException {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        odyssey = new Robot(this, "Blue", true);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        buildPaths();

        Thread pidf = new Thread(() -> {
            while (opModeIsActive()) {
                odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
            }
        });

        while (opModeInInit()) {
            telemetry.addData("Status", "Waiting for Start");
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


    private void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.setMaxPower(0.6);
                follower.followPath(goToPreload);
                pathState = 1;
                break;

            case 1:
                follower.followPath(scorePreload);
                pathState = 2;
                break;

            case 2:
                if (!follower.isBusy()) {
                    performSpecimenDropoffUnder(odyssey);
                    sleep(1000);
                    follower.followPath(pushFirstSample, true);
                    pathState = 3;
                }
                break;

            case 3:
                follower.setMaxPower(1.0);
                if (!follower.isBusy()) {
                    follower.followPath(firstSampleObservationPoint, true);
                    pathState = 4;
                }
                break;

            case 4:
                follower.setMaxPower(1.0);
                if (!follower.isBusy()) {
                    follower.followPath(pushSecondSample, true);
                    pathState = 5;
                }
                break;

            case 5:
                follower.setMaxPower(1.0);
                if (!follower.isBusy()) {
                    follower.followPath(secondSampleObservationPoint, true);
                    pathState = 6;
                }
                break;

            case 6:
                follower.setMaxPower(1.0);
                if (!follower.isBusy()) {
                    follower.followPath(pushThirdSample, true);
                    pathState = 7;
                }
                break;
            case 7:
                follower.setMaxPower(1.0);
                if (!follower.isBusy()) {
                    follower.followPath(thirdSampleObservationPoint, true);
                    pathState = 8;
                }
                break;

            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(grabSampleFromPlayer, true);
                    performSpecimenPickup(odyssey);
                    pathState = 9;
                }
                break;

            case 9:
                if (!follower.isBusy()) {
                    follower.setMaxPower(0.6);
                    follower.followPath(goToPreload);
                    pathState = 10;
                }
                break;
            case 10:
                follower.followPath(scorePreload);
                pathState = 11;
                break;

            case 11:
                if (!follower.isBusy()) {
                    performSpecimenDropoff(odyssey);
                    sleep(1000);
                    follower.followPath(grabSampleFromPlayer, true);
                    pathState = 12;
                }
                break;


//
//            case 8:
//                if (!follower.isBusy()) {
//                    pathState = -1;
//                }
//                break;


        }
    }

    private void performSpecimenDropoff(Robot odyssey) {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);
        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(1000);
        //odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        // sleep(500);
        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(1000);
    }

    private void performSpecimenDropoffUnder(Robot odyssey) {
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING_AUTON);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.AUTON_BUCKET_DROP);
        sleep(1600);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
    }


    //specimen pickup equals picking up it fomr the wall and smaple pickeup is when you pick irt up from the floor.

    private void performSpecimenPickup(Robot odyssey) {
        odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_PICK);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(500);
        odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
    }

    private void performSamplePickup(Robot odyssey) {

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.LOWERED);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);

        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(1000);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.LOWERED);
        sleep(1000);

    }


    private void performSlideUp(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET);
        sleep(2000);
    }

    private void performSlideDown(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
        sleep(1000);

    }

    private Runnable performSlideDownRunnable(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
        sleep(1000);
        return null;
    }
}


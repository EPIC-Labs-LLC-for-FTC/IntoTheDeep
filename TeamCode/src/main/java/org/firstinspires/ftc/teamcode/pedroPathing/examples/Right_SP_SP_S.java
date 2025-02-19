
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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@Autonomous(name = "Right_SP_SP_S")
public class Right_SP_SP_S extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private Robot odyssey;

    private int pathState;

    Arm_PIDF arm = new Arm_PIDF(hardwareMap);
    Wrist wrist = new Wrist(hardwareMap);
    Claw claw = new Claw(hardwareMap);

    private final Pose startPose = new Pose(-8.25, -63.85, Math.toRadians(90));
 //   Pose2d initialPos = new Pose2d(-8.25, -63.85, Math.toRadians(90));

    private final Pose scoreSP1Pose = new Pose(-8.25, -63);

    private final Pose readyDrop1 = new Pose(32, 36);

    private final Pose readyDrop2 = new Pose(58, 36);

    private final Pose readyDropS1Pose = new Pose(58, 24);

    private final Pose DropS1Pose = new Pose(10, 24);

    private final Pose readyDropS2Pose = new Pose(58, 15);

    private final Pose DropS2Pose = new Pose(10, 15);

    private final Pose readyDropS3Pose = new Pose(58, 8);

    private final Pose DropS3Pose = new Pose(10, 8);

    private final Pose scoreSP2Pose = new Pose(37, 62);

    private final Pose pickSP = new Pose(10, 27);

    private final Pose scoreSp3pose = new Pose(37, 64);

    private final Pose scoreSp4pose = new Pose(37, 66);

    private final Pose scoreSp5pose = new Pose(37, 68);

    private final Pose samplePickPose = new Pose(9, 27, Math.toRadians(-90));

    private final Pose readyBucketScorepose1 = new Pose(37, 68);

    private final Pose readyBucketScorepose2 = new Pose(37, 102);

    private final Pose highBucketScorepose = new Pose(18, 125, Math.toRadians(-45));

    private final Pose readyParkpose = new Pose(64, 104, Math.toRadians(-90));

    private final Pose parkPose = new Pose(64, 98);

    // s = score, S = sample, SP = Specimen, b=back, r = ready, d = drop, p(at the end) = pose, p(at the beginning) = pick, HB = High Bucket, r = ready

    private Path scorePreload, park;
    private PathChain r1Dp, r2dp, rdS1p, dS1p, brdS1p, rdS2p, dS2p, brdS2p, rds3p, dS3p, sSP2p, pSP3, sSP3p, pSP4, sSP4p, pSP5, sSP5p, spp, rbsp1, rbsp2, SHBs, rpp;

    public void buildPaths() {

        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scoreSP1Pose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scoreSP1Pose.getHeading());

        r1Dp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSP1Pose), new Point(readyDrop1)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        r2dp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDrop1), new Point(readyDrop2)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        rdS1p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDrop2), new Point(readyDropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        dS1p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS1Pose), new Point(DropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        brdS1p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS1Pose), new Point(readyDropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        rdS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS1Pose), new Point(readyDropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        dS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS2Pose), new Point(DropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        brdS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS2Pose), new Point(readyDropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        rds3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS2Pose), new Point(readyDropS3Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        dS3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS3Pose), new Point(DropS3Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        sSP2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS3Pose), new Point(scoreSP2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        pSP3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSP2Pose), new Point(pickSP)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        sSP3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickSP), new Point(scoreSp3pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        pSP4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSp3pose), new Point(pickSP)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        sSP4p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickSP), new Point(scoreSp4pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        pSP5 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSp4pose), new Point(pickSP)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        sSP5p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickSP), new Point(scoreSp5pose)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        spp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSp5pose), new Point(samplePickPose)))
                .setLinearHeadingInterpolation(scoreSp5pose.getHeading(), samplePickPose.getHeading())
                .build();

        rbsp1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplePickPose), new Point(readyBucketScorepose1)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        rbsp2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyBucketScorepose1), new Point(readyBucketScorepose2)))
                .setConstantHeadingInterpolation(Math.toRadians(1))
                .build();

        SHBs = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyBucketScorepose2), new Point(highBucketScorepose)))
                .setLinearHeadingInterpolation(readyBucketScorepose2.getHeading(), highBucketScorepose.getHeading())
                .build();

        rpp = follower.pathBuilder()
                .addPath(new BezierLine(new Point(highBucketScorepose), new Point(readyParkpose)))
                .setLinearHeadingInterpolation(highBucketScorepose.getHeading(), readyParkpose.getHeading())
                .build();

        park = new Path(new BezierLine(new Point(readyParkpose), new Point(parkPose)));
        park.setConstantHeadingInterpolation(1);
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                if (!follower.isBusy()) {

                    follower.followPath(scorePreload);
                    setPathState(1);
                }
                break;
            case 1:
                if (!follower.isBusy()) {

                    follower.followPath(r1Dp, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {

                    follower.followPath(r2dp, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {

                    follower.followPath(rdS1p, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {

                    follower.followPath(dS1p, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {

                    follower.followPath(brdS1p, true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {

                    follower.followPath(rdS2p, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {

                    follower.followPath(dS2p, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {

                    follower.followPath(brdS2p, true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()) {

                    follower.followPath(rds3p, true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()) {

                    follower.followPath(dS3p, true);
                    setPathState(11);
                }
                break;
            case 11:
                if (!follower.isBusy()) {

                    follower.followPath(sSP2p, true);
                    setPathState(12);
                }
                break;
            case 12:
                if (!follower.isBusy()) {

                    follower.followPath(pSP3, true);
                    setPathState(13);
                }
                break;
            case 13:
                if (!follower.isBusy()) {

                    follower.followPath(sSP3p, true);
                    setPathState(14);
                }
                break;
            case 14:
                if (!follower.isBusy()) {

                    follower.followPath(pSP4, true);
                    setPathState(15);
                }
                break;
            case 15:
                if (!follower.isBusy()) {

                    follower.followPath(sSP4p, true);
                    setPathState(16);
                }
                break;
            case 16:
                if (!follower.isBusy()) {

                    follower.followPath(pSP5, true);
                    setPathState(17);
                }
                break;
            case 17:
                if (!follower.isBusy()) {

                    follower.followPath(sSP5p, true);
                    setPathState(18);
                }
                break;
            case 18:
                if (!follower.isBusy()) {

                    follower.followPath(spp, true);
                    setPathState(19);
                }
                break;
            case 19:
                if (!follower.isBusy()) {

                    follower.followPath(rbsp1, true);
                    setPathState(20);
                }
                break;
            case 20:
                if (!follower.isBusy()) {

                    follower.followPath(rbsp2, true);
                    setPathState(21);
                }
                break;
            case 21:
                if (!follower.isBusy()) {

                    follower.followPath(SHBs, true);
                    setPathState(22);
                }
                break;
            case 22:
                if (!follower.isBusy()) {

                    follower.followPath(rpp, true);
                    setPathState(23);
                }
                break;
            case 24:
                if (!follower.isBusy()) {

                    follower.followPath(park, true);
                    setPathState(25);
                }
                break;
            case 25:
                if (!follower.isBusy()) {
                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() {

        odyssey = new Robot(this, "blue",true);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        buildPaths();

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();


        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();



        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            follower.update();
            autonomousPathUpdate();

            telemetry.addData("path state", pathState);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.update();
        }
    }


}
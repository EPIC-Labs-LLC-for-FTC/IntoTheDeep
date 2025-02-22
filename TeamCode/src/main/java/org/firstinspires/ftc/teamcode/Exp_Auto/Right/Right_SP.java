package org.firstinspires.ftc.teamcode.Exp_Auto.Right;

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

import org.firstinspires.ftc.teamcode.PedroPathing.Constants.FConstants;
import org.firstinspires.ftc.teamcode.PedroPathing.Constants.LConstants;
import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Wrist;

@Autonomous(name = "Right_SP")
public class Right_SP extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.2, 56.2, Math.toRadians(0));

    private final Pose scoreSP1Pose = new Pose(36, 61);

    private final Pose scoreSP1BackPose = new Pose(20, 61);

    private final Pose readyDrop1 = new Pose(30, 36);

    private final Pose readyDrop2 = new Pose(58, 36);

    private final Pose readyDropS1Pose = new Pose(58, 24);

    private final Pose DropS1Pose = new Pose(12, 24);

    private final Pose readyDropS2Pose = new Pose(58, 15);

    private final Pose DropS2Pose = new Pose(12, 15);

    private final Pose readyDropS3Pose = new Pose(58, 11);

    private final Pose DropS3Pose = new Pose(12, 9);

    private final Pose scoreSP2Pose = new Pose(15, 62);

    private final Pose scoreSP2Pose2 = new Pose(37, 62);

    private final Pose pickSP = new Pose(12, 27);

    private final Pose scoreSp3pose = new Pose(37, 66);

    private final Pose scoreSp4pose = new Pose(37, 70);

    private final Pose parkPose = new Pose(9, 27);

    // s = score, S = sample, SP = Specimen, b=back, r = ready, d = drop, p(at the end) = pose, p(at the beginning) = pick

    private Path park;
    private PathChain scorePreload, r1Dp, r2dp, rdS1p, dS1p, brdS1p, rdS2p, dS2p, brdS2p, rds3p, dS3p, sSP2p, pSP3, sSP3p, pSP4, sSP4p;

    public void buildPaths() {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scoreSP1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addParametricCallback(0.001, () -> arm.specimenDrop())
                .addParametricCallback(0.001, () -> wrist.specimenDrop())
                .addParametricCallback(1, () -> claw.open())
                .addParametricCallback(1, () -> arm.specimenPick())
                .addParametricCallback(1, () -> wrist.specimenPick())

                .build();

        r1Dp = follower.pathBuilder()

                .addPath(new BezierLine(new Point(scoreSP1Pose), new Point(scoreSP1BackPose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(new BezierLine(new Point(scoreSP1BackPose), new Point(readyDrop1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        r2dp = follower.pathBuilder()

                .addPath(new BezierLine(new Point(readyDrop1), new Point(readyDrop2)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rdS1p = follower.pathBuilder()

                .addPath(new BezierLine(new Point(readyDrop2), new Point(readyDropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        dS1p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS1Pose), new Point(DropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        brdS1p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS1Pose), new Point(readyDropS1Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rdS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS1Pose), new Point(readyDropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        dS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS2Pose), new Point(DropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        brdS2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS2Pose), new Point(readyDropS2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rds3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS2Pose), new Point(readyDropS3Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        dS3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyDropS3Pose), new Point(DropS3Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addParametricCallback(1, () -> claw.close())


                .build();

        sSP2p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(DropS3Pose), new Point(scoreSP2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addParametricCallback(0.01, () -> arm.specimenDrop())
                .addParametricCallback(0.01, () -> wrist.specimenDrop())

                .addPath(new BezierLine(new Point(scoreSP2Pose), new Point(scoreSP2Pose2)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        pSP3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSP2Pose), new Point(pickSP)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addParametricCallback(0.001, () -> claw.open())
                .addParametricCallback(0.001, () -> arm.specimenPick())
                .addParametricCallback(0.001, () -> wrist.specimenPick())
                .addParametricCallback(1, () -> claw.close())

                .build();

        sSP3p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickSP), new Point(scoreSP2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(new BezierLine(new Point(scoreSP2Pose), new Point(scoreSp3pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addParametricCallback(0.001, () -> arm.specimenDrop())
                .addParametricCallback(0.001, () -> wrist.specimenDrop())
                .addParametricCallback(1, () -> claw.open())
                .addParametricCallback(1, () -> arm.specimenPick())
                .addParametricCallback(1, () -> wrist.specimenPick())

                .build();

        pSP4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSp3pose), new Point(pickSP)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        sSP4p = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickSP), new Point(scoreSP2Pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(new BezierLine(new Point(scoreSP2Pose), new Point(scoreSp4pose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        park = new Path(new BezierLine(new Point(scoreSp4pose), new Point(parkPose)));
        park.setConstantHeadingInterpolation(0);
    }

    public void autonomousPathUpdate() {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        switch (pathState) {
            case 0:
                if(!follower.isBusy()){
                    follower.setMaxPower(0.5);

                    follower.followPath(scorePreload);
                    setPathState(1);
                }
                break;
            case 1:
                if(!follower.isBusy()) {
                    follower.setMaxPower(1);

                    follower.followPath(r1Dp,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {

                    follower.followPath(r2dp,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {

                    follower.followPath(rdS1p,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {

                    follower.followPath(dS1p,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {

                    follower.followPath(brdS1p,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {

                    follower.followPath(rdS2p, true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {

                    follower.followPath(dS2p,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {

                    follower.followPath(brdS2p,true);
                    setPathState(9);
                }
                break;
            case 9:
                if(!follower.isBusy()) {

                    follower.followPath(rds3p,true);
                    setPathState(10);
                }
                break;
            case 10:
                if(!follower.isBusy()) {

                    follower.followPath(dS3p,true);
                    setPathState(11);
                }
                break;
            case 11:
                if(!follower.isBusy()) {

                    follower.followPath(sSP2p,true);
                    setPathState(12);
                }
                break;
            case 12:
                if(!follower.isBusy()) {

                    follower.followPath(pSP3,true);
                    setPathState(13);
                }
                break;
            case 13:
                if(!follower.isBusy()) {

                    follower.followPath(sSP3p,true);
                    setPathState(14);
                }
                break;
            case 14:
                if(!follower.isBusy()) {

                    follower.followPath(pSP4,true);
                    setPathState(15);
                }
                break;
            case 15:
                if(!follower.isBusy()) {

                    follower.followPath(sSP4p,true);
                    setPathState(16);
                }
                break;
            case 16:
                if(!follower.isBusy()) {

                    follower.followPath(park,true);
                    setPathState(17);
                }
                break;
            case 17:
                if(!follower.isBusy()) {
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
    public void runOpMode() throws InterruptedException {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        pathTimer = new Timer();
        Timer opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        buildPaths();

        while (opModeInInit()) {

            arm.armStart();
            wrist.start();
            claw.close();

        }

        waitForStart();

        opmodeTimer.resetTimer();
        setPathState(0);

        while (opModeIsActive()){

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
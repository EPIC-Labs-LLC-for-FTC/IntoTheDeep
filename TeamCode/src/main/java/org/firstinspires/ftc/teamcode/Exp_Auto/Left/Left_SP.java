package org.firstinspires.ftc.teamcode.Exp_Auto.Left;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
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

@Autonomous(name = "Left_SP")
public class Left_SP extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer;

    private int pathState;

    private final Pose startPose = new Pose(8, 88, Math.toRadians(0));

    private final Pose scoreSPPose = new Pose(36, 80);

    private final Pose readyGrab = new Pose(17, 97);

    private final Pose grabS1 = new Pose(29, 121);

    private final Pose dropS1 = new Pose(18, 126, Math.toRadians(-45));

    private final Pose grabS2 = new Pose(29, 130, Math.toRadians(0));

    private final Pose dropS2 = new Pose(18, 126, Math.toRadians(-45));

    private final Pose grabS3 = new Pose(45, 127, Math.toRadians(90));

    private final Pose dropS3 = new Pose(18, 126, Math.toRadians(-45));

    private final Pose readyParkpose = new Pose(60, 115, Math.toRadians(-90));

    private final Pose parkPose = new Pose(60, 96);

    private PathChain scorePreload, rg, gs1, ds1, gs2, ds2, gs3, ds3, readyPark, park;

    public void buildPaths() {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scoreSPPose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        rg = follower.pathBuilder()

                .addPath(new BezierLine(new Point(scoreSPPose), new Point(readyGrab)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        gs1 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(readyGrab), new Point(grabS1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        ds1 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(grabS1), new Point(dropS1)))
                .setLinearHeadingInterpolation(grabS1.getHeading(), dropS1.getHeading())
                .build();

        gs2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(dropS1), new Point(grabS2)))
                .setLinearHeadingInterpolation(dropS1.getHeading(), grabS2.getHeading())
                .build();

        ds2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(grabS2), new Point(dropS2)))
                .setLinearHeadingInterpolation(grabS2.getHeading(), dropS2.getHeading())
                .build();

        gs3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(dropS2), new Point(grabS3)))
                .setLinearHeadingInterpolation(dropS2.getHeading(), grabS3.getHeading())
                .build();

        ds3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(grabS3), new Point(dropS3)))
                .setLinearHeadingInterpolation(grabS3.getHeading(), dropS3.getHeading())
                .build();

        readyPark = follower.pathBuilder()
                .addPath(new BezierLine(new Point(dropS3), new Point(readyParkpose)))
                .setLinearHeadingInterpolation(dropS3.getHeading(), readyParkpose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(new Point(readyParkpose), new Point(parkPose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }

    public void autonomousPathUpdate() {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        switch (pathState) {
            case 0:
                if(!follower.isBusy()){

                    follower.followPath(scorePreload);
                    setPathState(1);
                }
                break;
            case 1:
                if(!follower.isBusy()) {

                    follower.followPath(rg,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {

                    follower.followPath(gs1,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {

                    follower.followPath(ds1,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {

                    follower.followPath(gs2,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {

                    follower.followPath(ds2,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {

                    follower.followPath(gs3, true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {

                    follower.followPath(ds3,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {

                    follower.followPath(readyPark,true);
                    setPathState(9);
                }
                break;
            case 9:
                if(!follower.isBusy()) {

                    follower.followPath(park,true);
                    setPathState(10);
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
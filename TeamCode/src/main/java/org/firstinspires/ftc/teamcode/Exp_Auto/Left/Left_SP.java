package org.firstinspires.ftc.teamcode.Exp_Auto.Left;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants.FConstants;
import org.firstinspires.ftc.teamcode.PedroPathing.Constants.LConstants;
import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;
import org.firstinspires.ftc.teamcode.components.Wrist;

@Autonomous(name = "Left_SP")
public class Left_SP extends OpMode {

    ColorSensor colorSensor;
    Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
    Slides slides = new Slides(hardwareMap);
    Arm arm = new Arm(hardwareMap);
    Wrist wrist = new Wrist(hardwareMap);
    Claw claw = new Claw(hardwareMap);

    private Follower follower;
    private Timer pathTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.2, 111.8, Math.toRadians(0));

    private final Pose scorePose = new Pose(14, 129, Math.toRadians(147));

    private final Pose specimenPose = new Pose(35, 80, Math.toRadians(315));

    private final Pose pickup1Pose = new Pose(45, 108, Math.toRadians(270));

    private final Pose pickup2Pose = new Pose(45, 120, Math.toRadians(270));

    private final Pose pickup3Pose = new Pose(45, 132, Math.toRadians(270));

    private final Pose parkPose = new Pose(68, 105, Math.toRadians(90));

    private final Pose parkControlPose = new Pose(61, 112, Math.toRadians(90));

    private final Pose submersiblePick1 = new Pose(74, 98, Math.toRadians(90));

    private final Pose submersiblePick2 = new Pose(62, 98, Math.toRadians(90));

    private Path park;
    private PathChain scorePreloadedSpecimen, grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3, autonSearch, submersibleTravel, scorePickup4;

    public void buildPaths() {

        scorePreloadedSpecimen = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(specimenPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), specimenPose.getHeading())
                .build();

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenPose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(specimenPose.getHeading(), pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        autonSearch = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(submersiblePick1)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), submersiblePick1.getHeading())
                .build();

        submersibleTravel = follower.pathBuilder()
                .addPath(new BezierLine(new Point(submersiblePick1), new Point(submersiblePick2)))
                .setLinearHeadingInterpolation(submersiblePick1.getHeading(), submersiblePick2.getHeading())
                .build();

        scorePickup4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(submersiblePick2), new Point(scorePose)))
                .setLinearHeadingInterpolation(submersiblePick2.getHeading(), scorePose.getHeading())
                .build();



        park = new Path(new BezierCurve(new Point(scorePose), new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());



    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:

                follower.followPath(scorePreloadedSpecimen);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {

                    arm.specimenReadyDrop();
                    arm.specimenDrop();
                    claw.open();

                    follower.followPath(grabPickup1,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {

                    wrist.wristPick();
                    claw.open();
                    arm.armPick();
                    claw.close();
                    arm.armDrop();

                    follower.followPath(scorePickup1,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {

                    slides.moveTo();
                    wrist.wristDrop();
                    claw.open();
                    claw.close();
                    slides.moveTo();

                    follower.followPath(grabPickup2,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {

                    wrist.wristPick();
                    claw.open();
                    arm.armPick();
                    claw.close();
                    arm.armDrop();

                    follower.followPath(scorePickup2,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {

                    slides.moveTo();
                    wrist.wristDrop();
                    claw.open();
                    claw.close();
                    slides.moveTo();

                    follower.followPath(grabPickup3,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {

                    wrist.wristPick();
                    claw.open();
                    arm.armPick();
                    claw.close();
                    arm.armDrop();

                    follower.followPath(scorePickup3, true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {

                    slides.moveTo();
                    wrist.wristDrop();
                    claw.open();
                    claw.close();
                    slides.moveTo();

                    follower.followPath(autonSearch,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {


                    follower.followPath(submersibleTravel,true);
                    setPathState(9);

                }
                break;
            case 9:
                if(!follower.isBusy()) {

                    arm.armPick();
                    wrist.wristPick();
                    claw.open();

                    follower.followPath(scorePickup4,true);
                    setPathState(10);

                }
                break;
            case 10:
                if(!follower.isBusy()) {

                    follower.followPath(park,true);
                    setPathState(8);
                }
                break;
            case 11:
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
    public void loop() {

        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {

        arm.armStart();
        wrist.start();
        claw.close();

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {
    }
}
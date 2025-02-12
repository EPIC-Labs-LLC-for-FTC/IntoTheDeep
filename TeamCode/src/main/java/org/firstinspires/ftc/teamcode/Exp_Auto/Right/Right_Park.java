package org.firstinspires.ftc.teamcode.Exp_Auto.Right;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants.FConstants;
import org.firstinspires.ftc.teamcode.PedroPathing.Constants.LConstants;

@Autonomous(name = "Right_Park")
public class Right_Park extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8.2, 56.3, Math.toRadians(1));

    private final Pose parkPose = new Pose(8.6, 23, Math.toRadians(1));

    private Path park;

    public void buildPaths() {

        park = new Path(new BezierLine(new Point(startPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(startPose.getHeading(), parkPose.getHeading());

    }

    public void autonomousPathUpdate() {
        if (pathState == 0) {
            follower.followPath(park);
            setPathState(1);
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
package org.firstinspires.ftc.teamcode.Exp_Auto.Left;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
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

@Autonomous(name = "Left_Park")
public class Left_Park extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(8, 112, Math.toRadians(-90));

    private final Pose parkPose = new Pose(68, 105);

    private final Pose parkPose2 = new Pose(68, 95);

    private Path park, park2;

    public void buildPaths() {

        park = new Path(new BezierLine(new Point(startPose), new Point(parkPose)));
        park.setConstantHeadingInterpolation(-90);

        park2 = new Path(new BezierLine(new Point(parkPose), new Point(parkPose2)));
        park2.setConstantHeadingInterpolation(-90);

    }

    public void autonomousPathUpdate() {

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        switch (pathState) {

            case 0:

                follower.followPath(park);
                setPathState(1);
                break;

            case 1:

                if(!follower.isBusy()) {

                    follower.followPath(park2,true);
                    setPathState(2);

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
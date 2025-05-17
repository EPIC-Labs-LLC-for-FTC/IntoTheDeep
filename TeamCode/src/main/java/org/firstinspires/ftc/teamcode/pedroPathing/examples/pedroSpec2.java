
package org.firstinspires.ftc.teamcode.pedroPathing.examples;

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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "Spec_Final_Pedro")
public class pedroSpec2 extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0.1; //sf=0

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private Robot odyssey;
    private int followerBusyInitalAccess = 0;
    private double followerBusyStartTime = 0;
    private double timeInFollowerBusyState = 0;
    private double maxFollowerBusyTime = 3;

    private int pathState = 0; // supposed to be 0
    private int nextPathState = 0;

    private final Pose startPose = new Pose(0, 0, Math.toRadians(0)); //x=0, y=0
    private final Pose specimenDropPose = new Pose(29.25, 0);
    private final Pose centerOfPushPose = new Pose(6,-8);
    private final Pose pushPose = new Pose(50, -35, Math.toRadians(180));
    private final Pose parkPose = new Pose(2, -35, Math.toRadians(180));
    private final Pose centerOfPushPose2 = new Pose(27,-28);
    private final Pose pushPose2 = new Pose(50, -45, Math.toRadians(180));
    private final Pose parkPose2 = new Pose(3, -45, Math.toRadians(180));
    private final Pose centerOfPushPose3 = new Pose(27,-40);
    private final Pose pushPose3 = new Pose(50, -51.5, Math.toRadians(180));
    private final Pose parkPose3 = new Pose(4, -51.5, Math.toRadians(180));
    private final Pose pickupPose = new Pose(21, 41);
    private final Pose specimenDropPose2 = new Pose(29.25, -1.5);
    private final Pose specimenBackPose = new Pose(21, -1);

    private Path goToPreload, moveToPark;
    private PathChain scorePreload, grabPickup1, scoreSpec, pushSample, park, pushSample2, park2, pushSample3, park3;

    private void buildPaths() {

        // Path for scoring preload
        goToPreload = new Path(new BezierLine(new Point(startPose), new Point(specimenDropPose)));
        goToPreload.setConstantHeadingInterpolation(0);

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenDropPose), new Point(specimenBackPose)))
                .setConstantHeadingInterpolation(0)
                .build();
        pushSample = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(specimenDropPose), new Point(centerOfPushPose), new Point(pushPose)))
                .setLinearHeadingInterpolation(centerOfPushPose.getHeading(), pushPose.getHeading())
                .build();
        park = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushPose), new Point(parkPose)))
                .setLinearHeadingInterpolation(pushPose.getHeading(), parkPose.getHeading())
                .build();
        pushSample2 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(parkPose), new Point(centerOfPushPose2), new Point(pushPose2)))
                .setLinearHeadingInterpolation(parkPose.getHeading(), pushPose2.getHeading())
                .build();
        park2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushPose2), new Point(parkPose2)))
                .setLinearHeadingInterpolation(pushPose2.getHeading(), parkPose2.getHeading())
                .build();
        pushSample3 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(parkPose2), new Point(centerOfPushPose3), new Point(pushPose3)))
                .setLinearHeadingInterpolation(parkPose2.getHeading(), pushPose3.getHeading())
                .build();
        park3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushPose3), new Point(parkPose3)))
                .setLinearHeadingInterpolation(pushPose3.getHeading(), parkPose3.getHeading())
                .build();
        scoreSpec = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickupPose), new Point(specimenDropPose2)))
                .setLinearHeadingInterpolation(pickupPose.getHeading(), specimenDropPose2.getHeading())
                .build();



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

            autonomousPathUpdate();

            telemetry.addData("Path State", pathState);
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.addData("elapsedTime", pathTimer.getElapsedTimeSeconds());
            telemetry.addData("nextState", nextPathState);
            telemetry.addData("timeInFollowerBusyState", timeInFollowerBusyState);
            telemetry.update();
        }
    }


    private void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // strafe to submirsable, working
                pathTimer.resetTimer();
                follower.setMaxPower(1);
                follower.followPath(goToPreload);
                follower.update();
                nextPathState = 1;
                pathState = 100;
                break;

            case 1: // score spec
                performSpecimenDropoffUnder(odyssey);
                nextPathState = 2;
                pathState = 2; //2
                break;

            case 2: // goes to sample and pushes
                follower.followPath(pushSample);
                follower.update();
                nextPathState = 3; //3
                pathState = 100;
                break;

            case 3: //push sample to observation zone
                follower.followPath(park);
                follower.update();
                nextPathState = 4; //4
                pathState = 100;
                break;

            case 4: // back up from bucket
                    follower.followPath(pushSample2);
                    follower.update();
                    nextPathState = 5; //5
                    pathState = 100;
                break;

            case 5: // slides down + sample pick up or arm up
                follower.followPath(park2);
                follower.update();
                nextPathState = 6; //5
                pathState = 100;
                break;

            case 6: // back up from bucket
                follower.followPath(pushSample3);
                follower.update();
                nextPathState = 7; //5
                pathState = 100;
                break;

            case 7: // slides down + sample pick up or arm up
                follower.followPath(park3);
                follower.update();
                nextPathState = 8; //5
                pathState = 100;
                break;

            case 8: // end state
                //if(!follower.isBusy()) {
                    telemetry.addData("path state", pathState);
                    telemetry.addData("x", follower.getPose().getX());
                    telemetry.addData("y", follower.getPose().getY());
                    telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                    telemetry.addData("elapsedTime", pathTimer.getElapsedTimeSeconds());
                telemetry.addData("nextState", nextPathState);
                telemetry.addData("timeInFollowerBusyState", timeInFollowerBusyState);
                    telemetry.update();

                    follower.breakFollowing();

                sleep(10000);
                pathState = 7;
                //}
                break;

            case 100:
                if(followerBusyInitalAccess == 0) {
                    followerBusyInitalAccess = 1;
                    followerBusyStartTime = pathTimer.getElapsedTimeSeconds();
                }
                timeInFollowerBusyState = pathTimer.getElapsedTimeSeconds() - followerBusyStartTime;

                if(!follower.isBusy()){
                    pathState = nextPathState;
                    followerBusyInitalAccess = 0;
                }
                else if ( timeInFollowerBusyState > maxFollowerBusyTime) {
                    /* if (nextPathState == 4){
                        pathState = 4;
                    }
                    else {
                        pathState = 8;//nextPathState; //temporary, change to backup, slides down, arm up
                    } */
                    followerBusyInitalAccess = 0;
                }
                else {
                    follower.update();
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
        sleep(1000);
        //odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        // sleep(500);
        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(1000);
    }

    private void performSpecimenDropoffUnder(Robot odyssey) {
        odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_DROP_AUTON);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.AUTON_SPECIMEN_DROP);
        sleep(500);

        //odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        //sleep(2000);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);

        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(500);

        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING_AUTON);
        sleep(500);

    }

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

    private void performSamplePickup1(Robot odyssey) {

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(400);

        odyssey.odysseyArm.move(ArmStates.AUTON_LOWERED);
        sleep(450);

        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(400);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(400);

        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING);
        sleep(400);

        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(900);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(400);

        odyssey.odysseyArm.move(ArmStates.AUTON_LOWERED);
        sleep(1000);

    }

    private void performSamplePickup2(Robot odyssey) {

        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(400);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(400);

        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING);
        sleep(400);

        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
        sleep(900);

        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(400);

        odyssey.odysseyArm.move(ArmStates.AUTON_LOWERED);
        sleep(1000);

    }


    private void performSlideUp(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.AUTON_HIGH_BUCKET);
        sleep(1000);
    }

    private void performSlideDown(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
        sleep(100); //1000

    }

    private Runnable performSlideDownRunnable(Robot odyssey) {
        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
        sleep(500);
        return null;
    }
}



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

@Autonomous(name = "Sample_Final_Pedro")
public class SampleFinalPedro extends LinearOpMode {
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
    private final Pose specimenDropPose = new Pose(29.25, -1.5);
    private final Pose specimenBackPose = new Pose(21, -1);
    private final Pose firstPickupPose = new Pose(18, 41);
    private final Pose secondPickupPose = new Pose(18.5, 51);//21.5, 51
    private final Pose thirdPickupPose = new Pose(12, 52, Math.toRadians(0));
    private final Pose depositPose = new Pose(0, 52, Math.toRadians(315));
    private final Pose bucketBackPose = new Pose(8, 49, Math.toRadians(0));
    private final Pose parkPose = new Pose(2, 15, Math.toRadians(270));

    private Path goToPreload, moveToPark;
    private PathChain scorePreload, grabPickup1, backFromBucket, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    private void buildPaths() {

        // Path for scoring preload
        goToPreload = new Path(new BezierLine(new Point(startPose), new Point(specimenDropPose)));
        goToPreload.setConstantHeadingInterpolation(0);

        // Path chains for picking up and scoring samples

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenDropPose), new Point(specimenBackPose)))
                .setConstantHeadingInterpolation(0)
                .build();
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(specimenDropPose), new Point(firstPickupPose))) // first one is sepecimenBackPose
                .setConstantHeadingInterpolation(0)
                .build();
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(firstPickupPose), new Point(depositPose)))
                .setLinearHeadingInterpolation(firstPickupPose.getHeading(), depositPose.getHeading())
                .build();
        backFromBucket = follower.pathBuilder()
                .addPath(new BezierLine(new Point(depositPose), new Point(bucketBackPose)))
                .setLinearHeadingInterpolation(depositPose.getHeading(), bucketBackPose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(bucketBackPose), new Point(secondPickupPose)))
                .setLinearHeadingInterpolation(bucketBackPose.getHeading(), secondPickupPose.getHeading())
                //.addParametricCallback(0.60, () -> performSlideDown(odyssey))
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(secondPickupPose), new Point(depositPose)))
                .setLinearHeadingInterpolation(secondPickupPose.getHeading(), depositPose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(depositPose), new Point(thirdPickupPose)))
                .setLinearHeadingInterpolation(depositPose.getHeading(), thirdPickupPose.getHeading())
                .addParametricCallback(0.30, () -> performSlideDown(odyssey))
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(thirdPickupPose), new Point(depositPose)))
                .setLinearHeadingInterpolation(thirdPickupPose.getHeading(), depositPose.getHeading())
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
                follower.setMaxPower(1); // 0.6 (UPDATED)
                follower.followPath(goToPreload);
                follower.update();
                nextPathState = 1; //1
                pathState = 100;
                break;

            case 1: // move back from submirsable, working
                performSpecimenDropoffUnder(odyssey);
                //follower.followPath(scorePreload);
                //follower.update();
                nextPathState = 2;
                pathState = 2; //2
                break;

            case 2: // move from submirsable back pose to first sample, working
                follower.followPath(grabPickup1);
                follower.update();
                nextPathState = 3;//3
                pathState = 100;
                break;

            case 3: //picking up sample, working + score sample
                performSamplePickup1(odyssey);
                sleep(500);
                performSlideUp(odyssey);
                follower.setMaxPower(0.6);
                follower.followPath(scorePickup1, true);
                follower.update();
                maxFollowerBusyTime = 1.75;
                nextPathState = 4; //4
                pathState = 100;
                break;

            case 4: // back up from bucket
                    maxFollowerBusyTime = 3; //3
                    follower.followPath(backFromBucket, true);
                    follower.update();
                    nextPathState = 5; //5
                    pathState = 100;
                break;

            case 5: // slides down + sample pick up or arm up
            performSlideDown(odyssey);
                    if(pathTimer.getElapsedTimeSeconds()<26.0){ // time should be 15.0 for 1+1
                        follower.followPath(grabPickup2, true);
                        follower.update();
                        nextPathState = 6;
                        pathState = 100;
                    }
                    else{
                        sleep(1200);
                        odyssey.odysseyArm.move(ArmStates.AUTON_ARM_UP);
                        pathState = 7;
                    }

                break;

            case 6: // pick up + score sample 2
                performSamplePickup2(odyssey);
                sleep(500);
                performSlideUp(odyssey);
                sleep(300);
                follower.followPath(scorePickup2, true);
                follower.update();
                maxFollowerBusyTime = 1.75;
                nextPathState = 4;
                pathState = 100;

                break;

            case 7: // end state
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
                    if (nextPathState == 4){
                        pathState = 4;
                    }
                    else {
                        pathState = 7;//nextPathState; //temporary, change to backup, slides down, arm up
                    }
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


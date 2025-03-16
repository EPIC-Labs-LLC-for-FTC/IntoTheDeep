
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
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private Robot odyssey;

    private int pathState = 0;

    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));
    private final Pose specimenDropPose = new Pose(22, -2); //x=28.5, y=78
    private final Pose specimenBackPose = new Pose(27.5, 80);
    private final Pose firstPickupPose = new Pose(25, 116);
    private final Pose secondPickupPose = new Pose(25, 124, Math.toRadians(0));
    private final Pose thirdPickupPose = new Pose(20, 132, Math.toRadians(0));
    private final Pose depositPose = new Pose(13, 130, Math.toRadians(-42));
    private final Pose parkPose = new Pose(2, 15, Math.toRadians(270));

    private Path goToPreload, moveToPark;
    private PathChain scorePreload, grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

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
                .addPath(new BezierLine(new Point(specimenBackPose), new Point(firstPickupPose)))
                .setConstantHeadingInterpolation(0)
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(firstPickupPose), new Point(depositPose)))
                .setLinearHeadingInterpolation(firstPickupPose.getHeading(), depositPose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(depositPose), new Point(secondPickupPose)))
                .setLinearHeadingInterpolation(depositPose.getHeading(), secondPickupPose.getHeading())
                .addParametricCallback(0.60, () -> performSlideDown(odyssey))
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
            case 0: // strafe to submirsable, working
                follower.setMaxPower(0.6);
                follower.followPath(goToPreload);
                pathState = 2;
                break;

            case 1:
                follower.followPath(scorePreload);
                pathState = 7;
                break;

            case 2: // deposit specimen, working
                if (!follower.isBusy()) {
                    performSpecimenDropoffUnder(odyssey);
                    //sleep(500);
                    //follower.followPath(grabPickup1, true);
                    pathState = 7;
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    performSamplePickup(odyssey);
                    sleep(500);
                    performSlideUp(odyssey);
                    pathState = 4;
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(scorePickup1, true);
                    pathState = 5;
                    pathTimer.resetTimer();
                }
                break;

            case 5:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 5.0) {
                    //   performSlideDown(odyssey);
                    sleep(500);
                    follower.followPath(grabPickup2, true);
                    sleep(500);
                    pathState = 6;

                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    performSamplePickup(odyssey);
                    sleep(500);
                   // performSlideUp(odyssey);
                   // pathState = 7;
                }
                break;

            case 7: // end state
                if(!follower.isBusy()) {
                    sleep(1000);
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
        sleep(1000);
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


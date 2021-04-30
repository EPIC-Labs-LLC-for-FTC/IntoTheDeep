package org.firstinspires.ftc.teamcode.robots.UGBot;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.UGBot.utils.Constants;
import org.firstinspires.ftc.teamcode.robots.UGBot.vision.StackHeight;
import org.firstinspires.ftc.teamcode.robots.UGBot.vision.VisionProvider;
import org.firstinspires.ftc.teamcode.robots.UGBot.vision.VisionProviders;
import org.firstinspires.ftc.teamcode.statemachine.MineralStateProvider;
import org.firstinspires.ftc.teamcode.statemachine.Stage;
import org.firstinspires.ftc.teamcode.statemachine.StateMachine;
import org.firstinspires.ftc.teamcode.vision.Viewpoint;

/**
 * Class to keep all autonomous-related functions and state-machines in
 */
public class Autonomous {

    private PoseUG robot;
    private Telemetry telemetry;
    private Gamepad gamepad1;

    public static int sampleExtendMiddle = 2210;
    public static int sampleExtendLeft = 2200;
    public static int sampleExtendRight = 2200;
    public static boolean sampleContinue = false;

    // vision-related configuration
    public VisionProvider vp;
    public int visionProviderState = 0;
    public boolean visionProviderFinalized;
    public boolean enableTelemetry = true;
    public static final Class<? extends VisionProvider>[] visionProviders = VisionProviders.visionProviders;
    public static final Viewpoint viewpoint = Viewpoint.WEBCAM;
    public StackHeight height = StackHeight.HOLD_STATE;
    public int ugState = 0;
    private MineralStateProvider ugStateProvider = () -> ugState;

    // staging and timer variables
    public float autoDelay = 0;
    public Stage autoStage = new Stage();
    public Stage autoSetupStage = new Stage();

    // auto constants
    private static final double DRIVE_POWER = .65;
    private static final float TURN_TIME = 2;
    private static final float DUCKY_TIME = 1.0f;

    public Autonomous(PoseUG robot, Telemetry telemetry, Gamepad gamepad1) {
        this.robot = robot;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
    }

    public boolean sample() {
        // Turn on camera to see which is gold
        StackHeight gp = vp.detect();
        height = gp;
        // Hold state lets us know that we haven't finished looping through detection
        if (gp != StackHeight.NONE_FOUND) {
            switch (gp) {
                case ZERO:
                    ugState = 0;
                    break;
                case FOUR:
                    ugState = 2;
                    break;
                case ONE:
                case NONE_FOUND:
                default:
                    ugState = 1;
                    break;
            }
            telemetry.addData("Vision Detection", "Stack Height: %s", gp.toString());
//            vp.shutdownVision();
            return true;
        } else {
            telemetry.addData("Vision Detection", "NONE_FOUND (still looping through internally)");
            return false;
        }
    }

    public StateMachine simultaneousStateTest = getStateMachine(autoStage).addSimultaneousStates(() -> {
        robot.turret.rotateRight(0.25);
        return false;
    }, () -> {
        robot.driveMixerDiffSteer(0.25, 0.25);
        return false;
    }).build();

    private Constants.Position targetPose;

    public StateMachine AutoFull = getStateMachine(autoStage)
            //raise elbow to minimum distance for clear gripper extension
            .addSingleState(() -> robot.launcher.setElbowTargetAngle(5))
            //deploy intake
            .addState(()-> robot.deployIntake())
            //open then extend the gripper
            .addTimedState(1f, ()->robot.launcher.wobbleRelease(),()->robot.launcher.wobbleGrip())
            //simulate dangerzone
//            .addSingleState(()->robot.turret.setDangerModeActive(true))
            .addState(() -> robot.launcher.wobbleGrip()) //close gripper
//            .addTimedState(1f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
            .addState(() -> robot.launcher.setElbowTargetAngle(15))

//            .addTimedState(1f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))

            .addMineralState(ugStateProvider,
                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_A_1,true,  .8,.1),
                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_B_1, true, .8,.1),
                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_C_1,true,  .8,.1))

            .addMineralState(ugStateProvider,
                    ()-> robot.turret.setTurretAngle(270 + Constants.GRIPPER_HEADING_OFFSET),
                    ()-> robot.turret.setTurretAngle(90 + Constants.GRIPPER_HEADING_OFFSET),
                    ()-> robot.turret.setTurretAngle(270 + Constants.GRIPPER_HEADING_OFFSET))
            //release the wobble goal
            .addState(() -> robot.launcher.wobbleRelease())
            //spin up the flywheel
            .addSingleState(() -> robot.launcher.preSpinFlywheel(900))

            .addTimedState(.5f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))

            //todo: should gripper/elbow be elevated here? To not disturb wobble as it comes back?
//                .addSingleState(() -> robot.setTarget(Constants.Target.HIGH_GOAL))

            //launch preferred since we can't seem to launch while driving away from goal at speed
            .addState(()-> robot.driveToFieldPosition(Constants.Position.LAUNCH_PREFERRED,false,  .8,.1))
            .addTimedState(1.5f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))

            .addState(() -> robot.shootRingAuton(Constants.Target.HIGH_GOAL,3))
//
//
//            .addState(()-> robot.driveToFieldPosition(Constants.Position.WOBBLE_TWO_APPROACH,false, .4,.1))
//
//            .addSingleState(() -> robot.setTarget(Constants.Target.NONE))
//            .addSingleState(() -> robot.setAutoLaunchActive(false))
//
//            .addTimedState(1f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
//            .addState(()-> robot.driveToFieldPosition(Constants.Position.WOBBLE_TWO_GRAB,false, .4,.1))
//            .addTimedState(1f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
//            .addState(()-> robot.driveToFieldPosition(Constants.Position.WOBBLE_TWO_APPROACH,true, 1,.2))
//
//
//            .addMineralState(ugStateProvider,
//                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_A_2,true,  .8,.1),
//                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_B_2,true,  .8,.1),
//                    ()-> robot.driveToFieldPosition(Constants.Position.TARGET_C_2,true,  .8,.1))
//            .addTimedState(2f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))

            .addState(()-> robot.driveToFieldPosition(Constants.Position.NAVIGATE, true, 1,.1))
            .addTimedState(2f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
            .build();

    public StateMachine AutoTest = getStateMachine(autoStage)
            .addState(()-> robot.deployIntake())
            .addTimedState(2f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
            .addState(() -> robot.driveToFieldPosition(Constants.startingXOffset,Constants.startingYOffset+1.5,true,.6,.1))
            .addSingleState(() -> robot.setTarget(Constants.Target.HIGH_GOAL))
            .addSingleState(()->robot.setAutoLaunchActive(true))
            .addTimedState(7f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))
            .addState(() -> robot.driveToFieldPosition(Constants.startingXOffset,Constants.startingYOffset,false,.2,.1))

            .addTimedState(10f, () -> telemetry.addData("DELAY", "STARTED"), () -> telemetry.addData("DELAY", "DONE"))

            .build();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // Old Autonomous Routines //
    // //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private StateMachine.Builder getStateMachine(Stage stage) {
//        return
//                StateMachine.builder()
//                        .stateSwitchAction(() -> robot.launcher.setGripperPos(robot.launcher.toggleGripper()))
//                        .stateEndAction(() -> robot.turret.maintainHeadingTurret(false)).stage(stage);

            return StateMachine.builder()
                    .stateSwitchAction(() -> robot.returnTrue())
                    .stateEndAction(() -> {})
                    .stage(stage);

    }

    public void deinitVisionProvider() {
        telemetry.addData("Please wait", "Deinitializing vision");
        // telemetry.update();
        robot.ledSystem.setColor(LEDSystem.Color.CALM);
        vp.shutdownVision();
        vp = null;
        visionProviderFinalized = false;
    }

    public void initVisionProvider() {
        try {
            telemetry.addData("Please wait", "Initializing vision");
            // telemetry.update();
            robot.ledSystem.setColor(LEDSystem.Color.CALM);
            vp = visionProviders[visionProviderState].newInstance();
             vp.initializeVision(robot.hwMap, viewpoint, this.enableTelemetry);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        visionProviderFinalized = true;
    }

    public void initDummyVisionProvider() {
        try {
            telemetry.addData("Please wait", "Initializing vision");
            // telemetry.update();
            robot.ledSystem.setColor(LEDSystem.Color.CALM);
            vp = VisionProviders.defaultProvider.newInstance();
             vp.initializeVision(robot.hwMap, viewpoint, false);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        visionProviderFinalized = true;
    }
}

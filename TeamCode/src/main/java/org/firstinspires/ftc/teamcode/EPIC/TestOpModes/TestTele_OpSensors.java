package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.deprecatedcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.deprecatedcode.SparkFunOTOSDrive;

@TeleOp(name = "TestTele_OpSensors")
public class TestTele_OpSensors extends LinearOpMode {
    public static double ap = 0.03, ai = 0, ad = 0.0015, af = 0.065;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;
    private RevColorSensorV3 wristColor;
    private int allianceColor = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot and mecanum drive

        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(8.25, -63.85,
                Math.toRadians(90)));
        wristColor = hardwareMap.get(RevColorSensorV3.class,"wristColor");
        DistanceSensor ds = (DistanceSensor)wristColor;
        Pose2d initialPos = new Pose2d(8.25, -63.85, Math.toRadians(90));
        drive.setPoseEstimate(initialPos);

        Robot odyssey = new Robot(this, "Blue", true);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {
            idle();
        }

        Thread coord = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    telemetry.addData("X", drive.pose.position.x);
                    telemetry.addData("Y", drive.pose.position.y);
                    telemetry.addData("Heading (Deg)", Math.toDegrees(drive.pose.heading.toDouble()));
                    telemetry.addData("Color",wristColor.argb());
                    telemetry.addData("alpha",wristColor.alpha());
                    telemetry.addData("red",wristColor.red());
                    telemetry.addData("blue",wristColor.blue());
                    telemetry.addData("green",wristColor.green());
                    telemetry.addData("distance",wristColor.getDistance(DistanceUnit.INCH));
                    String aColor = "red";
                    if(allianceColor==2)
                        aColor = "blue";
                    telemetry.addData("alliance",aColor);

                    telemetry.addData("alliance No",allianceColor);
                    telemetry.addData("distance 2",ds.getDistance(DistanceUnit.INCH));
                    telemetry.update();
                }
            }
        };

        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    //odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                    //odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                }
            }
        };

        Thread motion = new Thread() {
            public void run(){
                double multiplier = 1;
                while (opModeIsActive()){
                    if(gamepad1.left_trigger>0){
                        multiplier=0.2;
                    }
                    else{
                        multiplier=1;
                    }
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    -gamepad1.left_stick_y*multiplier,
                                    -gamepad1.left_stick_x*multiplier
                            ),
                            -gamepad1.right_stick_x*multiplier
                    ));

                    drive.updatePoseEstimate();
                }
            }
        };
        waitForStart();
        coord.start();
        pidf.start();
        motion.start();
        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(1000);
        Actions.runBlocking(moveToColor(odyssey,wristColor));

        // Move forward to position (24, 0, 0)
//        moveToPosition(drive, new Pose2d(24, 0, 0));

        // Perform specimen pickup sequence
//        performSpecimenPickup(odyssey);

        // Perform specimen drop-off sequence
//        performSpecimenDropoff(odyssey);

        // Move backward to position (12, 0, 0)
//        moveToPosition(drive, new Pose2d(12, 0, 0));
    }

    /**
     * Moves the robot to a specific position using the MecanumDrive's trajectory capabilities.
     */
    private Action moveToColor(Robot odyssey,RevColorSensorV3 wristColor){
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while(opModeIsActive()){

                    if(gamepad1.a){
                        odyssey.odysseyClaw.move(ClawStates.OPEN);
                        sleep(500);
                    }
                    if(gamepad1.start){
                        if(allianceColor==1)
                            allianceColor=2;
                        else
                            allianceColor = 1;
                    }
                    if(wristColor.green()>150 && wristColor.red()>100){
                        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                        sleep(500);
                    }
                    else if(allianceColor == 1 && wristColor.red()>100){
                        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                        sleep(500);
                    }
                    else if(allianceColor == 2 && wristColor.blue()>150){
                        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                        sleep(500);
                    }
                }
                return false;
            }
        };
    }

    private void moveToPosition(MecanumDrive mecanumDrive, Pose2d targetPose) {
        /**   try {
         MecanumDrive.FollowTrajectoryAction trajectoryAction = mecanumDrive.new FollowTrajectoryAction(
         [FollowTrajectoryAction]mecanumDrive.actionBuilder(mecanumDrive.pose)
         .lineToX(20)
         .build()
         );

         // Run the trajectory
         while (opModeIsActive() && trajectoryAction.run()) {
         // Wait for trajectory to complete
         }
         } catch (Exception e) {
         // Handle trajectory execution errors
         }*/
    }

    /**
     * Handles the sequence for picking up a specimen.
     */
    private Action performSpecimenPickup(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
                sleep(500);

                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(1000);

                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
                sleep(500);
                return false;
            }
        };
    }

    /**
     * Handles the sequence for dropping off a specimen.
     */
    private Action performSpecimenDropoff(Robot odyssey) throws InterruptedException {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
                sleep(500);

                odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
                //odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_PICK);
                sleep(500);

                //odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                //sleep(500);

                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(500);

                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_LANDSCAPE);
                sleep(500);

                odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
                sleep(500);

                //odyssey.odysseyArm.move(ArmStates.INITIALIZED);
                //sleep(1000);
                return false;
            }
        };

    }
}

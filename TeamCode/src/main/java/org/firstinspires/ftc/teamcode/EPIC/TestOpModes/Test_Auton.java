package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Components.SpecimenClaw;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

@Config
@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {

    public static double ap = 0.02, ai = 0, ad = 0.0015, af = 0.08;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        Pose2d initialPose = new Pose2d(37.22, 13.55, Math.toRadians(90));
        Robot odyssey = new Robot(this, "Red");
        odyssey.initialize();
        //SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(-29.39, 48.73, 179.85));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        sleep(1000);
        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                    odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                }
            }
        };
        TrajectoryActionBuilder tb = drive.actionBuilder(initialPose)
                        .lineToY(-13.49)
                .waitSeconds(1);
        TrajectoryActionBuilder tb2 = drive.actionBuilder(initialPose)
                .lineToY(-16.49)
                .waitSeconds(1);
        waitForStart();
        pidf.start();
       //odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH);
        sleep(2000);
        Action tac1 = tb.build();
        Action tac2 = tb2.build();
        Actions.runBlocking(
                new SequentialAction(
                        tac1,
                        odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH,true),
                        tac2,
                        odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH,true),
                        odyssey.odysseySClaw.move(SpecimenClaw.SClawStates.OPEN, true),
                        tac1
                )
        );
        while(opModeIsActive()){

        }
        //x =
        //y =
        //w =
    }
}

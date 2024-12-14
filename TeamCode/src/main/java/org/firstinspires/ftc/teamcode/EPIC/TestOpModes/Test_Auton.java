package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {

    public static double ap = 0.02, ai = 0, ad = 0.0015, af = 0.08;
    public static double sp = 0.02, si = 0, sd = 0.001, sf = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        Robot odyssey = new Robot(this, "Red");
        odyssey.initialize();
        //SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(-29.39, 48.73, 179.85));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        sleep(1000);
        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                    //odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                }
            }
        };
        TrajectoryActionBuilder tb = drive.actionBuilder(initialPose)
                        .lineToY(28)
                .waitSeconds(1);
        TrajectoryActionBuilder tb2 = drive.actionBuilder(initialPose)
                .lineToY(0)
                .waitSeconds(1);

        Action tac1 = tb.build();
        Action tac2 = tb2.build();
        Action sleeper = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                sleep(500);
                return false;
            }
        };
        waitForStart();
        pidf.start();
       //odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH);
        sleep(2000);
        Actions.runBlocking(
                new SequentialAction(
                        tac1,
                        //odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH,true),
                        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP,true),
                        odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE,true),
                        sleeper,
                        odyssey.odysseyClaw.move(ClawStates.OPEN,true),
                        sleeper,
//                        tac2,
//                        odyssey.odysseySlider.slide(SliderStates.SPECIMEN_HIGH,true),
                   //     odyssey.odysseySClaw.move(SpecimenClaw.SClawStates.OPEN, true),
                        tac2
                )
        );
        while(opModeIsActive()){

        }
        //x =
        //y =
        //w =
    }
}

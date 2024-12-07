package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

@Autonomous(name = "Auton_BlueLeft")
public class Auton_BlueLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Blue");
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {

        }

        waitForStart();

//        odyssey.odysseyArm.move(ArmStates.LOWERED);
//        sleep(2000);
//        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
//        sleep(1500);
//        odyssey.odysseyClaw.move(ClawStates.OPEN);
//        sleep(1500);
//        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING);
//        sleep(1500);
//        odyssey.odysseyClaw.move(ClawStates.OPEN);
//        sleep(1500);
//        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
//        sleep(1500);
//        odyssey.odysseyArm.move(ArmStates.INITIALIZED);
//        sleep(500);

        double distance = 24;
        odyssey.odysseyWheels.encoderDrive(0.6,distance,distance,distance,distance,6);
        sleep(1000);
        odyssey.odysseyArm.move(ArmStates.LOWERED);
        sleep(1000);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
        distance = -12;
        odyssey.odysseyWheels.encoderDrive(0.6,distance,distance,distance,distance,6);
        sleep(1000);
        distance = 24;
        odyssey.odysseyWheels.strafeRight(0.6,distance,6);

        while (opModeIsActive()) {

        }
    }
}

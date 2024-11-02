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

        odyssey.odysseyArm.move(ArmStates.LOWERED,6);
        sleep(2000);
        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(1500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(1500);
        odyssey.odysseyWrist.setPos(WristStates.INITIALIZING);
        sleep(1500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(1500);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(1500);
        odyssey.odysseyArm.move(ArmStates.INITIALIZED,6);
        sleep(500);

        while (opModeIsActive()) {

        }
    }
}

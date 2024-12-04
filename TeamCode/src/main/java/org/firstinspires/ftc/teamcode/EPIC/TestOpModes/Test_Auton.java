package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;

@Config
@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;

        Robot odysseyRobot = new Robot(this, "Red");
        odysseyRobot.initialize();
        sleep(1000);

        waitForStart();
    }
}

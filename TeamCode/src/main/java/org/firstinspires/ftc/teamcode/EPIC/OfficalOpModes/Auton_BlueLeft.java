package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;

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

        while (opModeIsActive()) {

        }
    }
}

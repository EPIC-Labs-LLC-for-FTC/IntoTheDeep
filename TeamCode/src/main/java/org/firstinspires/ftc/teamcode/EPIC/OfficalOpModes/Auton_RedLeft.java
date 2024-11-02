package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;

@Autonomous(name = "Auton_RedLeft")
@Disabled
public class Auton_RedLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Red");
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {

        }

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}

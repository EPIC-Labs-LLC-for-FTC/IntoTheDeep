package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;

@Autonomous(name = "Auton_BlueRight")
public class Auton_BlueRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Blue");
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {

        }

        waitForStart();
        double distance = 30;
        odyssey.odysseyWheels.encoderDrive(0.6,distance,-distance,-distance,distance,6);
        while (opModeIsActive()) {

        }
    }
}

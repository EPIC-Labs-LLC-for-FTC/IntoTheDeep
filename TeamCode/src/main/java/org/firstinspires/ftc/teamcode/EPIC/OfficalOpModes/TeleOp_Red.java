package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;

@TeleOp(name = "TeleOp_Red")
public class TeleOp_Red extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Red");
        odyssey.initialize();

        while (opModeInInit()) {

        }

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}

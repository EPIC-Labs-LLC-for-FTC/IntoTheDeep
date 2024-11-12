package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider_PIDF;

@Config
@TeleOp(name = "TeleOp_PIDF")
public class Test_TeleOp_PIDF extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        Slider_PIDF slider = new Slider_PIDF(hardwareMap);
        arm.initialize();
        slider.initialize();

        while (opModeInInit()){

        }
        waitForStart();

        while (opModeIsActive()){

        }
    }
}

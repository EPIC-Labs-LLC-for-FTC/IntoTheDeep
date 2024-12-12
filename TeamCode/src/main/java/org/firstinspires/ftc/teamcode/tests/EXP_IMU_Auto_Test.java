package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;

import org.firstinspires.ftc.teamcode.components.Slides;


@Disabled

@Autonomous(name = "EXP_IMU_TEST")
public class EXP_IMU_Auto_Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {



        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.initialize();

        Slides slides = new Slides(hardwareMap);
        slides.setParent(this);
        slides.setTelemetry(this.telemetry);
        slides.initialize();

        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.initialize();

        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.initialize();

        while (opModeInInit()){

            wheels.initialize();

            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.update();

        }

        waitForStart();
        while (opModeIsActive()){

            wheels.driveStraight(1,10,0);
            sleep(500);
            wheels.driveStraight(1,-10,0);
            sleep(500);
            wheels.turnToHeading(1,90);
            sleep(500);
            wheels.turnToHeading(1,0);
            sleep(500);
            wheels.strafe(1,10,0);
            sleep(500);
            wheels.strafe(1,-10,0);


            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.update();
        }
    }
}

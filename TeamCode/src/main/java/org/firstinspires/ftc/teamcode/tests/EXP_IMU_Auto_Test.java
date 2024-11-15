package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;

@Autonomous(name = "EXP_IMU_TEST")
public class EXP_IMU_Auto_Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.initialize();

        while (opModeInInit()){

            wheels.initialize();

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
            sleep(500);
            wheels.strafeDiagonal("northeast", 1,10,0);
            sleep(500);
            wheels.strafeDiagonal("southwest", 1,10,0);
            sleep(500);
            wheels.strafeDiagonal("northwest", 1,10,0);
            sleep(500);
            wheels.strafeDiagonal("southeast", 1,10,0);

            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.update();
        }
    }
}

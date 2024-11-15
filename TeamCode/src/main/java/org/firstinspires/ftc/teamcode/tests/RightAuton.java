package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;

@Autonomous(name = "RightAuton")
public class RightAuton extends LinearOpMode {

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
        claw.close();

        while (opModeInInit()){

            wheels.initialize();


        }

        waitForStart();
        //while (opModeIsActive()){

            double distance = 30;
            arm.angle();
            //wheels.encoderDrive(0.6,distance,-distance,-distance,distance,6);
            distance = 13;
            //wheels.encoderDrive(0.6,distance,distance,distance,distance,2);
            slides.moveToPosition(-200);
            sleep(2000);
            slides.moveToPosition(-100);
            sleep(1000);
            claw.open();
            sleep(1000);
            arm.start();
            distance=-13;
        //wheels.encoderDrive(0.6,distance,distance,distance,distance,6);
        distance = 30;
        //wheels.encoderDrive(0.6,distance,-distance,-distance,distance,6);

            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.update();
        //}
    }
}

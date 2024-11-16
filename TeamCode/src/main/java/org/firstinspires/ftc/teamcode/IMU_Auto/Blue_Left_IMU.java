package org.firstinspires.ftc.teamcode.IMU_Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;

@Autonomous(name = "Blue_Left_IMU")
public class Blue_Left_IMU extends LinearOpMode {

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

            arm.initialize();
            claw.initialize();
            wheels.initialize();
            slides.initialize();

            wheels.imu.resetYaw();

            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.update();

        }

        waitForStart();
        while (opModeIsActive()){
            if (opModeIsActive()){

                //Score1
                arm.angle();
                wheels.driveStraight(1,20,0);
                sleep(500);

                slides.lBucket();
                sleep(500);

                claw.open();
                sleep(500);

                slides.start();
                wheels.driveStraight(1,-5,0);
                sleep(500);

                //Score2
                arm.Horizontal();

                wheels.strafe(1,20,0);
                claw.close();
                sleep(500);

                arm.angle();
                wheels.strafe(1,-20,0);
                slides.lBucket();
                sleep(500);

                wheels.driveStraight(1,5,0);
                claw.open();
                sleep(500);

                //Score3
                wheels.strafe(1,5,0);
                arm.Horizontal();
                wheels.strafe(1,15,0);
                claw.close();
                arm.angle();
                sleep(500);

                wheels.strafe(1,-15,0);
                slides.lBucket();
                sleep(500);
                wheels.strafe(1,-5,0);
                claw.open();

                //Score4
                wheels.driveStraight(1,-5,0);
                sleep(500);

                slides.start();
                arm.Horizontal();
                sleep(500);

                wheels.driveStraight(1,10,0);
                wheels.strafe(1,20,0);
                claw.close();
                arm.angle();
                wheels.strafe(1,-20,0);
                wheels.driveStraight(1,-10,0);
                sleep(500);
                slides.lBucket();
                claw.open();
                sleep(500);

                slides.start();
                claw.close();

                //Park
                wheels.strafe(1,30,0);
                wheels.turnToHeading(1,180);
                wheels.driveStraight(1,10,180);

            }
            telemetry.addData("Robot Heading = %4.0f", wheels.getHeading());
            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
            telemetry.addData("Arm1Position", arm.arm1.getPosition());
            telemetry.addData("Arm2Position", arm.arm2.getPosition());
            telemetry.addData("Claw1", claw.claw1.getPosition());
            telemetry.addData("Claw2", claw.claw2.getPosition());
            telemetry.update();
        }
    }
}

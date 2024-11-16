package org.firstinspires.ftc.teamcode.IMU_Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;

@Autonomous(name = "Red_Right_IMU")
public class Red_Right_IMU extends LinearOpMode {

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
                wheels.driveStraight(1,5,0);
                claw.open();

                //Score2
                wheels.strafe(1,-30,0);
                wheels.driveStraight(1,5,0);
                wheels.strafe(1,30,0);

                //Score3
                wheels.strafe(1,-30,0);
                wheels.driveStraight(1,5,0);
                wheels.strafe(1,30,0);

                //Score4
                wheels.strafe(1,-30,0);
                wheels.driveStraight(1,5,0);
                wheels.strafe(1,30,0);

                //Park
                wheels.strafe(1,40,0);
                wheels.driveStraight(1,10,0);
                wheels.turnToHeading(1,180);
                wheels.driveStraight(1,20,180);

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

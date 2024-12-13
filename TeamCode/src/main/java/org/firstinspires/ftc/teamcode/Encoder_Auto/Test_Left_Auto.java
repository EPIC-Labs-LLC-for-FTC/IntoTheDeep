package org.firstinspires.ftc.teamcode.Encoder_Auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RRsetup.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;

@Autonomous(name = "Test_Left_Auto")
public class Test_Left_Auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

//        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
//        wheels.telemetry = telemetry;
//        wheels.parent = this;
//        wheels.initialize();

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
        double ldistance = 0;
        if (opModeIsActive()) {
            waitForStart();

            //Score1
            arm.angle();
            ldistance = 24;
            wheels.encoderDrive(1, ldistance, -ldistance, -ldistance, ldistance, 2);
            ldistance = 8;
            wheels.encoderDrive(1, -ldistance, -ldistance, ldistance, ldistance, 2);
            ldistance = 22;
            wheels.encoderDrive(1, ldistance, ldistance, ldistance, ldistance, 2);
            sleep(500);
            slides.lBucket();
            sleep(2000);
            claw.open();
            sleep(1000);
            ldistance = -11;
            wheels.encoderDrive(0.2, ldistance, ldistance, ldistance, ldistance, 2);
            slides.start();

            //Park
            ldistance = 25;
            wheels.encoderDrive(0.4, ldistance, ldistance, -ldistance, -ldistance, 2);

            ldistance = 16;
            wheels.encoderDrive(1, ldistance, ldistance, ldistance, ldistance, 2);
            arm.start();

            ldistance = 16;
            wheels.encoderDrive(1, ldistance, ldistance, -ldistance, -ldistance, 2);

        }
    }
}

package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;
import org.firstinspires.ftc.teamcode.components.Wrist;

@Config
@TeleOp(name = "Expedition_Test1")
public class  Expedition_Test1 extends LinearOpMode {

    public FtcDashboard dashboard = FtcDashboard.getInstance();

    public ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        while (opModeInInit()){
            
            wheels.initialize();

        }

        waitForStart();
        while (opModeIsActive()){

            double movement = gamepad1.left_stick_y;
            double rotation = -gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            boolean precision = gamepad1.right_bumper;

            wheels.driverControl(precision, movement, rotation, strafe);

            slides.slideControl(gamepad2.left_stick_y);

            if(gamepad2.dpad_up){

                arm.armBaseUp();

            } else if (gamepad2.dpad_down) {

                arm.armBaseDown();

            } else if (gamepad2.y) {

                wrist.plus1();

            } else if (gamepad2.a) {

                wrist.minus1();

            } else if (gamepad2.b) {

                wrist.plus2();

            } else if (gamepad2.x) {

                wrist.minus2();

            } else if (gamepad2.right_bumper) {

                claw.open1();

            } else if (gamepad2.left_bumper) {

                claw.close1();

            } else if (gamepad2.right_trigger > 0.2) {

                claw.open2();

            } else if (gamepad2.left_trigger > 0.2) {

                claw.close2();

            }

            if (gamepad1.dpad_up) {

                arm.armBase1.setPosition(0);
                arm.armBase2.setPosition(1);

            } else if (gamepad1.dpad_down) {

                arm.armBase1.setPosition(1);
                arm.armBase2.setPosition(0);

            } else if (gamepad1.dpad_right) {

                wrist.wrist1.setPosition(0.5);

            } else if (gamepad1.dpad_left) {

                wrist.wrist2.setPosition(0.5);

            } else if (gamepad1.right_bumper) {

                claw.claw1.setPosition(0);
                claw.claw2.setPosition(1);

            } else if (gamepad1.left_bumper) {

                claw.claw1.setPosition(1);
                claw.claw2.setPosition(0);

            }

            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
            
            telemetry.addData("ArmBase1Position", arm.armBase1.getPosition());
            telemetry.addData("ArmBase2Position", arm.armBase2.getPosition());

            telemetry.addData("Wrist1Position", wrist.wrist1.getPosition());
            telemetry.addData("Wrist2Position", wrist.wrist2.getPosition());
            
            telemetry.addData("Claw1", claw.claw1.getPosition());
            telemetry.addData("Claw2", claw.claw2.getPosition());

            telemetry.update();
        }
    }
}
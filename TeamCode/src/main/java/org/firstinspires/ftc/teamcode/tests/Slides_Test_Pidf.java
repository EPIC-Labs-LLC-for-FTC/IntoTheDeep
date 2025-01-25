package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;
import org.firstinspires.ftc.teamcode.components.Wrist;

@TeleOp(name = "Slides_Test_Pidf")
public class Slides_Test_Pidf extends LinearOpMode {

    public ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        slides.setParent(this);
        slides.setTelemetry(this.telemetry);
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        while (opModeInInit()){
            
            wheels.initialize();
            arm.armRest2();
            wrist.rest();
            claw.close();

        }

        waitForStart();
        while (opModeIsActive()){

            double movement = gamepad1.left_stick_y;
            double rotation = -gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            boolean precision = gamepad1.right_bumper;

            wheels.driverControl(precision, movement, rotation, strafe);

            slides.slideControl(gamepad2.left_stick_y);

            slides.moveTo();

            if (gamepad1.right_bumper){
                slides.slidesUp();
            } else if (gamepad1.left_bumper) {
                slides.slidesDown();
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
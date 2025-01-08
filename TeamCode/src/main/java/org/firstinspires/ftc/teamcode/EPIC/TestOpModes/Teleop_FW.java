package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@TeleOp(name = "Teleop_FW")
public class Teleop_FW extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


//        colorSensor = hardwareMap.get(ColorSensor.class,"colorSensor");


        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.initialize();

        Slider slides = new Slider(hardwareMap);
        slides.setParent(this);
        slides.setTelemetry(this.telemetry);
        slides.initialize();

        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.initialize();

        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(this.telemetry);
        wrist.initialize();

        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.initialize();

        while (opModeInInit()){

            arm.initialize();
            claw.initialize();
            wheels.initialize();
            slides.initialize();

        }

        waitForStart();

        while (opModeIsActive()){

            double movement = gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            boolean precision = gamepad1.left_stick_button;

            wheels.driverControl(precision, movement, rotation, strafe);

            if (gamepad2.right_trigger > 0.2) {
                slides.slideManualDown();
            } else if (gamepad2.left_trigger > 0.2) {
                slides.slideManualUp();
            }

            if (gamepad2.right_bumper) {
                arm.armManualDown();
            } else if (gamepad2.left_bumper) {
                arm.armManualUp();
            }


            if (gamepad1.left_bumper) {
                claw.close();
            } else if (gamepad1.right_bumper) {
                claw.open();
            }

            if (gamepad1.left_trigger > 0.1) {
                wrist.moveUp();
            } else if (gamepad1.right_trigger > 0.1) {
                wrist.moveDown();
            }



            telemetry.update();
        }
    }

}

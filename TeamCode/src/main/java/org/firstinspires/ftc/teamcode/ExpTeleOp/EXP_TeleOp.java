package org.firstinspires.ftc.teamcode.ExpTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;
import org.firstinspires.ftc.teamcode.components.Wrist;

@TeleOp(name = "EXP_TeleOp")
public class EXP_TeleOp extends LinearOpMode {

    public ColorSensor colorSensor;

    public boolean rightBumperToggle = false;
    public boolean rightBumperPressed = false;

    public boolean leftBumperToggle = false;
    public boolean leftBumperPressed = false;

    public boolean leftBumperToggle1 = false;
    public boolean leftBumperPressed1 = false;

    public int target =0;

    @Override
    public void runOpMode() throws InterruptedException {

        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;

        Slides slides = new Slides(hardwareMap);
        slides.setParent(this);
        slides.setTelemetry(this.telemetry);

        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);

        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(this.telemetry);

        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        while (opModeInInit()){

            arm.armRest();
            claw.open();
            wrist.rest();
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

            if (gamepad2.right_bumper && !rightBumperPressed) {
                rightBumperToggle = !rightBumperToggle;
                if (rightBumperToggle) {
                    arm.armRest();
                    wrist.rest();
                } else {
                    arm.armPick();
                    wrist.wristPick();
                }
                rightBumperPressed = true;
            } else if (!gamepad2.right_bumper) {
                rightBumperPressed = false;
            }

            if (gamepad2.right_trigger > 0.2) {
                claw.open();
            } else {
                claw.close();
            }

            if (gamepad2.left_bumper && !leftBumperPressed) {
                leftBumperToggle = !leftBumperToggle;
                if (leftBumperToggle) {
                    arm.specimenPick();
                    wrist.specimenPick();
                } else {
                    arm.specimenReadyDrop();
                    wrist.specimenDrop();
                }
                leftBumperPressed = true;
            } else if (!gamepad2.left_bumper) {
                leftBumperPressed = false;
            }

            if (gamepad2.b) {
                arm.armDrop();
                wrist.wristDrop();
            }

            if (gamepad2.y) {
                arm.specimenDrop();
            }

            if (gamepad2.a) {
                arm.armRest2();
                wrist.rest();
            }

            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());

            telemetry.addData("SlideTarget", target);

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

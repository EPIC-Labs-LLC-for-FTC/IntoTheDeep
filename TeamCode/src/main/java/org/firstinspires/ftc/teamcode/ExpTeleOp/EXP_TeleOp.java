package org.firstinspires.ftc.teamcode.ExpTeleOp;

import android.graphics.Color;

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

            arm.armDrop();
            claw.close();
            wrist.wristDrop();
            slides.initialize();
            wheels.initialize();

        }

        waitForStart();

        int colorMode = 1;
        boolean scanningMode = false;

        while (opModeIsActive()){

            double movement = gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            boolean precision = gamepad1.right_bumper;

            wheels.driverControl(precision, movement, rotation, strafe);

            slides.slideControl(-gamepad2.left_stick_y);

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

//            if (gamepad2.right_trigger > 0.2) {
//                claw.open();
//            } else {
//                claw.close();
//            }

            if (gamepad2.left_bumper && !leftBumperPressed) {
                leftBumperToggle = !leftBumperToggle;
                if (leftBumperToggle) {
                    arm.specimenPick();
                    wrist.specimenPick();
                } else {
                    arm.specimenDrop();
                    wrist.specimenReadyDrop();
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
                wrist.specimenDrop();
            }

            if (gamepad1.a) {
                colorMode = 1; // Look for red and yellow
            } else if (gamepad1.b) {
                colorMode = 2; // Look for blue and yellow
            }

            telemetry.addData("Target Mode", colorMode == 1 ? "Red+Yellow" : "Blue+Yellow");

            if (gamepad2.left_trigger > 0.2) {
                if (!scanningMode) {
                    scanningMode = true;
                    claw.open();
                    telemetry.addData("Claw", "Opened for scanning");
                }

                float hsvValues[] = new float[3];
                Color.RGBToHSV(colorSensor.red() * 8,
                        colorSensor.green() * 8,
                        colorSensor.blue() * 8,
                        hsvValues);

                boolean targetDetected = false;
                if (colorMode == 1) { // Target: Red OR Yellow
                    // Red: hue between 0-30 or 330-360; Yellow: hue between 30-90
                    if (hsvValues[0] <= 90 || hsvValues[0] >= 330) {
                        targetDetected = true;
                    }
                } else if (colorMode == 2) { // Target: Blue OR Yellow
                    // Blue: hue between 180-270; Yellow: hue between 30-90
                    if ((hsvValues[0] >= 30 && hsvValues[0] <= 90) ||
                            (hsvValues[0] >= 180 && hsvValues[0] <= 270)) {
                        targetDetected = true;
                    }
                }

                telemetry.addData("HSV", "[%.1f, %.2f, %.2f]", hsvValues[0], hsvValues[1], hsvValues[2]);

                if (targetDetected) {
                    telemetry.addData("Color Sensor", "Target color detected!");
                    claw.close();
                    arm.armRest();
                    wrist.rest();
                    scanningMode = false;
                }
            } else {
                scanningMode = false;
                if (gamepad2.right_trigger > 0.2) {
                    claw.open();
                } else {
                    claw.close();
                }
            }

            float hsvValues[] = new float[3];
            Color.RGBToHSV(colorSensor.red() * 8,
                    colorSensor.green() * 8,
                    colorSensor.blue() * 8,
                    hsvValues);

            String sensorColor;
            if (hsvValues[0] <= 30 || hsvValues[0] >= 330) {
                sensorColor = "Red";
            } else if (hsvValues[0] > 30 && hsvValues[0] <= 90) {
                sensorColor = "Yellow";
            } else if (hsvValues[0] >= 180 && hsvValues[0] <= 270) {
                sensorColor = "Blue";
            } else {
                sensorColor = "None";
            }

            telemetry.addData("Sensor Reading", "Detected: " + sensorColor);
            telemetry.addData("HSV", "[%.1f, %.2f, %.2f]", hsvValues[0], hsvValues[1], hsvValues[2]);


            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());

            telemetry.addData("SlideTarget", target);

            telemetry.addData("ArmBase1Position", arm.armBase1.getPosition());
            telemetry.addData("ArmBase2Position", arm.armBase2.getPosition());

            telemetry.addData("Wrist1Position", wrist.wrist1.getPosition());
            telemetry.addData("Wrist2Position", wrist.wrist2.getPosition());

            telemetry.addData("Claw", claw.claw.getPosition());

            telemetry.update();
        }
    }
}

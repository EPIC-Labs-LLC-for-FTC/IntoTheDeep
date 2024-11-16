package org.firstinspires.ftc.teamcode.ExpTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.components.Slides;

@TeleOp(name = "Expedition_TeleOp1")
public class Expedition_TeleOp1 extends LinearOpMode {

//    public ColorSensor colorSensor = null;

    @Override
    public void runOpMode() throws InterruptedException {

//        colorSensor = hardwareMap.get(ColorSensor.class,"colorSensor");

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

        boolean clawOpen = false;
        String mode = "red_yellow";

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
            boolean precision = gamepad1.right_bumper;

            wheels.driverControl(precision, movement, rotation, strafe);

            slides.slideControl(-gamepad2.left_stick_y);

            //Added

//            if (gamepad1.a) {
//                mode = "red_yellow";
//            } else if (gamepad1.b) {
//                mode = "blue_yellow";
//            }
//
//            if (gamepad2.right_trigger > 0.4) {
//                claw.open();
//                clawOpen = true;
//            } else if (clawOpen && colorSensor != null) {
//                int red = colorSensor.red();
//                int green = colorSensor.green();
//                int blue = colorSensor.blue();
//
//                if (mode.equals("red_yellow")) {
//                    if (red > green && red > blue || (red > blue && green > blue)) {
//                        claw.close();
//                        clawOpen = false;
//                        arm.angle();
//                    }
//                } else if (mode.equals("blue_yellow")) {
//                    if (blue > red && blue > green || (red > blue && green > red)) {
//                        claw.close();
//                        clawOpen = false;
//                        arm.angle();
//                    }
//                }
//            } else {
//                claw.close();
//                clawOpen = false;
//            }

            //End of added

            if (gamepad1.dpad_up){
                slides.lBar();
            }

            if (gamepad2.right_trigger > 0.4){
                claw.open();
            } else {
                claw.close();
            }

            if (gamepad2.y){
                arm.start();
            } else if (gamepad2.b) {
                arm.angle();
            } else if (gamepad2.a) {
                arm.Horizontal();
            }

            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
            telemetry.addData("Arm1Position", arm.arm1.getPosition());
            telemetry.addData("Arm2Position", arm.arm2.getPosition());
            telemetry.addData("Claw1", claw.claw1.getPosition());
            telemetry.addData("Claw2", claw.claw2.getPosition());
//            telemetry.addData("Color (R, G, B)", "(%d, %d, %d)", colorSensor.red(), colorSensor.green(), colorSensor.blue());
            telemetry.update();
        }
    }
}
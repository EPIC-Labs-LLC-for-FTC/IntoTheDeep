package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slides;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

@TeleOp(name = "Expedition_Test1")
public class Expedition_Test1 extends LinearOpMode {

    double lefty;
    double leftx;
    double righty;
    double rightx;

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
            wrist.initialize();
            claw.initialize();
            wheels.initialize();
            slides.initialize();

        }

        waitForStart();
        while (opModeIsActive()){

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;

            wheels.move(lefty,righty,leftx,rightx);

            if (gamepad2.dpad_down) {
                arm.putDown();
            } else if (gamepad2.dpad_left) {
                arm.liftUp();
            } else if (gamepad2.dpad_up) {
                claw.open1();
            } else if (gamepad2.dpad_right) {
                claw.close1();
            } else if (gamepad2.a) {
                wrist.plus();
            } else if (gamepad2.b) {
                wrist.minus();
            }

            if (gamepad2.right_trigger < 0.5){

                slides.slide1.setPower(1);
                slides.slide2.setPower(1);

            } else if (gamepad2.left_trigger < 0.5) {

                slides.slide1.setPower(-1);
                slides.slide2.setPower(-1);

            } else {
                
                slides.slide1.setPower(0);
                slides.slide2.setPower(0);

            }

            telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
            telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
            telemetry.addData("Arm1Position", arm.arm1.getPosition());
            telemetry.addData("Arm2Position", arm.arm2.getPosition());
            telemetry.addData("WristPosition", wrist.wrist.getPosition());
            telemetry.addData("Claw1", claw.claw1.getPosition());
            telemetry.addData("Claw2", claw.claw2.getPosition());
            telemetry.update();
        }
    }
}

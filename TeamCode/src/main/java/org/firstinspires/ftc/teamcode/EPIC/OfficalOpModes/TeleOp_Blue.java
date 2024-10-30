package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

@TeleOp(name = "TeleOp_Blue")
public class TeleOp_Blue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Blue");
        odyssey.initialize();

        Thread dt = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    double lefty = gamepad1.left_stick_y;
                    double leftx = gamepad1.left_stick_x;
                    double righty = gamepad1.right_stick_y;
                    double rightx = gamepad1.right_stick_x;
                    odyssey.odysseyWheels.move(lefty, righty, leftx, rightx);
                }
            }
        };

        while (opModeInInit()) {

        }

        waitForStart();

        dt.start();

        while (opModeIsActive()) {
            if (gamepad2.a) {
                odyssey.odysseyArm.move(ArmStates.LOWERED);
                sleep(5000);
            } else if (gamepad2.dpad_left) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(500);
            } else if (gamepad2.dpad_right) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_LANDSCAPE);
                sleep(500);
            } else if (gamepad2.b) {
                odyssey.odysseyArm.move(ArmStates.DEPOSITING);
                sleep(5000);
            } else if (gamepad2.dpad_up) {
                odyssey.odysseyClaw.move(ClawStates.OPEN);
            } else if (gamepad2.x) {
                odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET, 10);
                sleep(1000);
                odyssey.odysseySlider.slide(SliderStates.RETRACTED, 10);
            } else if (gamepad2.y) {
                odyssey.odysseySlider.slide(SliderStates.LOW_HANG, 5);
                sleep(5000);
                odyssey.odysseySlider.slide(SliderStates.RETRACTED, 12);
            }

            telemetry.update();
        }
    }
}

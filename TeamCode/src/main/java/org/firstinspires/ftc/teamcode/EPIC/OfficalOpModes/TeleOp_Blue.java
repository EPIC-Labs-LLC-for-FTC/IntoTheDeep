package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

@TeleOp(name = "TeleOp_Blue")
public class TeleOp_Blue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Blue");
        odyssey.initialize();
        sleep(1000);

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

        Thread slider = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    if (gamepad1.dpad_up && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.LOW_HANG_START, 7);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.dpad_down && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.LOW_HANG, 7);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.left_bumper && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET, 7);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.right_bumper && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.RETRACTED, 7);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if ((gamepad1.left_bumper || gamepad1.right_bumper) && (odyssey.odysseyArm.stateArm == ArmStates.DEPOSITING)) {
                        telemetry.addData("Slider Thread", "Arm is in the way! Please move it!");
                    }
                }
            }
        };

        while (opModeInInit()) {

        }

        waitForStart();

        dt.start();
        slider.start();

        while (opModeIsActive()) {
            if (gamepad2.x) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(100);
            } else if (gamepad2.y) {
                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(100);
            } else if (gamepad2.a) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_LANDSCAPE);
                sleep(100);
            } else if (gamepad2.dpad_up) {
                odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                sleep(100);
            } else if (gamepad2.dpad_down) {
                odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
                sleep(100);
            } else if (gamepad2.dpad_left) {
                odyssey.odysseyArm.setAdjustments(10);
            } else if (gamepad2.dpad_right) {
                odyssey.odysseyArm.setAdjustments(-5);
            } else if (gamepad2.left_bumper) {
                odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT, 6);
                sleep(2000);
            } else if (gamepad2.right_bumper) {
                odyssey.odysseyArm.move(ArmStates.LOWERED, 6);
                sleep(2000);
            } else if (gamepad1.a) {
                odyssey.odysseyWheels.setPower(0.2);
            } else if (gamepad1.b) {
                odyssey.odysseyWheels.setPower(0.6);
            } else if (gamepad1.x) {
                odyssey.odysseyWheels.setPower(1);
            }

            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

@TeleOp(name = "Driver Tryout TeleOp")
public class TeleOp_Tryout extends LinearOpMode {
    public static double ap = 0.02, ai = 0, ad = 0.001, af = 0.08;
    public static double sp = 0.02, si = 0, sd = 0.0018, sf = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Red");

        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    odyssey.odysseyArm.runPIDF(ap, ai, ad, af);
                    odyssey.odysseySlider.runPIDF(sp, si, sd, sf);
                }
            }
        };

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

        Thread gamepadOne = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    if (gamepad1.dpad_up && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.LOW_HANG_START);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.dpad_down && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.LOW_HANG);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.left_bumper && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.HIGH_BUCKET);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (gamepad1.right_bumper && (odyssey.odysseyArm.stateArm != ArmStates.DEPOSITING)) {
                        odyssey.odysseySlider.slide(SliderStates.RETRACTED);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if ((gamepad1.left_bumper || gamepad1.right_bumper || gamepad1.dpad_down || gamepad1.dpad_up) && (odyssey.odysseyArm.stateArm == ArmStates.DEPOSITING)) {
                        telemetry.addData("Slider Thread", "Arm is in the way! Please move it!");
                    } else if (gamepad1.a) {
                        odyssey.odysseyWheels.setPower(0.2);
                    } else if (gamepad1.b) {
                        odyssey.odysseyWheels.setPower(0.6);
                    } else if (gamepad1.x) {
                        odyssey.odysseyWheels.setPower(1);
                    }
                }
            }
        };

        while (opModeInInit()) {

        }

        waitForStart();

        pidf.start();
        dt.start();
        gamepadOne.start();

        while (opModeIsActive()) {
            if (gamepad2.x) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
                sleep(50);
            } else if (gamepad2.y) {
                odyssey.odysseyClaw.move(ClawStates.OPEN);
                sleep(50);
            } else if (gamepad2.a) {
                odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_LANDSCAPE);
                sleep(50);
            } else if (gamepad2.dpad_up) {
                odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                sleep(50);
            } else if (gamepad2.dpad_down) {
                odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
                sleep(50);
            } else if (gamepad2.left_bumper) {
                odyssey.odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
            } else if (gamepad2.right_bumper) {
                odyssey.odysseyArm.move(ArmStates.LOWERED);
            }

            telemetry.update();
        }
    }
}

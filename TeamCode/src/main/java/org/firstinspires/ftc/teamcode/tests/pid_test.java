package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class pid_test extends LinearOpMode {

    public FtcDashboard dashboard = FtcDashboard.getInstance();
    public PIDController controller;

    public static double p = 0.005, i = 0.0, d = 0.0; // Adjust initial values as needed
    public static double f = 0.0; // Feedforward gain
    public static int targetPosition = 0; // Target position in ticks
    private final double tick_in_degrees = 537.7 / 360; // Encoder ticks per degree

    public DcMotorEx slide1 = null;
    public DcMotorEx slide2 = null;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        slide2.setDirection(DcMotorSimple.Direction.REVERSE);
        slide1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slide1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slide2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            // Update PID values from Dashboard
            controller.setPID(p, i, d);

            int currentPos1 = slide1.getCurrentPosition();
            int currentPos2 = slide2.getCurrentPosition();

            // Calculate PID output
            double pidOutput1 = controller.calculate(currentPos1, targetPosition);
            double pidOutput2 = controller.calculate(currentPos2, targetPosition);

            // Feedforward to help hold position and move smoothly
            double feedForward = Math.cos(Math.toRadians(targetPosition / tick_in_degrees)) * f;

            // Apply combined PID and feedforward power
            double power1 = pidOutput1 + feedForward;
            double power2 = pidOutput2 + feedForward;

            slide1.setPower(power1);
            slide2.setPower(power2);

            // Telemetry for debugging
            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("Current Position 1", currentPos1);
            telemetry.addData("Current Position 2", currentPos2);
            telemetry.addData("Power 1", power1);
            telemetry.addData("Power 2", power2);
            telemetry.update();

            if (gamepad2.dpad_up) {
                targetPosition += 10; // Increase target
            } else if (gamepad2.dpad_down) {
                targetPosition -= 10; // Decrease target
            }

            if (gamepad2.y) {
                resetEncoders();
            }
        }
    }

    private void resetEncoders() {
        slide1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slide1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slide2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        targetPosition = 0;
    }
}

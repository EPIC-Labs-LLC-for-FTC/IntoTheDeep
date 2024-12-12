package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.roadrunner.Action;
import java.util.ArrayList;
import java.util.List;

@Config
@TeleOp
public class expslides extends LinearOpMode {

    public FtcDashboard dashboard = FtcDashboard.getInstance();
    public PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    public static int target1 = -1000;
    private final double tick_in_degrees = 537.7/360;

    public DcMotorEx slide1 = null;
    public DcMotorEx slide2 = null;

    private List<Action> runningActions = new ArrayList<>();

    public void actions() {
        TelemetryPacket packet = new TelemetryPacket();

        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;

        dashboard.sendTelemetryPacket(packet);
    }


    public void slidePManual() {

        if (gamepad2.right_trigger > 0.2) {
            slide1.setPower(0.2);
            slide2.setPower(0.2);
        } else if (gamepad2.left_trigger > 0.2) {
            slide1.setPower(-0.2);
            slide2.setPower(-0.2);

        } else {
            slide1.setPower(0);
            slide2.setPower(0);
        }
    }

    public void slideManual() {
        if (gamepad2.right_bumper) {
            target1 = target1 + 10;
        } else if (gamepad2.left_bumper) {
            target1 = target1 - 10;
        }
    }

    public void reset() {
        if (gamepad2.y) {
            slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void slideAction() {

        if (gamepad2.a) {
            runningActions.add(new ParallelAction(new InstantAction(() -> slide1.getCurrentPosition())));
            runningActions.add(new ParallelAction(new InstantAction(() -> slide2.getCurrentPosition())));
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p, i, d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        slide2.setDirection(DcMotorSimple.Direction.REVERSE);

        target1 = 0;

        waitForStart();
        sleep(100);

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                controller.setPID(p, i, d);

                int slidePos = slide1.getCurrentPosition();
                int slidePos2 = slide2.getCurrentPosition();

                double pid1 = controller.calculate(slidePos, target1);
                double pid2 = controller.calculate(slidePos, target1);

                double ff1 = Math.cos(Math.toRadians(target1 / tick_in_degrees)) * f;

                double power1 = pid1 + ff1;
                double power2 = pid2 + ff1;


                slide1.setPower(power1);
                slide2.setPower(power2);

                telemetry.addData("Slide Position", slidePos);
                telemetry.addData("Slide Position", slidePos2);
                telemetry.addData("Slide Target", target1);
                telemetry.addData("Slide Power", power1);
                telemetry.addData("Slide Power", power2);

                slideManual();
                slidePManual();
                actions();
                reset();
                slideAction();
            }
        }
    }
}

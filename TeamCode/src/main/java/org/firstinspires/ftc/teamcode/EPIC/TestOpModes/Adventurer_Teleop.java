package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.List;

@Config
@TeleOp(name = "Adventurer_Teleop")
public class Adventurer_Teleop extends LinearOpMode {

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    private PIDController controller;

    public static double p1 = 0.02, i1 = 0, d1 = 0.00015;
    public static double f1 = -0.2;
    public static int target1 = 0;
    private final double tick_in_degrees1 = 2786.2/360;

    public DcMotorEx frontLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx backLeft = null;
    public DcMotorEx backRight = null;
    public DcMotorEx armLeft = null;
    public DcMotorEx armRight = null;
    public DcMotorEx slideLeft = null;
    public DcMotorEx slideRight = null;
    public Servo clawRight = null;
    public Servo clawLeft = null;

    double movement;
    double rotation;
    double strafe;

    public void actions() {
        TelemetryPacket packet = new TelemetryPacket();

        // updated based on gamepads

        // update running actions
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;

        dash.sendTelemetryPacket(packet);
    }

    public void driverControl() {

        movement = gamepad1.left_stick_y;
        rotation = gamepad1.right_stick_x;
        strafe = gamepad1.left_stick_x;

        double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
        double direction = Math.atan2(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        boolean precision = gamepad1.right_bumper;

        //INFO Increasing speed to a maximum of 1
        double fl = magnitude * Math.sin(direction + Math.PI / 4) + rotation;
        double bl = magnitude * Math.cos(direction + Math.PI / 4) + rotation;
        double fr = magnitude * Math.cos(direction + Math.PI / 4) - rotation;
        double br = magnitude * Math.sin(direction + Math.PI / 4) - rotation;

        double hypot = Math.hypot(movement, strafe);
        double ratio;
        if (movement == 0 && strafe == 0)
            ratio = 1;
        else if (precision)
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(fl), Math.abs(bl)), Math.abs(fr)), Math.abs(br))) / 2;
        else
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(fl), Math.abs(bl)), Math.abs(fr)), Math.abs(br)));

        frontLeft.setPower(ratio * fl);
        backLeft.setPower(ratio * bl);
        frontRight.setPower(ratio * fr);
        backRight.setPower(ratio * br);

    }

    public void rightarmManual() {

        if (gamepad2.right_bumper) {
            armRight.setPower(0.2);
        } else if (gamepad2.left_bumper) {
            armRight.setPower(-0.2);
        } else {
            armRight.setPower(0);
        }

    }

    public void leftarmManual() {

        if (gamepad1.right_bumper) {
            armLeft.setPower(0.2);
        } else if (gamepad1.left_bumper) {
            armLeft.setPower(-0.2);
        } else {
            armLeft.setPower(0);
        }

    }


    public void rightslideManual() {

        if (gamepad2.right_trigger > 0) {
            slideRight.setPower(0.2);
        } else if (gamepad2.left_trigger > 0) {
            slideRight.setPower(-0.2);
        } else {
            slideRight.setPower(0);
        }
    }

    public void leftslideManual() {

        if (gamepad1.right_trigger > 0) {
            slideLeft.setPower(0.2);
        } else if (gamepad1.left_trigger > 0) {
            slideLeft.setPower(-0.2);
        } else {
            slideLeft.setPower(0);
        }
    }

    public void slideManual() {
        if (gamepad2.right_trigger > 0) {
            slideRight.setPower(1);
            slideLeft.setPower(1);
        } else if (gamepad2.left_trigger > 0) {
            slideRight.setPower(-1);
            slideLeft.setPower(-1);
        } else {
            slideRight.setPower(0);
            slideLeft.setPower(0);
        }
    }

    public void armManual() {
        if (gamepad2.right_bumper) {
            armRight.setPower(1);
            armLeft.setPower(1);
        } else if (gamepad2.left_bumper) {
            armRight.setPower(-1);
            armLeft.setPower(-1);
        } else {
            armRight.setPower(0);
            armLeft.setPower(0);
        }
    }

    public void claw() {
        if (gamepad2.a) {
            clawRight.setPosition(0.5);
            clawLeft.setPosition(0.5);
        } else if (gamepad2.b) {
            clawRight.setPosition(0.9);
            clawLeft.setPosition(0.9);
        }
    }

    public void reset() {
        if (gamepad2.y) {
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void slideAction() {

        if (gamepad1.a) {
            runningActions.add(new ParallelAction(
                    new InstantAction(() -> slideRight.getCurrentPosition()

            )));
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p1, i1, d1);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
        slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");

        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);
        clawLeft.setDirection(Servo.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        sleep(100);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                controller.setPID(p1, i1, d1);
                  int armPos = armRight.getCurrentPosition();
                  double pid1 = controller.calculate(armPos, target1);
                  double ff1 = Math.cos(Math.toRadians(target1 / tick_in_degrees1)) * f1;

                  double power1 = pid1 + ff1;

                  armRight.setPower(power1);
                  armLeft.setPower(power1);

                telemetry.addData("Arm Position", armPos);
                telemetry.addData("Arm Target", target1);
                telemetry.update();
 
                driverControl();
                armManual();
                slideManual();
                claw();
                reset();

            }

        }

    }
}
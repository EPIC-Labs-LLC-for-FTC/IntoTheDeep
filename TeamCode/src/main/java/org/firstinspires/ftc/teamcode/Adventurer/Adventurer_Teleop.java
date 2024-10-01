package org.firstinspires.ftc.teamcode.Adventurer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Adventurer_Teleop")
public class Adventurer_Teleop extends LinearOpMode {

    public DcMotorEx frontLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx backLeft = null;
    public DcMotorEx backRight = null;
    public DcMotorEx armLeft = null;
    public DcMotorEx armRight = null;

    double movement;
    double rotation;
    double strafe;

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

    public void slideControl() {

        if (gamepad2.right_bumper) {
            armRight.setPower(1);
            armLeft.setPower(1);
        } else if (gamepad2.left_bumper) {
            armRight.setPower(-1);
            armLeft.setPower(-1);
        }

    }

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        armRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        sleep(100);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                driverControl();
                slideControl();

            }

        }

    }
}

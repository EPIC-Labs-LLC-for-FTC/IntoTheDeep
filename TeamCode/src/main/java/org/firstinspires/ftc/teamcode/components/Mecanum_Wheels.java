package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Mecanum_Wheels {

    public DcMotorEx leftFront;
    public DcMotorEx leftBack;
    public DcMotorEx rightBack;
    public DcMotorEx rightFront;

    public IMU imu = null;

    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;

    double headingError  = 0;
    double targetHeading = 0;
    double driveSpeed = 0;
    double turnSpeed = 0;

    double COUNTS_PER_MOTOR_REV = 537.7 ;
    double DRIVE_GEAR_REDUCTION = 1.0 ;
    double WHEEL_DIAMETER_INCHES = 3.779528 ;
    double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    double HEADING_THRESHOLD = 1;
    double P_TURN_GAIN = 0.1;
    double P_DRIVE_GAIN = 0.1;

    public double mecanumWheelCircumference = 12.9;

    public LinearOpMode parent;

    public Telemetry telemetry;


    //public double mecanumWheelCircumference = 12.8; //inches


    private ElapsedTime runtime = new ElapsedTime();

    public Mecanum_Wheels(HardwareMap hardwareMap) {

        rightFront = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class,"leftFront");
        rightBack = hardwareMap.get(DcMotorEx.class,"rightBack");
        leftBack = hardwareMap.get(DcMotorEx.class,"leftBack");
        imu = hardwareMap.get(IMU.class, "imu");

    }

    public void checkWheelEncoder(DcMotorEx motor) {
        int new_Target;
        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
        // Ensure that the opmode is still active
        if (parent.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_Target = motor.getCurrentPosition() + (int) (24 * ticksPerInchMecanum);
            motor.setTargetPosition(new_Target);

            // Turn On RUN_TO_POSITION
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motor.setPower(0.6 * leftErrorAdjustment);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < 2) &&
                    (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d  ", new_Target);
                telemetry.addData("Path2", "Running at %7d ",
                        motor.getCurrentPosition());
                telemetry.update();
            }
        }
    }

    public void initialize() {

        double reset = 0;
        rightFront.setPower(reset);
        leftFront.setPower(reset);
        leftBack.setPower(reset);
        rightBack.setPower(reset);

        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        imu.resetYaw();
    }

    public void driverControl(boolean precision, double movement, double rotation, double strafe) {
        double magnitude = Math.sqrt(Math.pow(strafe, 2) + Math.pow(movement, 2));
        double direction = Math.atan2(strafe, -movement);

        double lf = magnitude * Math.sin(direction + Math.PI / 4) + rotation;
        double lb = magnitude * Math.cos(direction + Math.PI / 4) + rotation;
        double rf = magnitude * Math.cos(direction + Math.PI / 4) - rotation;
        double rb = magnitude * Math.sin(direction + Math.PI / 4) - rotation;

        double hypot = Math.hypot(movement, strafe);
        double ratio;
        if (movement == 0 && strafe == 0)
            ratio = 1;
        else if (precision)
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.abs(rb)), Math.abs(rf))) / 2;
        else
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.abs(rb)), Math.abs(rf)));

        leftFront.setPower(ratio * lf * leftErrorAdjustment);
        leftBack.setPower(ratio * lb * leftErrorAdjustment);
        rightFront.setPower(ratio * rf * rightErrorAdjustment);
        rightBack.setPower(ratio * rb * rightErrorAdjustment);
    }

    public void encoderDrive(double speed,
                             double leftFrontInches, double leftRearInches, double rightFrontInches,
                             double rightRearInches, double timeoutS) {
        int new_leftFrontTarget;
        int new_rightFrontTarget;
        int new_leftRearTarget;
        int new_rightRearTarget;
        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
        // Ensure that the opmode is still active
        if (parent.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_leftFrontTarget = leftFront.getCurrentPosition() + (int) (leftFrontInches * ticksPerInchMecanum);
            new_rightFrontTarget = rightFront.getCurrentPosition() + (int) (rightFrontInches * ticksPerInchMecanum);

            new_leftRearTarget = leftBack.getCurrentPosition() + (int) (leftRearInches * ticksPerInchMecanum);
            new_rightRearTarget = rightBack.getCurrentPosition() + (int) (rightRearInches * ticksPerInchMecanum);
            leftFront.setTargetPosition(new_leftFrontTarget);
            rightFront.setTargetPosition(new_rightFrontTarget);


            leftBack.setTargetPosition(new_leftRearTarget);
            rightBack.setTargetPosition(new_rightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(speed * leftErrorAdjustment);
            rightFront.setPower(speed * rightErrorAdjustment);

            leftBack.setPower(speed * leftErrorAdjustment);
            rightBack.setPower(speed * rightErrorAdjustment);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d  :%7d :%7d :%7d", new_leftFrontTarget, new_rightFrontTarget, new_leftRearTarget, new_rightRearTarget);
                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),

                        leftBack.getCurrentPosition(),
                        rightBack.getCurrentPosition());
                telemetry.update();
            }
        }
    }

    public void driveStraight(double maxDriveSpeed, double distance, double heading) {
        int moveCounts = (int)(distance * COUNTS_PER_INCH);
        int leftFrontTarget = leftFront.getCurrentPosition() + moveCounts;
        int rightFrontTarget = rightFront.getCurrentPosition() + moveCounts;
        int leftBackTarget = leftBack.getCurrentPosition() + moveCounts;
        int rightBackTarget = rightBack.getCurrentPosition() + moveCounts;

        leftFront.setTargetPosition(leftFrontTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightBack.setTargetPosition(rightBackTarget);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        maxDriveSpeed = Math.abs(maxDriveSpeed);
        moveRobot(maxDriveSpeed, 0);

        while (leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy()) {
            turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);
            moveRobot(driveSpeed, turnSpeed);
        }

        moveRobot(0, 0);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafe(double maxStrafeSpeed, double distance, double heading) {
        int moveCounts = (int)(distance * COUNTS_PER_INCH);
        int leftFrontTarget = leftFront.getCurrentPosition() - moveCounts;
        int rightFrontTarget = rightFront.getCurrentPosition() + moveCounts;
        int leftBackTarget = leftBack.getCurrentPosition() + moveCounts;
        int rightBackTarget = rightBack.getCurrentPosition() - moveCounts;

        leftFront.setTargetPosition(leftFrontTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightBack.setTargetPosition(rightBackTarget);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        maxStrafeSpeed = Math.abs(maxStrafeSpeed);
        moveRobot(maxStrafeSpeed, 0);

        while (parent.opModeIsActive() && (leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy())) {

            turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

            moveRobot(maxStrafeSpeed, turnSpeed);

        }

        moveRobot(0, 0);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }



//    public void encoderDrive(double speed,
//                             double frontLeftInches, double backLeftInches, double frontRightInches,
//                             double backRightInches, double timeoutS) {
//        int new_frontLeftTarget;
//        int new_frontRightTarget;
//        int new_backLeftTarget;
//        int new_backRightTarget;
//        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
//        // Ensure that the opmode is still active.
//        if (parent.opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            new_frontLeftTarget = leftFront.getCurrentPosition() + (int) (frontLeftInches * ticksPerInchMecanum);
//            new_frontRightTarget = rightFront.getCurrentPosition() + (int) (frontRightInches * ticksPerInchMecanum);
//
//            new_backLeftTarget = leftBack.getCurrentPosition() + (int) (backLeftInches * ticksPerInchMecanum);
//            new_backRightTarget = rightBack.getCurrentPosition() + (int) (backRightInches * ticksPerInchMecanum);
//            leftFront.setTargetPosition(new_frontLeftTarget);
//            rightFront.setTargetPosition(new_frontRightTarget);
//
//
//            leftBack.setTargetPosition(new_backLeftTarget);
//            rightBack.setTargetPosition(new_backRightTarget);
//
//            // Turn On RUN_TO_POSITION
//            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            leftFront.setPower(speed*leftErrorAdjustment);
//            rightFront.setPower(speed*rightErrorAdjustment);
//
//            leftBack.setPower(speed*leftErrorAdjustment);
//            rightBack.setPower(speed*rightErrorAdjustment);
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            while (parent.opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
//                // Display it for the driver.
//                telemetry.addData("Path1", "Running to %1$7d :%2$7d :%3$7d :%4$7d", new_frontLeftTarget, new_frontRightTarget, new_backLeftTarget, new_backRightTarget);
//                telemetry.addData("Path2", "Running at %1$7d :2$%7d :%3$7d :%4$7d",
//                        leftFront.getCurrentPosition(),
//                        rightFront.getCurrentPosition(),
//
//                        leftBack.getCurrentPosition(),
//                        rightBack.getCurrentPosition());
//                telemetry.update();
//            }
//        }
//        // Stop all motion;
//        leftFront.setPower(0);
//        rightFront.setPower(0);
//
//        leftBack.setPower(0);
//        rightBack.setPower(0);
//
//        // Turn off RUN_TO_POSITION
//        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        //  sleep(250);   // optional pause after each move
//
//    }
    public void strafeDiagonal(String direction, double maxStrafeSpeed, double distance, double heading) {
        int moveCounts = (int)(distance * COUNTS_PER_INCH);
        maxStrafeSpeed = Math.abs(maxStrafeSpeed);

        switch (direction.toLowerCase()) {
            case "northeast":
                leftBack.setTargetPosition(leftBack.getCurrentPosition() + moveCounts);
                rightFront.setTargetPosition(rightFront.getCurrentPosition() + moveCounts);
                leftFront.setTargetPosition(leftFront.getCurrentPosition());
                rightBack.setTargetPosition(rightBack.getCurrentPosition());
                break;

            case "northwest":
                leftFront.setTargetPosition(leftFront.getCurrentPosition() + moveCounts);
                rightBack.setTargetPosition(rightBack.getCurrentPosition() + moveCounts);
                rightFront.setTargetPosition(rightFront.getCurrentPosition());
                leftBack.setTargetPosition(leftBack.getCurrentPosition());
                break;

            case "southeast":
                leftFront.setTargetPosition(leftFront.getCurrentPosition() - moveCounts);
                rightBack.setTargetPosition(rightBack.getCurrentPosition() - moveCounts);
                rightFront.setTargetPosition(rightFront.getCurrentPosition());
                leftBack.setTargetPosition(leftBack.getCurrentPosition());
                break;

            case "southwest":
                leftBack.setTargetPosition(leftBack.getCurrentPosition() - moveCounts);
                rightFront.setTargetPosition(rightFront.getCurrentPosition() - moveCounts);
                leftFront.setTargetPosition(leftFront.getCurrentPosition());
                rightBack.setTargetPosition(rightBack.getCurrentPosition());
                break;
        }
    }
    public void holdHeading(double maxTurnSpeed, double heading, double holdTime) {


        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (parent.opModeIsActive() && (holdTimer.time() < holdTime)) {
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);

            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);

            moveRobot(0, turnSpeed);

        }

        moveRobot(0, 0);
    }

    public void turnToHeading(double maxTurnSpeed, double heading) {
        getSteeringCorrection(heading, P_DRIVE_GAIN);

        while (Math.abs(headingError) > HEADING_THRESHOLD) {
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);
            moveRobot(0, turnSpeed);
        }
        moveRobot(0, 0);
    }

    private double getSteeringCorrection(double desiredHeading, double proportionalGain) {
        targetHeading = desiredHeading;
        headingError = targetHeading - getHeading();
        while (headingError > 180) headingError -= 360;
        while (headingError <= -180) headingError += 360;
        return Range.clip(headingError * proportionalGain, -1, 1);
    }

    private void moveRobot(double drive, double turn) {
        driveSpeed = drive;
        turnSpeed = turn;
        double leftFrontSpeed = drive - turn;
        double rightFrontSpeed = drive + turn;
        double leftBackSpeed = drive - turn;
        double rightBackSpeed = drive + turn;
        double max = Math.max(Math.max(Math.abs(leftFrontSpeed), Math.abs(rightFrontSpeed)), Math.max(Math.abs(leftBackSpeed), Math.abs(rightBackSpeed)));

        if (max > 1.0) {
            leftFrontSpeed /= max;
            rightFrontSpeed /= max;
            leftBackSpeed /= max;
            rightBackSpeed /= max;
        }
        leftFront.setPower(leftFrontSpeed);
        rightFront.setPower(rightFrontSpeed);
        leftBack.setPower(leftBackSpeed);
        rightBack.setPower(rightBackSpeed);
    }

    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }
}
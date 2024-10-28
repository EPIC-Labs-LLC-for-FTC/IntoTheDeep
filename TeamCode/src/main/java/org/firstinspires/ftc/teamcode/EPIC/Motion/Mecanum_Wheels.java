package org.firstinspires.ftc.teamcode.EPIC.Motion;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.EPIC.Components.AComponents;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IMecanumListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.MecanumEventObject;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.DriveStates;

import java.util.ArrayList;
import java.util.List;

public class Mecanum_Wheels extends AComponents {
    //Configuration used: 6wheelConfig
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    double backcorrection = 1.0;
    public DriveStates stateMecanum;
    private List<IMecanumListener> listeners;

    //public DcMotorEx xRail;


    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;

    public double mecanumWheelCircumference = 12; //inches
    public double omniWheelCircumference = 12; //inches




    public int velocity = 200;

    private ElapsedTime runtime = new ElapsedTime();

    public Mecanum_Wheels(HardwareMap hardwareMap) {
        frontright = hardwareMap.get(DcMotorEx.class,"frontRight");
        frontleft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        backright = hardwareMap.get(DcMotorEx.class,"backRight");
        backleft = hardwareMap.get(DcMotorEx.class,"backLeft");

        this.listeners = new ArrayList<>();

        //xRail = hardwareMap.get(DcMotorEx.class, "xRail");
    }

    //initialize for TeleOp
    @Override
    public void initialize() {
        double reset = 0;
        frontright.setPower(reset);
        //frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setPower(reset);
        backleft.setPower(reset);
        backright.setPower(reset);

        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontright.setDirection(DcMotorSimple.Direction.FORWARD);

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);

        if(IsAutonomous)
        {


            frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void encoderDrive(double speed,
                             double frontLeftInches, double backLeftInches, double frontRightInches,
                             double backRightInches, double timeoutS) {
        int new_frontLeftTarget;
        int new_frontRightTarget;
        int new_backLeftTarget;
        int new_backRightTarget;
        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
        // Ensure that the opmode is still active
        if (parent.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_frontLeftTarget = frontleft.getCurrentPosition() + (int) (frontLeftInches * ticksPerInchMecanum);
            new_frontRightTarget = frontright.getCurrentPosition() + (int) (frontRightInches * ticksPerInchMecanum);

            new_backLeftTarget = backleft.getCurrentPosition() + (int) (backLeftInches * ticksPerInchMecanum);
            new_backRightTarget = backright.getCurrentPosition() + (int) (backRightInches * ticksPerInchMecanum);
            frontleft.setTargetPosition(new_frontLeftTarget);
            frontright.setTargetPosition(new_frontRightTarget);


            backleft.setTargetPosition(new_backLeftTarget);
            backright.setTargetPosition(new_backRightTarget);

            // Turn On RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontleft.setPower(speed*leftErrorAdjustment);
            frontright.setPower(speed*rightErrorAdjustment);

            backleft.setPower(speed*leftErrorAdjustment);
            backright.setPower(speed*rightErrorAdjustment);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontleft.isBusy() || frontright.isBusy() || backleft.isBusy() || backright.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %1$7d :%2$7d :%3$7d :%4$7d", new_frontLeftTarget, new_frontRightTarget, new_backLeftTarget, new_backRightTarget);
                telemetry.addData("Path2", "Running at %1$7d :2$%7d :%3$7d :%4$7d",
                        frontleft.getCurrentPosition(),
                        frontright.getCurrentPosition(),

                        backleft.getCurrentPosition(),
                        backright.getCurrentPosition());
                telemetry.update();
            }
        }
        // Stop all motion;
        frontleft.setPower(0);
        frontright.setPower(0);

        backleft.setPower(0);
        backright.setPower(0);

        // Turn off RUN_TO_POSITION
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move

    }

    public void move(double lefty, double righty, double leftx, double rightx){
        frontright.setPower((-lefty  - rightx - leftx)*rightErrorAdjustment); // should work same as above
        frontleft.setPower((-lefty + rightx + leftx)*leftErrorAdjustment);
        backright.setPower((-lefty - rightx + leftx)*rightErrorAdjustment);
        backleft.setPower((-lefty + rightx - leftx)*leftErrorAdjustment);

    }

    public void addMecanumListener (IMecanumListener listener) {
        listeners.add(listener);
    }

    public void removeMecanumListener (IMecanumListener listener) {
        listeners.remove(listener);
    }

    public void fireMecanumActivity (MecanumEventObject event) {
        for (IMecanumListener listener : listeners) {
            listener.mecanumActivity(event);
        }
    }

    public void moveForwards (double speed, double distance, double timeOutS) {
        stateMecanum = DriveStates.MOVE_FORWARDS;
        fireMecanumActivity(new MecanumEventObject(this, stateMecanum));
        encoderDrive(speed, distance, distance, distance, distance, timeOutS);
    }

    public void moveBackwards (double speed, double distance, double timeOutS) {
        stateMecanum = DriveStates.MOVE_BACKWARDS;
        fireMecanumActivity(new MecanumEventObject(this, stateMecanum));
        encoderDrive(speed, -distance, -distance, -distance, -distance, timeOutS);
    }

    public void strafeRight (double speed, double distance, double timeOutS) {
        stateMecanum = DriveStates.STRAFING_RIGHT;
        fireMecanumActivity(new MecanumEventObject(this, stateMecanum));
        encoderDrive(speed, distance, -distance, -distance, distance, timeOutS);
    }

    public void strafeLeft (double speed, double distance, double timeOutS) {
        stateMecanum = DriveStates.STRAFING_LEFT;
        fireMecanumActivity(new MecanumEventObject(this, stateMecanum));
        encoderDrive(speed, -distance, distance, distance, -distance, timeOutS);
    }

    public void clockwise (double speed, double distance, double timeOutS) {
        encoderDrive(speed, distance, distance, -distance, -distance, timeOutS);
    }

    public void counterClockwise (double speed, double distance, double timeOutS) {
        encoderDrive(speed, -distance, -distance, distance, distance, timeOutS);
    }
}

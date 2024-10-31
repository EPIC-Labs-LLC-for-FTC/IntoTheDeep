package org.firstinspires.ftc.teamcode.EPIC.Motion;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum_Wheels {
    //Configuration used: 6wheelConfig
    public DcMotorEx leftFront = null;
    public DcMotorEx leftRear = null;
    public DcMotorEx rightRear = null;
    public DcMotorEx rightFront = null;
    double backcorrection = 1.0;

    //public DcMotorEx xRail;

    public boolean IsAutonomous = false;

    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;

    public double mecanumWheelCircumference = 12; //inches
    public double omniWheelCircumference = 12; //inches



    public LinearOpMode parent;

    public int velocity = 200;

    private ElapsedTime runtime = new ElapsedTime();

    public Telemetry telemetry;

    public Mecanum_Wheels(HardwareMap hardwareMap) {
        rightFront = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class,"leftFront");
        rightRear = hardwareMap.get(DcMotorEx.class,"rightRear");
        leftRear = hardwareMap.get(DcMotorEx.class,"leftRear");

        //xRail = hardwareMap.get(DcMotorEx.class, "xRail");
    }

    //initialize for TeleOp
    public void initialize() {
        double reset = 0;
        rightFront.setPower(reset);
        //rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setPower(reset);
        leftRear.setPower(reset);
        rightRear.setPower(reset);

        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(IsAutonomous)
        {


            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
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

            new_leftRearTarget = leftRear.getCurrentPosition() + (int) (leftRearInches * ticksPerInchMecanum);
            new_rightRearTarget = rightRear.getCurrentPosition() + (int) (rightRearInches * ticksPerInchMecanum);
            leftFront.setTargetPosition(new_leftFrontTarget);
            rightFront.setTargetPosition(new_rightFrontTarget);


            leftRear.setTargetPosition(new_leftRearTarget);
            rightRear.setTargetPosition(new_rightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(speed*leftErrorAdjustment);
            rightFront.setPower(speed*rightErrorAdjustment);

            leftRear.setPower(speed*leftErrorAdjustment);
            rightRear.setPower(speed*rightErrorAdjustment);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() || rightFront.isBusy() || leftRear.isBusy() || rightRear.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d  :%7d :%7d :%7d", new_leftFrontTarget, new_rightFrontTarget, new_leftRearTarget, new_rightRearTarget);
                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),

                        leftRear.getCurrentPosition(),
                        rightRear.getCurrentPosition());
                telemetry.update();
            }
        }
        // Stop all motion;
        leftFront.setPower(0);
        rightFront.setPower(0);

        leftRear.setPower(0);
        rightRear.setPower(0);

        // Turn off RUN_TO_POSITION
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move

    }

    public void move(double lefty, double righty, double leftx, double rightx){
        rightFront.setPower((-lefty  - rightx - leftx)*rightErrorAdjustment); // should work same as above
        leftFront.setPower((-lefty + rightx + leftx)*leftErrorAdjustment);
        rightRear.setPower((-lefty - rightx + leftx)*rightErrorAdjustment);
        leftRear.setPower((-lefty + rightx - leftx)*leftErrorAdjustment);

    }
}

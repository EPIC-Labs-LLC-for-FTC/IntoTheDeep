package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

public class Arm extends AComponents implements IArm{

    //Declare your servos, motors, sensors, other devices here
    private DcMotorEx armMotorR;
    private DcMotorEx armMotorL;
    public double speed = 0.5;
    public ArmStates stateArm;

    public Arm(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        armMotorR = hardwareMap.get(DcMotorEx.class, "armMotorR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "armMotorL");
    }
    @Override
    public void initialize() {
        double reset = 0;
        armMotorR.setPower(reset);
        armMotorL.setPower(reset);

        armMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (IsAutonomous) {
            //override settings for autonomous mode if needed
        }

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateArm = ArmStates.NEUTRAL;
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Arm State", stateArm.toString());
        telemetry.update();
    }

    @Override
    public void move(ArmStates state) {
        //Negative value lifts arm up, positive moves it down.
        double position = state.getState() - stateArm.getState();
        int targetPosR;
        int targetPosL;
        double degreesPerRotationArm = 537.7;
        //degreesPerRotationArm is a placeholder until testing. 360 should be correct though.
        double ticksPerDegree = 537.7 / degreesPerRotationArm;

        targetPosR = armMotorR.getCurrentPosition() + (int) (position * ticksPerDegree);
        targetPosL = armMotorL.getCurrentPosition() + (int) (position * ticksPerDegree);

        armMotorR.setTargetPosition(targetPosR);
        armMotorL.setTargetPosition(targetPosL);

        armMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotorR.setPower(speed);
        armMotorL.setPower(speed);

        while (parent.opModeIsActive() &&
                (armMotorR.isBusy() || armMotorL.isBusy())) {
            telemetry.addData("Arm running to", "armMotorR: %1$7.3d  armMotorL: %2$7.3d",
                    targetPosR, targetPosL);
            telemetry.addData("Arm progress", "armMotorR: %1$7.3d  armMotorL: %2$7.3d",
                    armMotorR.getCurrentPosition(), armMotorL.getCurrentPosition());
            telemetry.update();
        }

        armMotorR.setPower(0);
        armMotorL.setPower(0);

        stateArm = state;

        armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void freeMove(double speed) {
        /*
        Negative speed input for running backwards. Method
         will likely only be used for testing.
        */
        armMotorR.setPower(speed);
        armMotorL.setPower(speed);
    }
}

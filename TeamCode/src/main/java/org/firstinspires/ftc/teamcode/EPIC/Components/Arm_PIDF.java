package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

public class Arm_PIDF extends AComponents implements IArm, IPIDF{
    public PIDController armController;

    private final DcMotorEx armMotorR;
    private final DcMotorEx armMotorL;
    public ArmStates stateArm;

    private final double ticksPerDegrees = 1425.1/360;

    public Arm_PIDF(HardwareMap hardwareMap) {
        armMotorR = hardwareMap.get(DcMotorEx.class, "SMR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "SML");
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Arm State", "INITIALIZED");
        telemetry.update();
    }

    public void initialize(double p, double i, double d) {
        armController = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        double reset = 0;
        armMotorR.setPower(reset);
        armMotorL.setPower(reset);

        armMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateArm = ArmStates.INITIALIZED;

        this.displayComponentValues();
    }

    @Override
    public void runPIDF(double p, double i, double d, double f, int target) {
        armController.setPID(p, i, d);
        int armPosR = armMotorR.getCurrentPosition();
        int armPosL = armMotorL.getCurrentPosition();
        double pidR = armController.calculate(armPosR, target);
        double pidL = armController.calculate(armPosL, target);
        double ff = Math.cos(Math.toRadians(target / ticksPerDegrees)) * f;

        double powerR = pidR + ff;
        double powerL = pidL + ff;

        armMotorR.setPower(powerR);
        armMotorL.setPower(powerL);

        telemetry.addData("ArmPosR: ", armPosR);
        telemetry.addData("ArmPosL", armPosL);
        telemetry.addData("ArmTargetPos: ", target);
        telemetry.update();
    }

    @Override
    public void freeMove(double speed) {

    }

    @Override
    public void move(ArmStates state, double timeOutS) {

    }
}

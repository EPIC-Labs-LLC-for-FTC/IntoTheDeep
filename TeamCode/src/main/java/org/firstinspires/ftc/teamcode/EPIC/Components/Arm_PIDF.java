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

    public static double p = 0, i = 0, d = 0, f = 0;

    public int targetPos;

    private final DcMotorEx armMotorR;
    private final DcMotorEx armMotorL;
    public ArmStates stateArm;

    private final double ticksPerDegrees = 1425.1/360;

    public Arm_PIDF(HardwareMap hardwareMap) {
        armController = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        armMotorR = hardwareMap.get(DcMotorEx.class, "SMR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "SML");
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Arm State", "INITIALIZED");
        telemetry.update();
    }

    @Override
    public void initialize() {
        double reset = 0;
        armMotorR.setPower(reset);
        armMotorL.setPower(reset);

        armMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorL.setDirection(DcMotorSimple.Direction.FORWARD);

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateArm = ArmStates.INITIALIZED;

        this.displayComponentValues();
    }

    @Override
    public void runPIDF() {
        armController.setPID(p, i, d);
        int armPos = armMotorR.getCurrentPosition();
        double pid = armController.calculate(armPos, targetPos);
        double ff = Math.cos(Math.toRadians(targetPos / ticksPerDegrees)) * f;

        double power = pid + ff;

        armMotorR.setPower(power);
        armMotorL.setPower(power);

        telemetry.addData("Pos: ", armPos);
        telemetry.addData("TargetPos: ", targetPos);
        telemetry.update();
    }

    @Override
    public void freeMove(double speed) {

    }

    @Override
    public void move(ArmStates state, double timeOutS) {

    }
}

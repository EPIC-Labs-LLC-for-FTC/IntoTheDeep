package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ArmEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IArmListener;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

import java.util.ArrayList;
import java.util.List;

public class Arm_PIDF extends AComponents implements IArm, IPIDF{
    public PIDController armController;

    private final DcMotorEx armMotorR;
    private final DcMotorEx armMotorL;
    public ArmStates stateArm;
    private List<IArmListener> listeners;

    private final double ticksPerDegrees = 1425.1/360;
    public int targetPos;

    public Arm_PIDF(HardwareMap hardwareMap) {
        armMotorR = hardwareMap.get(DcMotorEx.class, "AMR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "AML");

        listeners = new ArrayList<>();
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

    public void addArmListener(IArmListener listener) {
        listeners.add(listener);
    }

    public void removeArmListener(IArmListener listener) {
        listeners.remove(listener);
    }

    private void notifyArmStateChange(ArmEventObject event) {
        for (IArmListener listener : listeners) {
            listener.onArmMove(event);
        }
    }

    @Override
    public void runPIDF(double p, double i, double d, double f) {
        armController.setPID(p, i, d);
        int armPos = armMotorR.getCurrentPosition();
        double pid = armController.calculate(armPos, targetPos);
        double ff = Math.cos(Math.toRadians(targetPos / ticksPerDegrees)) * f;

        double power = (pid/4) + ff;

        armMotorR.setPower(power);
        armMotorL.setPower(power);

        telemetry.addData("ArmPos: ", armPos);
        telemetry.addData("ArmTargetPos: ", targetPos);
    }

    @Override
    public void freeMove(double speed) {

    }

    @Override
    public void move(ArmStates state) {
        this.targetPos = (int) state.getState();
        this.stateArm = state;
        this.notifyArmStateChange(new ArmEventObject(this, stateArm));
    }
}

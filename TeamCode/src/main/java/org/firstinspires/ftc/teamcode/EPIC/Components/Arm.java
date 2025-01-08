package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm implements IComponents, IArm{

    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    private ElapsedTime runtime = new ElapsedTime();
    //Declare your servos, motors, sensors, other devices here

    private DcMotorEx armRight;
    private DcMotorEx armLeft;
    private PIDController controller;
    public double p1 = 0.013, i1 = 0, d1 = 0.00075;
    public double f1 = -0.2;
    private int target1 = 0;
    private final double tick_in_degrees1 = 2786.2/360;
    public Arm(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
    }
    @Override
    public void initialize() {

        armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new PIDController(p1, i1, d1);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
    }

    @Override
    public void displayComponentValues() {

    }

    @Override
    public void setParent(LinearOpMode parent) {

    }

    @Override
    public void setTelemetry(Telemetry telemetry) {

    }

    @Override
    public void setIsAutonomous(boolean isAutonomous) {

    }


    @Override
    public void armMove(int target1) {
        controller.setPID(p1, i1, d1);
        int armPos = armRight.getCurrentPosition();
        double pid1 = controller.calculate(armPos, target1);
        double ff1 = Math.cos(Math.toRadians(target1 / tick_in_degrees1)) * f1;

        double power1 = pid1 + ff1;

        armRight.setPower(power1);
        armLeft.setPower(power1);

        telemetry.addData("Arm Position", armPos);
        telemetry.addData("Arm Target", target1);
        telemetry.addData("Arm Power", power1);
    }

    public void armManualUp() {
        target1 = target1 + 10;
    }

    public void armManualDown() {
        target1 = target1 + 10;
    }

}

package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slider implements IComponents, ISlider{
    public boolean IsAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    private DcMotorEx slideMotorR;
    private DcMotorEx slideMotorL;

    public Slider(HardwareMap hardwareMap){
        //define devices here
        slideMotorR = hardwareMap.get(DcMotorEx.class, "slideMotorR");
        slideMotorL = hardwareMap.get(DcMotorEx.class, "slideMotorL");
    }

    @Override
    public void initialize() {
        double reset = 0;
        slideMotorR.setPower(reset);
        slideMotorL.setPower(reset);

        slideMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotorL.setDirection(DcMotorSimple.Direction.REVERSE);

        slideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (IsAutonomous) {

        }
    }

    @Override
    public void displayComponentValues() {

    }

    @Override
    public void setParent(LinearOpMode parent) {
        this.parent = parent;
    }

    @Override
    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void setIsAutonomous(boolean isAutonomous) {
        this.IsAutonomous = isAutonomous;
    }

    @Override
    public void goUp(double position) {

    }

    @Override
    public void goDown(double position) {

    }
}

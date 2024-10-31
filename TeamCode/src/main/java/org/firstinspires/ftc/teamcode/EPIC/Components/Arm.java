package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IArmListener; // Import IArmListener
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ArmEventObject; // Import ArmEventObject

import java.util.ArrayList;
import java.util.List;

public class Arm extends AComponents implements IArm{

    // Declare your servos, motors, sensors, other devices here
    private DcMotorEx armMotorR;
    private DcMotorEx armMotorL;
    public double speed = 1;
    public ArmStates stateArm;
    private double holdPower = 0.1;
    private double errorMultiplierR = 0.192;
    private double errorMultiplierL = 1;
    private double adjustments = 0;
    private ElapsedTime runtime = new ElapsedTime();
    // New list to hold arm listeners
    private List<IArmListener> listeners;

    public Arm(HardwareMap hardwareMap) {
        // Instantiate servos, motors, sensors, other devices here
        armMotorR = hardwareMap.get(DcMotorEx.class, "AMR");
        armMotorL = hardwareMap.get(DcMotorEx.class, "AML");

        // Initializes the listeners list
        this.listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        double reset = 0;
        armMotorR.setPower(reset);
        armMotorL.setPower(reset);

        armMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorL.setDirection(DcMotorSimple.Direction.FORWARD);

        armMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (IsAutonomous) {
            // Override settings for autonomous mode if needed
        }

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stateArm = ArmStates.INITIALIZED;

        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Arm State", "Initialized");
        telemetry.update();
    }

    public double getAdjustments(){
        return adjustments;
    }

    public void setAdjustments(double adjustment){
        adjustments+= adjustment;
    }

    @Override
    public void move(ArmStates state, double timeOutS) {
        // Negative value lifts arm up, positive moves it down.
        double position = state.getState() - stateArm.getState();
        int targetPosR;
        int targetPosL;

        targetPosR = armMotorR.getCurrentPosition() + (int) position;
        targetPosL = armMotorL.getCurrentPosition() + (int) position;
        if(stateArm==ArmStates.LOWERED){
            targetPosR += adjustments;
            targetPosL += adjustments;
        }
        armMotorR.setTargetPosition(targetPosR);
        armMotorL.setTargetPosition(targetPosL);

        armMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotorR.setPower(speed*errorMultiplierR);
        armMotorL.setPower(speed*errorMultiplierL);
        runtime.reset();
        while (parent.opModeIsActive() &&
                (armMotorR.isBusy() || armMotorL.isBusy()) && runtime.seconds() < timeOutS) {
            telemetry.addData("L. Arm", this.getLeftMotorPos());
            telemetry.update();
        }

        // Update the arm state
        this.stateArm = state;
        if(stateArm==ArmStates.DEPOSITING){


            armMotorR.setPower(0);
            armMotorL.setPower(0);
        }
        else {


            armMotorR.setPower(holdPower*errorMultiplierR);
            armMotorL.setPower(holdPower*errorMultiplierL);
        }

        // Notify listeners about the state change
        notifyArmStateChange(new ArmEventObject(this, this.stateArm));
    }

    public void move(int pos){



        //armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




        armMotorR.setTargetPosition(pos);
        armMotorL.setTargetPosition(pos);
        armMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();

        armMotorR.setPower(speed*errorMultiplierR);
        armMotorL.setPower(speed*errorMultiplierL);
        while (parent.opModeIsActive() &&
                (runtime.seconds() < 5.0) &&
                (armMotorR.isBusy() || armMotorL.isBusy())) {
//            telemetry.addData("Arm running to", "armMotorR: %1$7.3d  armMotorL: %2$7.3d",
//                    pos, pos);
//            telemetry.addData("Arm progress", "armMotorR: %1$7.3d  armMotorL: %2$7.3d",
//                    armMotorR.getCurrentPosition(), armMotorL.getCurrentPosition());
//            // telemetry.update();
        }

        armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotorR.setPower(holdPower*errorMultiplierR);
        armMotorL.setPower(holdPower*errorMultiplierL);
    }

    public int getLeftMotorPos(){
        return armMotorR.getCurrentPosition();
    }

    public int getRightMotorPos(){
        return armMotorL.getCurrentPosition();
    }

    private void notifyArmStateChange(ArmEventObject event) {
        for (IArmListener listener : listeners) {
            listener.onArmMove(event);
        }
    }

    public void freeMove(double speed) {
        /*
        Negative speed input for running backwards. Method
         will likely only be used for testing.
        */
        armMotorR.setPower(speed);
        armMotorL.setPower(speed);
    }

    // New method to register listeners
    public void addArmListener(IArmListener listener) {
        listeners.add(listener);
    }

    // New method to remove listeners
    public void removeArmListener(IArmListener listener) {
        listeners.remove(listener);
    }
}



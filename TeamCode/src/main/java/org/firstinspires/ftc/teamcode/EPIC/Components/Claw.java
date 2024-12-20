package org.firstinspires.ftc.teamcode.EPIC.Components;

import static org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ClawEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IClawListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.WristEventObject;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

import java.util.ArrayList;
import java.util.List;

public class Claw extends AComponents implements IClaw{

    public Servo leftFinger;
    public Servo rightFinger;
    private List<IClawListener> listeners;
    public ClawStates stateClaw;

    //Declare your servos, motors, sensors, other devices here

    public Claw(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        leftFinger = hardwareMap.get(Servo.class, "LF");
        rightFinger = hardwareMap.get(Servo.class, "RF");
        this.listeners = new ArrayList<>();
    }

    @Override
    public void initialize() {
        move(0.45);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Claw State", "Claw Initialized");
        telemetry.update();
    }

    @Override
    public void move(ClawStates state) {
        double targetPos = state.getClawPos();
        leftFinger.setPosition(targetPos);
        rightFinger.setPosition(0.45-targetPos);
        this.stateClaw = state;
        fireClaw(new ClawEventObject(this, this.stateClaw));
    }

    public Action move(ClawStates state, boolean IsAction) {

        Claw claw = this;
        Action action = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double targetPos = (int) state.getClawPos();
                claw.move(targetPos);
                claw.stateClaw = state;
                //slide.fireSliderEvent(stateSlider);
                fireClaw(new ClawEventObject(claw, claw.stateClaw));
                return false;
            }
        };
        return action;
    }
    public void move(double pos) {
        leftFinger.setPosition(pos);
        rightFinger.setPosition(0.45-pos);
        //this.stateClaw = state;
        fireClaw(new ClawEventObject(this, this.stateClaw));
    }

    public void addClawListener(IClawListener listener) {
        listeners.add(listener);
    }

    public void removeClawListener(IClawListener listener) {
        listeners.remove(listener);
    }

    private void fireClaw(ClawEventObject event) {
        for (IClawListener listener : listeners) {
            listener.runClaw(event);
        }
    }
}

package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ISClawListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.SClawEventObject;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

import java.util.ArrayList;
import java.util.List;

public class SpecimenClaw extends AComponents implements IClaw{
    public Servo rightSpecFinger;
    public Servo leftSpecFinger;
    private List<ISClawListener> listeners;

    public enum SClawStates {
        OPEN(1.0),
        HOLDING_SPECIMEN(0);

        private double state;

        SClawStates(double i) {
            this.state = i;
        }

        public double getState() {
            return state;
        }

        public void setState(double state) {
            this.state = state;
        }
    }

    public SClawStates stateClaw;

    public SpecimenClaw(HardwareMap hardwareMap) {
        rightSpecFinger = hardwareMap.get(Servo.class, "RSF");
        leftSpecFinger = hardwareMap.get(Servo.class, "LSF");
        listeners = new ArrayList<>();
    }

    public void addSClawListener(ISClawListener listener) {
        listeners.add(listener);
    }

    public void removeSClawListener(ISClawListener listener) {
        listeners.remove(listener);
    }

    public void fireSClawEvent(SClawStates state) {
        for (ISClawListener listener : listeners) {
            SClawEventObject sceo = new SClawEventObject(this, state);
            listener.specClawMove(sceo);
        }
    }

    @Override
    public void initialize() {
        leftSpecFinger.setPosition(0);
        rightSpecFinger.setPosition(0);
    }

    @Override
    public void move(ClawStates state) {
        //dont use this version here it's useless sadly
    }

    public void move(SClawStates state) {
        stateClaw = state;
        leftSpecFinger.setPosition(stateClaw.getState());
        rightSpecFinger.setPosition(stateClaw.getState());
        fireSClawEvent(stateClaw);
    }
}
package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

public class SpecimenClaw extends AComponents implements IClaw{
    public Servo rightSpecFinger;
    public Servo leftSpecFinger;

    public SpecimenClaw(HardwareMap hardwareMap) {
        rightSpecFinger = hardwareMap.get(Servo.class, "RSF");
        leftSpecFinger = hardwareMap.get(Servo.class, "LSF");
    }

    @Override
    public void initialize() {
        leftSpecFinger.setPosition(0);
        rightSpecFinger.setPosition(0);
    }

    @Override
    public void move(ClawStates state) {

    }
}

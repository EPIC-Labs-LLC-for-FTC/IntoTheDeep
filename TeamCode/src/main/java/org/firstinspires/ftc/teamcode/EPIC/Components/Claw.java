package org.firstinspires.ftc.teamcode.EPIC.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw extends AComponents implements IClaw{

    private Servo leftFinger;
    private Servo rightFinger;
    public double reset = 0;

    //Declare your servos, motors, sensors, other devices here

    public Claw(HardwareMap hardwareMap) {
        //Instantiate your servos, motors, sensors, other devices here
        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
    }

    @Override
    public void initialize() {
        leftFinger.setDirection(Servo.Direction.REVERSE);
        rightFinger.setDirection(Servo.Direction.FORWARD);

        leftFinger.scaleRange(0, 0.125);
        rightFinger.scaleRange(0, 0.125);
        //setPosition(1.0) will open the claws 45 degrees outwards
        leftFinger.setPosition(reset);
        rightFinger.setPosition(reset);

        if(IsAutonomous){
            //override settings for autonomous mode if needed
        }
        this.displayComponentValues();
    }

    @Override
    public void displayComponentValues() {
        telemetry.addData("Claw","Object Initialized");
        telemetry.update();
    }

    @Override
    public void open(double position) {
        leftFinger.setPosition(position);
        rightFinger.setPosition(position);
    }

    @Override
    public void close(double position) {
        leftFinger.setPosition(-position);
        rightFinger.setPosition(-position);
    }

    public void relativeMove(double position) {
        //Negative value for closing, positive for opening
        leftFinger.setPosition(leftFinger.getPosition() + position);
        rightFinger.setPosition(rightFinger.getPosition() + position);
    }
}

package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;

@Config
@TeleOp(name = "TestServo")
public class Test_Servos extends LinearOpMode {

    public static double wristPos = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        int sleepval = 1000;
        Servo jointL = hardwareMap.get(Servo.class, "yservo");

        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.b) {
                jointL.setPosition(0);
                sleep(100);
            }
            else if(gamepad1.x) {
                jointL.setPosition(1);
                sleep(100);
            }
            else if(gamepad1.a) {
                jointL.setPosition(jointL.getPosition() - 0.01);
                sleep(100);
            }
            else if(gamepad1.y) {
                jointL.setPosition(jointL.getPosition() + 0.01);
                sleep(100);
            }
            telemetry.addData("JointL", jointL.getPosition());
            telemetry.update();
        }
    }
}

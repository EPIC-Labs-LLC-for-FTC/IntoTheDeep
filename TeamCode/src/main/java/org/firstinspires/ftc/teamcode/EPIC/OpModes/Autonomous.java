package org.firstinspires.ftc.teamcode.EPIC.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Test Auton")
//@Disabled
public class Autonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.leftErrorAdjustment = 1;
        //mecanum.
        mecanum.parent = this;
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.initialize();


        waitForStart();
        sleep(100);
        //forward
        distance = 51;
        mecanum.encoderDrive(0.8,distance,distance,distance,distance,2);
        //strafe left
//        distance = 12.2;
//        mecanum.encoderDrive(0.8,-distance,distance,distance,-distance,2);
//        distance = 2.5;
//        mecanum.encoderDrive(0.8,-distance,-distance,-distance,-distance,1);
//
//        sleep(700);
//        //strafe right
//        distance = 13.25;
//        mecanum.encoderDrive(0.8,distance,-distance,-distance,distance,2);
//        //turn left
//        distance = 21.4;
//        mecanum.encoderDrive(0.8,-distance,-distance,distance,distance,2);
//        //strafe right
//        distance = 2;
//        mecanum.encoderDrive(0.8,distance,-distance,-distance,distance,1);
//
//        //backward
//        distance = 16;
//        mecanum.encoderDrive(0.8,-distance,-distance,-distance,-distance,2);
//
//        sleep(200);
//        //forward
//        distance = 17.5;
//        mecanum.encoderDrive(0.8,distance,distance,distance,distance,2);
//        //strafe right
//        distance = 14.5;
//        mecanum.encoderDrive(0.8,distance,-distance,-distance,distance,2);
//        //turn left
//        distance = 1.25;
//        mecanum.encoderDrive(0.8,-distance,-distance,-distance,-distance,1);
//        sleep(1000);
//        //strafe left
//        distance = 13.5;
//        mecanum.encoderDrive(0.8,-distance,distance,distance,-distance,2);









    }
}

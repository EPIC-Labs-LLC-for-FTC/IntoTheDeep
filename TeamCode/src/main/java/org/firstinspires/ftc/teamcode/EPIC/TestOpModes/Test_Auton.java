package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;

@Autonomous(name = "Test Auton")
//@Disabled
public class Test_Auton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.leftErrorAdjustment = 1;
        //mecanum.
        mecanum.setParent(this);
        mecanum.setIsAutonomous(true);
        mecanum.velocity = 400;
        mecanum.setTelemetry(this.telemetry);
        mecanum.initialize();

        //Component Declaration
        //Arm declaration and initialization
        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.setIsAutonomous(true);
        arm.initialize();

        //Wrist declaration and initialization
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(this.telemetry);
        wrist.setIsAutonomous(true);
        wrist.initialize();

        //Claw declaration and initialization
        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.setIsAutonomous(true);
        claw.initialize();

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

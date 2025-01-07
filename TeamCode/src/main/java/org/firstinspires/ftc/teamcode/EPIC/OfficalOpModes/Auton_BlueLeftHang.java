package org.firstinspires.ftc.teamcode.EPIC.OfficalOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.DriveStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

@Autonomous(name = "Auton_BlueLeftHang")
public class Auton_BlueLeftHang extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot odyssey = new Robot(this, "Blue", true);
        odyssey.setIsAutonomous(true);
        odyssey.initialize();

        while (opModeInInit()) {

        }

        waitForStart();


        double distance = 24;//requires change
        odyssey.odysseyWheels.encoderDrive(0.6, distance, distance, distance, distance, 6);
        sleep(1000);

        //  odyssey.odysseyWheels.move(DriveStates.);


        odyssey.odysseyArm.move(ArmStates.SPECIMEN_PICK);
        sleep(1000);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(1000);
        odyssey.odysseyClaw.move(ClawStates.HOLDING_SAMPLE_PORTRAIT);
        sleep(1000);
        // navigate to the bar
        odyssey.odysseyArm.move(ArmStates.SPECIMEN_DROP);
        sleep(500);
        odyssey.odysseyWrist.setPos(WristStates.PICKING_UP_SAMPLE);
        sleep(500);
        odyssey.odysseyWrist.setPos(WristStates.SPECIMEN_PICK);
        sleep(500);
        odyssey.odysseyClaw.move(ClawStates.OPEN);
        sleep(500);
        odyssey.odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
        sleep(500);


        odyssey.odysseyArm.move(ArmStates.INITIALIZED);
        sleep(1000);
        distance = -12;
        odyssey.odysseyWheels.encoderDrive(0.6, distance, distance, distance, distance, 6);
        sleep(1000);


    }


}

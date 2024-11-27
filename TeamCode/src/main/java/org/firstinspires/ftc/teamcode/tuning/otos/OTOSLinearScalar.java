package org.firstinspires.ftc.teamcode.tuning.otos;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

@TeleOp
public class OTOSLinearScalar extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(0,0,0));
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.setParent(this);
        mecanum.setTelemetry(telemetry);
        mecanum.initialize();
        double inchesMoved = 0;
        double trueInches = 119.3;

        Thread dt = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    double lefty = gamepad1.left_stick_y;
                    double leftx = gamepad1.left_stick_x;
                    double righty = gamepad1.right_stick_y;
                    double rightx = gamepad1.right_stick_x;
                    mecanum.move(lefty, righty, leftx, rightx);
                }
            }
        };

        waitForStart();
        dt.start();
        while (opModeIsActive()) {
            drive.updatePoseEstimate();
            inchesMoved = drive.otos.getPosition().x;
            telemetry.addData("UncorrectedPos", inchesMoved);
            telemetry.addData("Linear Scalar Value", trueInches/inchesMoved);
            telemetry.update();
        }
    }
}

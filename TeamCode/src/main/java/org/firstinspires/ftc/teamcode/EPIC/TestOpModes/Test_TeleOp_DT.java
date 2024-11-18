package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

@TeleOp(name = "TeleOp_DT")
public class Test_TeleOp_DT extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.setParent(this);
        mecanum.setTelemetry(this.telemetry);
        mecanum.initialize();

        while (opModeInInit()) {

        }

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                if (mecanum.frontleft.isBusy()) {
                    mecanum.frontleft.setPower(0);
                } else {
                    mecanum.frontleft.setPower(1);
                }
            } else if (gamepad1.y) {
                if (mecanum.frontright.isBusy()) {
                    mecanum.frontright.setPower(0);
                } else {
                    mecanum.frontright.setPower(1);
                }
            } else if (gamepad1.a) {
                if (mecanum.backright.isBusy()) {
                    mecanum.backright.setPower(0);
                } else {
                    mecanum.backright.setPower(1);
                }
            } else if (gamepad1.b) {
                if (mecanum.backleft.isBusy()) {
                    mecanum.backleft.setPower(0);
                } else {
                    mecanum.backleft.setPower(1);
                }
            }
        }
    }
}

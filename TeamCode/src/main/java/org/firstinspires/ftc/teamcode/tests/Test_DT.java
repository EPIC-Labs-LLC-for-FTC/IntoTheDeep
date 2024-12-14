package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Mecanum_Wheels;

//@Disabled
@TeleOp(name = "Test_DT")
public class Test_DT extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.parent = this;
        mecanum.telemetry = telemetry;
        mecanum.initialize();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                mecanum.rightFront.setPower(1);
            } else if (gamepad1.b) {
                mecanum.leftFront.setPower(1);
            } else if (gamepad1.x) {
                mecanum.rightBack.setPower(1);
            } else if (gamepad1.y) {
                mecanum.leftBack.setPower(1);
            }

            if(gamepad1.dpad_down){
                mecanum.checkWheelEncoder(mecanum.leftBack);
            }
            else if(gamepad1.dpad_left){
                mecanum.checkWheelEncoder(mecanum.leftFront);
            }
            else if(gamepad1.dpad_up){
                mecanum.checkWheelEncoder(mecanum.rightFront);
            }
            else if(gamepad1.dpad_right){
                mecanum.checkWheelEncoder(mecanum.rightBack);
            }
        }
    }
}
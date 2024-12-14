package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

//@Disabled
@TeleOp(name = "Test_DT")
public class Test_DT extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.setParent(this);
        mecanum.setTelemetry(telemetry);
        mecanum.initialize();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                mecanum.frontright.setPower(1);
            } else if (gamepad1.b) {
                mecanum.frontleft.setPower(1);
            } else if (gamepad1.x) {
                mecanum.backright.setPower(1);
            } else if (gamepad1.y) {
                mecanum.backleft.setPower(1);
            }

            

            if(gamepad1.dpad_down){
                mecanum.checkWheelEncoder(mecanum.backleft);
            }
            else if(gamepad1.dpad_left){
                mecanum.checkWheelEncoder(mecanum.frontleft);
            }
            else if(gamepad1.dpad_up){
                mecanum.checkWheelEncoder(mecanum.frontright);
            }
            else if(gamepad1.dpad_right){
                mecanum.checkWheelEncoder(mecanum.backright);
            }
        }
    }
}

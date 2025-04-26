package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "TestMotor")
public class Test_Motors extends LinearOpMode {

    public static double wristPos = 0;


    private DcMotorEx nlm1;
    @Override
    public void runOpMode() throws InterruptedException {
        nlm1 = hardwareMap.get(DcMotorEx.class, "nlm1");
        int sleepval = 1000;
        double reset = 0;
        nlm1.setPower(reset);
        nlm1.setDirection(DcMotorSimple.Direction.FORWARD);
        nlm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        nlm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nlm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        int armPos = nlm1.getCurrentPosition();
        int targetPos = armPos;
        waitForStart();
        while (opModeIsActive()) {

            if(gamepad1.b) {
                armPos = nlm1.getCurrentPosition();
                targetPos = armPos+1000;
                //sleep(500);
            }
            else if(gamepad1.x) {
                armPos = nlm1.getCurrentPosition();
                targetPos = armPos-1000;

                telemetry.addData("targetPos", targetPos);
                telemetry.update();
                //sleep(100);
            }
            else if(gamepad1.a) {
                targetPos=0;
                //sleep(100);
            }
            else if(gamepad1.y) {

                //sleep(100);
            }
            if(targetPos>=0){
                nlm1.setTargetPosition(targetPos);
                nlm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                nlm1.setPower(1);
                sleep(1000);
            }
            nlm1.setPower(0);
            telemetry.addData("nlm1", nlm1.getCurrentPosition());
            telemetry.update();
        }
    }
}

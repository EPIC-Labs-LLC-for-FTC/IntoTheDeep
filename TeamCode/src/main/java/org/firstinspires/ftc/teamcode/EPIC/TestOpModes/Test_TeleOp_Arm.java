package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;


@TeleOp(name = "Test_TeleOp_Arm")
public class Test_TeleOp_Arm extends LinearOpMode {
    double lefty;
    double leftx;
    double righty;
    double rightx;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx armRight;
        DcMotorEx armLeft;
        armLeft = hardwareMap.get(DcMotorEx.class, "AML");
        armRight = hardwareMap.get(DcMotorEx.class, "AMR");

        armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        armLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armLeft.setTargetPosition(0);
        armRight.setTargetPosition(0);

        boolean a = false;
        waitForStart();
        while(opModeIsActive()) {
            a = gamepad1.a;
            if (a) {
//                while (gamepad1.a) {
//                    odysseyRobot.odysseyArm.freeMove(0.25);
//                }
//                odysseyRobot.odysseyArm.freeMove(0);
                //odysseyRobot.odysseyClaw.open(0.575);

                armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                armRight.setTargetPosition(60);
                armLeft.setTargetPosition(60);

                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                armRight.setPower(0.3);
                armLeft.setPower(0.3);

                while (opModeIsActive() &&
                        (armRight.isBusy() || armLeft.isBusy())) {
//                telemetry.addData("Slider running to", "sliderR: %1$7.3d  sliderL: %2$7.3d", targetPosR, targetPosL);
//                telemetry.addData("Slider progress", "sliderR: %1$7.3d  sliderL: %2$7.3d",
//                        slideMotorR.getCurrentPosition(), slideMotorL.getCurrentPosition());
                    telemetry.update();
                }

                armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                armRight.setPower(0.3);
                armLeft.setPower(0.3);
            }

            //if(touchSensor.isPressed()){
            //telemetry.addData("My Touch Sensor is pressed? ", touchSensor.isPressed());
            //telemetry.addData("My Touch Sensor press value: ", touchSensor.getValue());

//            telemetry.addData("My Color Sensor is color? ", robot.colorSensor.getARGBColor());
//            telemetry.addData("My Color Sensor distance in Inches: ", robot.colorSensor.getDistanceInInches());
//            telemetry.addData("My Color Sensor distance in MMs: ", robot.colorSensor.getDistanceInMM());

            telemetry.addData("left arm", armLeft.getCurrentPosition());
            telemetry.addData("right arm", armRight.getCurrentPosition());
            telemetry.update();
        }
    }
}


package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;


@TeleOp(name = "Test_TeleOp_ArmOnly")
public class Test_TeleOp_ArmOnly extends LinearOpMode {
    double lefty;
    double leftx;
    double righty;
    double rightx;

    @Override
    public void runOpMode() throws InterruptedException {
        //Arm declaration and initialization
        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.setIsAutonomous(true);
        arm.initialize();
//
//        //Wrist declaration and initialization
//        Wrist wrist = new Wrist(hardwareMap);
//        wrist.setParent(this);
//        wrist.setTelemetry(this.telemetry);
//        wrist.setIsAutonomous(true);
//        wrist.initialize();
//
//        //Claw declaration and initialization
//        Claw claw = new Claw(hardwareMap);
//        claw.setParent(this);
//        claw.setTelemetry(this.telemetry);
//        claw.setIsAutonomous(true);
//        claw.initialize();
//
//        Slider slider = new Slider(hardwareMap);
//        slider.setParent(this);
//        slider.setTelemetry(this.telemetry);
//
//        slider.setIsAutonomous(true);
//
//        slider.initialize();

        while (opModeInInit()){

        }


        boolean dup2;
        boolean ddown2;
        boolean dup1 = false;
        boolean ddown1 = false;
        boolean dl1 = false;
        boolean dr1 = false;
        boolean y2;
        boolean a2;
        boolean x2;




        waitForStart();
        int armPos = 0;

        while (opModeIsActive()){
            //robot.colorSensor.getColor();
            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;
            dl1 = gamepad1.dpad_left;
            dr1 = gamepad1.dpad_right;
            dup1 = gamepad1.dpad_up;
            ddown1 = gamepad1.dpad_down;

            if(dl1){
                armPos = 1000;
                arm.move(armPos);
                sleep(2000);
            }
            else if(dr1) {
                armPos -= 10;
                arm.move(armPos);
            } else if (dup1) {

            }
            else if (ddown1) {

            }
            if (gamepad1.a) {
//                while (gamepad1.a) {
//                    odysseyRobot.odysseyArm.freeMove(0.25);
//                }
//                odysseyRobot.odysseyArm.freeMove(0);
                //odysseyRobot.odysseyClaw.open(0.575);

                armPos += 200;
                arm.move(armPos);
                sleep(2000);
            } else if (gamepad1.b) {


                armPos -= 200;
                arm.move(armPos);
                sleep(2000);
            } else if (gamepad1.x) {

            } else if (gamepad1.y) {

            }
            //if(touchSensor.isPressed()){
            //telemetry.addData("My Touch Sensor is pressed? ", touchSensor.isPressed());
            //telemetry.addData("My Touch Sensor press value: ", touchSensor.getValue());

//            telemetry.addData("My Color Sensor is color? ", robot.colorSensor.getARGBColor());
//            telemetry.addData("My Color Sensor distance in Inches: ", robot.colorSensor.getDistanceInInches());
//            telemetry.addData("My Color Sensor distance in MMs: ", robot.colorSensor.getDistanceInMM());

            telemetry.addData("left arm",arm.getLeftMotorPos());
            telemetry.addData("right arm",arm.getRightMotorPos());
            telemetry.update();
        }
    }
}

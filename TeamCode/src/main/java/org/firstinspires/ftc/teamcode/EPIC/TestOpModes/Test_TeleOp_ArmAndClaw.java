package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;


@TeleOp(name = "Test_TeleOp_ArmAndClaw")
public class Test_TeleOp_ArmAndClaw extends LinearOpMode {
    double lefty;
    double leftx;
    double righty;
    double rightx;

    @Override
    public void runOpMode() throws InterruptedException {
//        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
//        wheels.initialize();
//        wheels.telemetry = telemetry;
//        wheels.parent = this;
        //wheels.leftErrorAdjustment = 0.52;
        //wheels.rightErrorAdjustment = 0.52;
//        MyTouchSensor touchSensor = new MyTouchSensor(hardwareMap);
//        touchSensor.initialize();
//        touchSensor.telemetry = telemetry;
//        touchSensor.parent = this;

        //Component Declaration
        //Arm declaration and initialization
//        Arm arm = new Arm(hardwareMap);
//        arm.setParent(this);
//        arm.setTelemetry(this.telemetry);
//        arm.setIsAutonomous(true);
//        arm.initialize();
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
        Robot odysseyRobot = new Robot(this, "Red");
        odysseyRobot.initialize();

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

            odysseyRobot.odysseyWheels.move(lefty,righty,leftx,rightx);
            if(dl1){
                armPos += 10;
                odysseyRobot.odysseyArm.move(armPos);

                telemetry.addData("left arm", odysseyRobot.odysseyArm.getLeftMotorPos());
                telemetry.addData("right arm", odysseyRobot.odysseyArm.getRightMotorPos());
            }
            else if(dr1) {
                armPos -= 10;
                odysseyRobot.odysseyArm.move(armPos);
                telemetry.addData("left arm", odysseyRobot.odysseyArm.getLeftMotorPos());
                telemetry.addData("right arm", odysseyRobot.odysseyArm.getRightMotorPos());
            } else if (dup1) {
                odysseyRobot.odysseySlider.slide(537.7*4);
                sleep(2000);
            }
            else if (ddown1) {
                odysseyRobot.odysseySlider.slide(0);
                sleep(2000);
            }
            if (gamepad1.a) {
//                while (gamepad1.a) {
//                    odysseyRobot.odysseyArm.freeMove(0.25);
//                }
//                odysseyRobot.odysseyArm.freeMove(0);
                //odysseyRobot.odysseyClaw.open(0.575);

                armPos += 50;
                odysseyRobot.odysseyArm.move(armPos);

                telemetry.addData("left arm", odysseyRobot.odysseyArm.getLeftMotorPos());
                telemetry.addData("right arm", odysseyRobot.odysseyArm.getRightMotorPos());
                sleep(2000);
            } else if (gamepad1.b) {
                //odysseyRobot.odysseyClaw.open(0.875);
                //while (gamepad1.b) {
                    //odysseyRobot.odysseyArm.freeMove(-0.25);
                //}
                //odysseyRobot.odysseyArm.freeMove(0);

                //odysseyRobot.odysseySlider.slide(537.7*4);
                odysseyRobot.odysseyClaw.close(0.575);
//                armPos -= 10;
//                odysseyRobot.odysseyArm.move(armPos);
//
//                telemetry.addData("left arm", odysseyRobot.odysseyArm.getLeftMotorPos());
//                telemetry.addData("right arm", odysseyRobot.odysseyArm.getRightMotorPos());
//                sleep(2000);
            } else if (gamepad1.x) {
                //odysseyRobot.odysseySlider.slide(537.7*4);
                //odysseyRobot.odysseyClaw.close(0.7);

                odysseyRobot.odysseySlider.slide(2150);
                sleep(2000);
                odysseyRobot.odysseySlider.setPower(0);
            } else if (gamepad1.y) {
                //odysseyRobot.odysseySlider.slide(-537.7);
                odysseyRobot.odysseyClaw.open(0.85);

//                odysseyRobot.odysseySlider.slide(0);
//                sleep(2000);
//                odysseyRobot.odysseySlider.setPower(0);
            }
            //if(touchSensor.isPressed()){
            //telemetry.addData("My Touch Sensor is pressed? ", touchSensor.isPressed());
            //telemetry.addData("My Touch Sensor press value: ", touchSensor.getValue());

//            telemetry.addData("My Color Sensor is color? ", robot.colorSensor.getARGBColor());
//            telemetry.addData("My Color Sensor distance in Inches: ", robot.colorSensor.getDistanceInInches());
//            telemetry.addData("My Color Sensor distance in MMs: ", robot.colorSensor.getDistanceInMM());

            telemetry.addData("left finger",odysseyRobot.odysseyClaw.getLeftFingerPosition());
            telemetry.addData("right finger",odysseyRobot.odysseyClaw.getRightFingerPosition());
            telemetry.addData("left arm",odysseyRobot.odysseyArm.getLeftMotorPos());
            telemetry.addData("right arm",odysseyRobot.odysseyArm.getRightMotorPos());
            telemetry.addData("left slider",odysseyRobot.odysseySlider.getLeftPosition());
            telemetry.addData("right slider",odysseyRobot.odysseySlider.getRightPosition());
            telemetry.update();
        }
    }
}

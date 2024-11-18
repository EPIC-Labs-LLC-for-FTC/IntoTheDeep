package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyTouchSensor;


@TeleOp(name = "Test TeleOp Red")
public class Test_TeleOp_Red extends LinearOpMode {
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
        waitForStart();

        while (opModeIsActive()){
            //robot.colorSensor.getColor();
            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;
            odysseyRobot.odysseyWheels.move(lefty,righty,leftx,rightx);

            if (gamepad1.a) {
                odysseyRobot.odysseySlider.slide(250);
            } else if (gamepad1.b) {
                odysseyRobot.odysseySlider.slide(-250);
            }

            telemetry.update();
        }
    }
}

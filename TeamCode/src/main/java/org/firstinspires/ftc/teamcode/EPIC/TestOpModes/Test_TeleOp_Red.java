package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.Robot.Robot;
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
        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);

        while (opModeInInit()){

        }


        boolean dup2;
        boolean ddown2;
        boolean y2;
        boolean a2;
        boolean x2;




        waitForStart();


        while (opModeIsActive()){
            //robot.colorSensor.getColor();
            rightx = gamepad1.right_stick_x;
            righty = gamepad1.right_stick_y;
            leftx = gamepad1.left_stick_x;
            lefty = gamepad1.left_stick_y;
            wheels.move(rightx,leftx,righty,lefty);
            //if(touchSensor.isPressed()){
            //telemetry.addData("My Touch Sensor is pressed? ", touchSensor.isPressed());
            //telemetry.addData("My Touch Sensor press value: ", touchSensor.getValue());

//            telemetry.addData("My Color Sensor is color? ", robot.colorSensor.getARGBColor());
//            telemetry.addData("My Color Sensor distance in Inches: ", robot.colorSensor.getDistanceInInches());
//            telemetry.addData("My Color Sensor distance in MMs: ", robot.colorSensor.getDistanceInMM());
            telemetry.update();
            //}
        }
    }
}

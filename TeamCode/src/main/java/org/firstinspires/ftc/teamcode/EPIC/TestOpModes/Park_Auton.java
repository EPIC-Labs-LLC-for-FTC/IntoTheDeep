package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;

@Autonomous(name = "Park_Auton")
//@Disabled
public class Park_Auton extends LinearOpMode {
    private PIDController controller;
    private PIDController controller2;

    public static double p1 = 0.02, i1 = 0, d1 = 0.00015;
    public static double p2 = 0.017, i2 = 0, d2 = 0.0001;

    public static double f1 = -0.2;
    public static double f2 = -0.02;
    public static int target1 = 0;
    public static int target2 = 0;
    private final double tick_in_degrees1 = 2786.2/360;
    private final double tick_in_degrees2 = 537.7/360;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p1, i1, d1);
        controller2 = new PIDController(p2, i2, d2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        double distance = 0;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.leftErrorAdjustment = 1;
        //mecanum.
        mecanum.parent = this;
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.initialize();

        //Component Declaration
        //Arm declaration and initialization
        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.setIsAutonomous(true);
        arm.initialize();

        //Wrist declaration and initialization
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(this.telemetry);
        wrist.setIsAutonomous(true);
        wrist.initialize();

        //Claw declaration and initialization
        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.setIsAutonomous(true);
        claw.initialize();

        //Slider
        Slider slider = new Slider(hardwareMap);
        slider.setParent(this);
        slider.setTelemetry(this.telemetry);
        slider.setIsAutonomous(true);
        slider.initialize();

        // Arm PID
        Thread armPID = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    controller.setPID(p1, i1, d1);
                    int armPos = arm.getCurrentPosition();
                    double pid1 = controller.calculate(armPos, target1);
                    double ff1 = Math.cos(Math.toRadians(target1 / tick_in_degrees1)) * f1;

                    double power1 = pid1 + ff1;

                    arm.setPower(power1);

                    telemetry.addData("Arm Position", armPos);
                    telemetry.addData("Arm Target", target1);
                    telemetry.addData("Arm Power", power1);
                }
            }
        };

        Thread telemetryupdate = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    telemetry.addData("Arm Position", arm.armLeft.getCurrentPosition());
                    telemetry.addData("Arm Position", arm.armRight.getCurrentPosition());
                    telemetry.update();
                }
            }
        };



        waitForStart();
        telemetryupdate.start();
        sleep(100);
//        armPID.start();
//        sliderPID.start();
        distance = 15;
        mecanum.encoderDrive(0.6,distance,distance,distance,distance,2);
        sleep(1000);
        claw.open();
        sleep(2000);







    }
}
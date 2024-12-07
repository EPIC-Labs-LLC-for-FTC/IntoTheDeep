package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;

@Disabled
@TeleOp(name = "Wrist_Tester")
public class Test_TeleOp_Wrist extends LinearOpMode {

    public double targetPosR = 0;
    public double targetPosL = 0;
    public static double ap = 0.02, ai = 0, ad = 0.0015, af = 0.08;
    public static int atarget;
    double wristPosition = 1;


    @Override
    public void runOpMode() throws InterruptedException {
        int sleepval = 1000;
        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(telemetry);
        wrist.initialize();
        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(telemetry);
        arm.initialize(ap, ai, ad);
        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(telemetry);
        claw.initialize();
        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
        wheels.setParent(this);
        wheels.setParent(this);
        wheels.setTelemetry(telemetry);
        wheels.initialize();



        Thread pidf = new Thread() {
            public void run() {
                while (opModeIsActive()) {
                    arm.runPIDF(ap, ai, ad, af);
                }
            }
        };
        double lefty = gamepad1.left_stick_y;
        double leftx = gamepad1.left_stick_x;
        double righty = gamepad1.right_stick_y;
        double rightx = gamepad1.right_stick_x;

        waitForStart();
        pidf.start();
        while (opModeIsActive()) {


            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;


            if (gamepad1.a) {
                wristPosition += 0.01;
                wrist.setPos(wristPosition);
                sleep(sleepval);
            } else if (gamepad1.b) {
                wristPosition -= 0.01;
                wrist.setPos(wristPosition);
                sleep(sleepval);
            } else if (gamepad1.x) {
                atarget -= 100;
                arm.targetPos = atarget;
                //arm.runPIDF(ap, ai, ad, af);
                sleep(sleepval);
            } else if (gamepad1.y) {
                atarget += 100;
                arm.targetPos = atarget;
                //arm.runPIDF(ap, ai, ad, af);
                sleep(sleepval);
            }
            wheels.move(lefty, righty, leftx, rightx);

            //wrist.setPos(targetPosL);
            //sleep(500);





            telemetry.addData("JointR", wrist.jointR.getPosition());
            telemetry.addData("JointL", wrist.jointL.getPosition());
            telemetry.addData("ArmR", atarget);
            telemetry.update();

        }
    }
}

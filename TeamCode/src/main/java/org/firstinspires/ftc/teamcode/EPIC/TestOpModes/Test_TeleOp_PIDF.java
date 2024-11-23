package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm_PIDF;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider_PIDF;

@Config
@TeleOp(name = "TeleOp_PIDF")
public class Test_TeleOp_PIDF extends LinearOpMode {
    public final double ap = 0.02, ai = 0, ad = 0.001, af = 0.08;
    public static int atarget;
    public static double sp = 0.02, si = 0, sd = 0.0018, sf = 0;
    public static int starget;

    @Override
    public void runOpMode() throws InterruptedException {
        Arm_PIDF arm = new Arm_PIDF(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        Slider_PIDF slider = new Slider_PIDF(hardwareMap);
        slider.setParent(this);
        slider.setTelemetry(this.telemetry);
        arm.initialize();
        slider.initialize(sp, si, sd);

        while (opModeInInit()){

        }
        waitForStart();

        while (opModeIsActive()){
            slider.targetPos = starget;
            slider.runPIDF(sp, si, sd, sf);
            arm.targetPos = atarget;
            arm.runPIDF(ap, ai, ad, af);
        }
    }
}

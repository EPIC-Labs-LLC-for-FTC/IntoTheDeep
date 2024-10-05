package org.firstinspires.ftc.teamcode.EPIC.Robot;

import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public Claw odysseyClaw;
    public Slider odysseySlider;
    public Arm odysseyArm;
    public Wrist odysseyWrist;
    public Mecanum_Wheels odysseyWheels;

    public Robot(LinearOpMode parent){
        odysseyClaw = new Claw(parent.hardwareMap);
        odysseySlider = new Slider(parent.hardwareMap);
        odysseyArm = new Arm(parent.hardwareMap);
        odysseyWrist = new Wrist(parent.hardwareMap);
        odysseyWheels = new Mecanum_Wheels(parent.hardwareMap);
        odysseyClaw.setParent(parent);
        odysseySlider.setParent(parent);
        odysseyArm.setParent(parent);
        odysseyWrist.setParent(parent);
        odysseyClaw.setTelemetry(parent.telemetry);
        odysseySlider.setTelemetry(parent.telemetry);
        odysseyArm.setTelemetry(parent.telemetry);
        odysseyWrist.setTelemetry(parent.telemetry);
    }
}

package org.firstinspires.ftc.teamcode.EPIC.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odyssey_Bot {
    public Claw odysseyClaw;
    public Slider odysseySlider;
    public Arm odysseyArm;
    public Wrist odysseyWrist;

    public Odyssey_Bot(HardwareMap hardwareMap){
        odysseyClaw = new Claw(hardwareMap);
        odysseySlider = new Slider(hardwareMap);
        odysseyArm = new Arm(hardwareMap);
        odysseyWrist = new Wrist(hardwareMap);
    }
}

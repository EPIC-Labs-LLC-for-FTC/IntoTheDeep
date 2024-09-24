package org.firstinspires.ftc.teamcode.EPIC.Sensors;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class MyColorSensor {
    public LinearOpMode parent = null;
    public Telemetry telemetry = null;
    private ColorSensor colorSensor = null;

    public MyColorSensor(HardwareMap hardwareMap){
        colorSensor = hardwareMap.get(ColorSensor.class,"color0");

    }

    public int getARGBColor(){
        return colorSensor.argb();
    }

    public int getAlpha(){
        return colorSensor.alpha();
    }

    public int getRedColor(){
        return colorSensor.red();
    }

    public int getGreenColor(){
        return colorSensor.green();
    }

    public int getBlueColor(){
        return colorSensor.blue();
    }
}

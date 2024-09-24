package org.firstinspires.ftc.teamcode.EPIC.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class MyColorRangeSensor {
    public LinearOpMode parent = null;
    public Telemetry telemetry = null;
    private ColorRangeSensor colorRangeSensor = null;

    public MyColorRangeSensor(HardwareMap hardwareMap){
        colorRangeSensor = hardwareMap.get(ColorRangeSensor.class,"color0");
    }

    public int getARGBColor(){
        return colorRangeSensor.argb();
    }

    public int getAlpha(){
        return colorRangeSensor.alpha();
    }

    public int getRedColor(){
        return colorRangeSensor.red();
    }

    public int getGreenColor(){
        return colorRangeSensor.green();
    }

    public int getBlueColor(){
        return colorRangeSensor.blue();
    }

    public double getDistanceInMM() {
        return colorRangeSensor.getDistance(DistanceUnit.MM);
    }
    public double getDistanceInCM() {
        return colorRangeSensor.getDistance(DistanceUnit.CM);
    }
    public double getDistanceInMeter() {
        return colorRangeSensor.getDistance(DistanceUnit.METER);
    }
    public double getDistanceInInches() {
        return colorRangeSensor.getDistance(DistanceUnit.INCH);
    }
}

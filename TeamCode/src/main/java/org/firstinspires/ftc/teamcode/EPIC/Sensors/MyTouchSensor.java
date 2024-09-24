package org.firstinspires.ftc.teamcode.EPIC.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MyTouchSensor {
    private TouchSensor sensor = null;
    public LinearOpMode parent = null;
    public Telemetry telemetry = null;
    public MyTouchSensor(HardwareMap hardwareMap)
    {
        sensor = hardwareMap.get(TouchSensor.class,"touchSensor0");
    }

    public void initialize(){

    }

    public boolean isPressed(){
        return sensor.isPressed();
    }

    public double getValue() {
        return sensor.getValue();
    }
}

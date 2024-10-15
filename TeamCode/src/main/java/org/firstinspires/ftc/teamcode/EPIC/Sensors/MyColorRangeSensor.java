package org.firstinspires.ftc.teamcode.EPIC.Sensors;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ColorEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IColorListener;

import java.util.ArrayList;
import java.util.List;

public class MyColorRangeSensor {
    public LinearOpMode parent = null;
    public Telemetry telemetry = null;
    //private ColorRangeSensor colorRangeSensor = null;
    private ColorSensor colorRangeSensor = null;
    private List<IColorListener> listeners;
    private DistanceSensor ds = null;

    private String alliance;
    public MyColorRangeSensor(HardwareMap hardwareMap,String alliance){
        //colorRangeSensor = hardwareMap.get(ColorRangeSensor.class,"color0");
        colorRangeSensor = hardwareMap.get(ColorSensor.class,"color0");
        ds = (DistanceSensor)colorRangeSensor;
        this.listeners = new ArrayList<>();
        this.alliance = alliance;
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

    public void getColor() {
        if (colorRangeSensor.argb()== Color.argb(0,255,255,0)){
            ColorEventObject ceo = new ColorEventObject(this,alliance,this.getDistanceInMM(),getARGBColor());
            this.fireColorPicker(ceo);
        }
        else if ("Red" == alliance && Math.abs( colorRangeSensor.argb())> 100000000 && Math.abs(colorRangeSensor.argb()) < 240000000){
        //&& colorRangeSensor.argb() == Color.argb(0,255,0,0)){
            ColorEventObject ceo = new ColorEventObject(this,alliance,this.getDistanceInInches(),getARGBColor());
            this.fireColorPicker(ceo);
        }
        else if ("Blue" == alliance && colorRangeSensor.argb() == Color.argb(0,0,0,255)){
            ColorEventObject ceo = new ColorEventObject(this,alliance,this.getDistanceInInches(),getARGBColor());
            this.fireColorPicker(ceo);
        }
    }

    public double getDistanceInMM() {
        return ds.getDistance(DistanceUnit.MM);
    }



    public double getDistanceInCM() {
        return ds.getDistance(DistanceUnit.CM);

    }
    public double getDistanceInMeter() {
        return ds.getDistance(DistanceUnit.METER);
    }
    public double getDistanceInInches() {
        return ds.getDistance(DistanceUnit.INCH);
    }

    public void addColorListener(IColorListener listener) {
        listeners.add(listener);
    }

    public void removeColorListener(IColorListener listener) {
        listeners.remove(listener);
    }

    private void fireColorPicker(ColorEventObject event) {
        for (IColorListener listener : listeners) {
            listener.colorPicker(event);
        }
    }
}

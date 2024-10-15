package org.firstinspires.ftc.teamcode.EPIC.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ColorEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IColorListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ITouchListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.TouchEventObject;

import java.util.ArrayList;
import java.util.List;
public class MyTouchSensor {
    private TouchSensor sensor = null;
    public LinearOpMode parent = null;
    public Telemetry telemetry = null;
    private List<ITouchListener> listeners;
    public MyTouchSensor(HardwareMap hardwareMap)
    {
        sensor = hardwareMap.get(TouchSensor.class,"touchSensor0");

        this.listeners = new ArrayList<>();
    }

    public void initialize(){

    }

    public boolean isPressed(){
        return sensor.isPressed();

    }

    public void checkButtonPressed(){
        TouchEventObject teo = new TouchEventObject(this,sensor.isPressed());
        this.fireButtonPressed(teo);
    }

    public double getValue() {
        return sensor.getValue();
    }


    public void addTouchListener(ITouchListener listener) {
        listeners.add(listener);
    }

    public void removeTouchListener(IColorListener listener) {
        listeners.remove(listener);
    }

    private void fireButtonPressed(TouchEventObject event) {
        for (ITouchListener listener : listeners) {
            listener.touchClicked(event);
        }
    }
}

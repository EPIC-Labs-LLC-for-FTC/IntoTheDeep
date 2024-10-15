package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import java.util.EventObject;

public class ColorEventObject extends EventObject {
    private String alliance;
    private int color;
    private double distance;
    public void ColorPickerEvent(int color){

    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ColorEventObject(Object source,String alliance, double distance, int color) {
        super(source);
        this.alliance = alliance;
        this.color = color;
        this.distance = distance;
    }

    public double getDistance(){
        return distance;
    }

    public int getColor(){
        return color;
    }
}

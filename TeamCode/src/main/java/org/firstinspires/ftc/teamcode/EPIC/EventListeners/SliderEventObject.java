package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

import java.util.EventObject;

public class SliderEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private final SliderStates newState;

    // Constructor
    public SliderEventObject(Object source, SliderStates newState) {
        super(source);
        this.newState = newState;
    }

    public SliderStates getSliderState() {
        return newState;
    }
}

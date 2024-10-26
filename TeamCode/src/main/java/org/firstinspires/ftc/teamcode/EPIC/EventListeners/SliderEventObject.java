package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;

public class SliderEventObject {
    private final Slider source;
    private final SliderStates newState;

    // Constructor
    public SliderEventObject(Slider source, SliderStates newState) {
        this.source = source;
        this.newState = newState;
    }

    // Method to get the source of the event
    public Slider getSource() {
        return source;
    }

    // Method to get the new state of the slider
    public SliderStates getSliderState() {
        return newState;
    }
}

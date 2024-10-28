package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

import java.util.EventObject;

public class WristEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private WristStates newState;

    public WristEventObject(Object source, WristStates newState) {
        super(source);
        this.newState = newState;
    }

    public WristStates getNewState() {
        return newState;
    }
}



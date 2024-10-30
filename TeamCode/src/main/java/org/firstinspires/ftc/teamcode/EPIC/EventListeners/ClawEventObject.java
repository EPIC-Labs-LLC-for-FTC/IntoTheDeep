package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;

import java.util.EventObject;

public class ClawEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    ClawStates newState;
    public ClawEventObject(Object source, ClawStates state) {
        super(source);
        this.newState = state;
    }

    public ClawStates getNewState() {
        return newState;
    }
}

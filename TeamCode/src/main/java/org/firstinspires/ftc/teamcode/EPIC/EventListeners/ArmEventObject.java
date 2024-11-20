package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

import java.util.EventObject;

public class ArmEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private ArmStates newState;

    public ArmEventObject(Object source, ArmStates newState) {
        super(source);
        this.newState = newState;
    }

    public ArmStates getNewState() {
        return newState;
    }
}


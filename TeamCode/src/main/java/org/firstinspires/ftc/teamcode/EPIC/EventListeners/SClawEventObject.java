package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.SpecimenClaw;

import java.util.EventObject;

public class SClawEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private final SpecimenClaw.SClawStates newState;

    public SClawEventObject(Object source, SpecimenClaw.SClawStates state) {
        super(source);
        this.newState = state;
    }

    public SpecimenClaw.SClawStates getNewState() {
        return this.newState;
    }
}

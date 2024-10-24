package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

public class ArmEventObject {
    private Arm source;
    private ArmStates newState;

    public ArmEventObject(Arm source, ArmStates newState) {
        this.source = source;
        this.newState = newState;
    }

    public Arm getSource() {
        return source;
    }

    public ArmStates getNewState() {
        return newState;
    }
}


package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.DriveStates;

import java.util.EventObject;

public class MecanumEventObject extends EventObject {
    private DriveStates newState;

    public MecanumEventObject(Object source, DriveStates state) {
        super(source);
        this.newState = state;
    }

    public DriveStates getNewState() {
        return this.newState;
    }
}

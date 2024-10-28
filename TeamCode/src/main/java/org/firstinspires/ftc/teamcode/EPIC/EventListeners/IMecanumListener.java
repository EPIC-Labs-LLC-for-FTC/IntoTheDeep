package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.DriveStates;

import java.util.EventListener;

public interface IMecanumListener extends EventListener {
    public void mecanumActivity (MecanumEventObject event);
}

package org.firstinspires.ftc.teamcode.EPIC.Components;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

public interface IArm {
    void freeMove(double speed);
    void move(ArmStates state, double timeOutS);
}

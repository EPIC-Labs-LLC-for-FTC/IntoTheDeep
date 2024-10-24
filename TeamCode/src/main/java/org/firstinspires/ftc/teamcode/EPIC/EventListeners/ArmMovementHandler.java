package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;

public class ArmMovementHandler implements IArmListener {
    @Override
    public void onArmMove(ArmEventObject event) {
        ArmStates newState = event.getNewState();
        switch (newState) {
            case LOWERED:
                System.out.println("Arm is in lowered position, ready to grab sample.");
                break;
            case LOWERED_BACK:
                System.out.println("Arm is in lowered back position, ready to deposit.");
                break;
            case NEUTRAL:
                System.out.println("Arm is back to neutral after depositing.");
                break;
            default:
                System.out.println("Arm moved to an unknown state.");
                break;
        }
    }
}


package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import java.util.EventObject;

public class ClawEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private double leftFingerPosition = 0;
    private double rightFingerPosition = 0;
    public ClawEventObject(Object source,double leftFingerPosition, double rightFingerPosition) {
        super(source);
        this.leftFingerPosition = leftFingerPosition;
        this.rightFingerPosition = rightFingerPosition;
    }

    public double getRightFingerPosition() {
        return rightFingerPosition;
    }

    public double getLeftFingerPosition() {
        return leftFingerPosition;
    }
}

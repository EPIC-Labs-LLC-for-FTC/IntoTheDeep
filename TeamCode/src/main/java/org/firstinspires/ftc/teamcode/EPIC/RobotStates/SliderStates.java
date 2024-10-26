package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum                                                                                                                  SliderStates {
    HIGH_BUCKET(25.0),
    LOW_BUCKET(9.75),
    LOW_HANG(2),
    RETRACTED(0.0),
    MOVING(-1.0); // Added MOVING state

    private double height;

    SliderStates(double height) {
        this.height = height;
    }

    public double getStateHeight() {
        return this.height;
    }

    public void setStateHeight(double height) {
        this.height = height;
    }
}

package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum                                                                                                                  SliderStates {
    HIGH_BUCKET(2250),
    LOW_HANG(1),
    LOW_HANG_START(850),
    RETRACTED(0.0);
    //Positions need to be redone

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

package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum                                                                                                                  SliderStates {
    HIGH_BUCKET(3650),
    LOW_HANG(1),
    LOW_HANG_START(1750),
    SPECIMEN_HIGH(1825),
    SPECIMEN_LOW(1700),
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

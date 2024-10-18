package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum SliderStates {
    HIGH_BUCKET(43.0),
    LOW_BUCKET(27.75),
    LOW_HANG(20.0),
    RETRACTED(0.0);


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

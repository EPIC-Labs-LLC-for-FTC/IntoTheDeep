package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum WristStates {
    PICKING_UP_SAMPLE(0),
    DEPOSITING_SAMPLE(0.29),
    NEUTRAL(0.1),
    INITIALIZING(0.45);

    private double pos;

    WristStates(double pos) {
        this.pos = pos;
    }

    public void setPos(double pos){
        this.pos = pos;
    }

    public double getPos() {
        return this.pos;
    }
}




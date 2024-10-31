package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum WristStates {
    PICKING_UP_SAMPLE(0.6),
    DEPOSITING_SAMPLE(0.325),
    NEUTRAL(0.1),
    INITIALIZING(0.15);

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




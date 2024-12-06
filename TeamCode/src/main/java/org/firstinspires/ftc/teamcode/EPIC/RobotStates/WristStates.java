package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum WristStates {
    PICKING_UP_SAMPLE(1),
    DEPOSITING_SAMPLE(0),
    NEUTRAL(0.5),
    INITIALIZING(1),
    DEPOSITING_SPECIMEN(0); //find this value

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




package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum WristStates {
    PICKING_UP_SAMPLE(1),
    DEPOSITING_SAMPLE(0),
    NEUTRAL(0.5),
    INITIALIZING(1),
    SPECIMEN_PICK(0.380);

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




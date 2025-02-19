package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum WristStates {


    PICKING_UP_SAMPLE(1),
    DEPOSITING_SAMPLE(0),
    NEUTRAL(0.5),
    INITIALIZING(0.1),
    INITIALIZING_AUTON(0.1),
    SPECIMEN_PICK(0.38),
    SPECIMEN_DROP(0.55),
    SPECIMEN_DROP_AUTON(0.55);//0.380

    //orignal pick is 0.38



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




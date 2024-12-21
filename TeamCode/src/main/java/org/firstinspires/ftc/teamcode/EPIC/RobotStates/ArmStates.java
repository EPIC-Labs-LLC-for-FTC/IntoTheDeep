package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    READY_TO_DEPOSIT(300),   //change
    DEPOSITING(20),
    //LOWERED(190),
    LOWERED(525),
    INITIALIZED(0.0),
    NEUTRAL(250),
    SPECIMEN_PICK(400), //change
    SPECIMEN_DROP(200);


    private double stateNum;

    ArmStates(double state) {
        this.stateNum = state;
    }

    public double getState() {
        return this.stateNum;
    }

    public void setState(double stateNum) {
        this.stateNum = stateNum;
    }
}

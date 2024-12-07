package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    READY_TO_DEPOSIT(300),
    DEPOSITING(20),
    //LOWERED(190),
    LOWERED(500),
    INITIALIZED(0.0),
    NEUTRAL(250),
    SPECIMEN_PICK(400), SPECIMEN_DROP(200); //check the value


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

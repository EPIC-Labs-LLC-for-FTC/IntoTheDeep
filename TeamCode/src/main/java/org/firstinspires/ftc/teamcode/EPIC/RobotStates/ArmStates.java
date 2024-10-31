package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    READY_TO_DEPOSIT(300),
    DEPOSITING(70),
    LOWERED(190),
    INITIALIZED(0.0),
    NEUTRAL(500); //check the value

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

package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    DEPOSITING(-20.0),
    LOWERED(120.0),
    NEUTRAL(0.0);

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

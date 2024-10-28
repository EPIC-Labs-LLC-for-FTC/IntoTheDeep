package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    DEPOSITING(10),
    LOWERED(190),
    INITIALIZED(0.0); //check the value

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

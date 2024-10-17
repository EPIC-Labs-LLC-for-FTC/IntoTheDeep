package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {
    ARM_DEPOSITING(-20.0),
    ARM_LOWERED(120.0),
    ARM_NEUTRAL(0.0);

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

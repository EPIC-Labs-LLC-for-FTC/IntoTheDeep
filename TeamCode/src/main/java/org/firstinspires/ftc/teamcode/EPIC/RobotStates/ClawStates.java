package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ClawStates {
    HOLDING_SAMPLE_PORTRAIT (0.275),
    OPEN(0);

    private double clawPos;

    ClawStates (double value) {
        this.clawPos = value;
    }

    public void setClawPos(double value) {
        this.clawPos = value;
    }

    public double getClawPos() {
        return this.clawPos;
    }
}

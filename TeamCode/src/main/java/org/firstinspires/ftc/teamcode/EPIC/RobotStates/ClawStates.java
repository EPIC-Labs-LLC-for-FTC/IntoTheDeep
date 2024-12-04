package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ClawStates {
    HOLDING_SAMPLE_PORTRAIT (0.5),
    HOLDING_SAMPLE_LANDSCAPE(0.62),
    OPEN(0.85);

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

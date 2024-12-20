package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ClawStates {
    HOLDING_SAMPLE_PORTRAIT (0.45),
    HOLDING_SAMPLE_LANDSCAPE(0.30),
    OPEN(0.13);

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

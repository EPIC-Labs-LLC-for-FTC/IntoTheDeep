package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ClawStates {
   HOLDING_SAMPLE_PORTRAIT (0),
 OPEN(0.25);
//HOLDING_SAMPLE_PORTRAIT (0.8),
 //   OPEN(1);

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

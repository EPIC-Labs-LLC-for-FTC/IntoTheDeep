package org.firstinspires.ftc.teamcode.EPIC.RobotStates;

public enum ArmStates {

    AUTON_ARM_UP(40),
    AUTON_BUCKET_DROP(100),
    READY_TO_DEPOSIT(315),
    DEPOSITING(60),
    LOWERED(525),
    AUTON_LOWERED(455), // 440
    INITIALIZED(0.0),
    NEUTRAL(250),
    SPECIMEN_PICK(397),
    SPECIMEN_DROP(200),
    AUTON_SPECIMEN_DROP(220), // initally 210

    SPECIMEN_DROP2(220),
    HOLDING_SAMPLE(450);

    //original reayd to deposit is  300 not 310


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

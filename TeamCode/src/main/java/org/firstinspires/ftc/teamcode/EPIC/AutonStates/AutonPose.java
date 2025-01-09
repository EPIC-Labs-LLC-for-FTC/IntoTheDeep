package org.firstinspires.ftc.teamcode.EPIC.AutonStates;

import com.acmerobotics.roadrunner.Vector2d;

public enum AutonPose {
    BUCKET_B(new Vector2d(60, 60)),
    BUCKET_R(new Vector2d(-60, -60)),
    OBSERVATION_B(new Vector2d(-60, 60)),
    OBSERVATION_R(new Vector2d(60, -60)),
    SPECIMEN_HANG_B(new Vector2d(0, 32)),
    SPECIMEN_HANG_R(new Vector2d(8.3, -41.26)),
    SAMPLE_GRAB_L(new Vector2d(-24, 0)),
    SAMPLE_GRAB_R(new Vector2d(24, 0)),
    START_POS_RR(new Vector2d(8.25, -63.85)), //This is the starting position in LocalizationTest
    START_POS_RL(new Vector2d(-8.25, -63.85)),
    START_POS_BR(new Vector2d(-8.25, 63.85)),
    START_POS_BL(new Vector2d(8.25, 63.85)),
    SAMPLE_FIRST_PUSH(new Vector2d (-24.37 , 41.8));
    ////SAMPLE_FIRST_DEPOSIT(new Vector2d (, , )),



   // SAMPLE_SECOND_PUSH(new Vector2d(,)),
//    SAMPLE_SECOND_DEPOSIT(new Vector2d(,)),
//    SAMPLE_THIRD_PUSH(new Vector2d(,)),
//    SAMPLE_THIRD_DEOPSIT(new Vector2d(,)
//);

    //Current Positions are placeholders and must be fine-tuned through LocalizationTest

    private Vector2d vector;

    private AutonPose(Vector2d vector2d) {
        this.vector = vector2d;
    }

    public Vector2d getVector() {
        return this.vector;
    }

    public void setVector(Vector2d vector) {
        this.vector = vector;
    }
}


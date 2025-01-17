package org.firstinspires.ftc.teamcode.EPIC.AutonStates;

import com.acmerobotics.roadrunner.Vector2d;

public enum AutonPose {
    BUCKET_B(new Vector2d(65.6, 31.6)),
    BUCKET_R(new Vector2d(-43.6, -54.4)),
    SPECIMEN_HANG_B(new Vector2d(11.3, 19.6)),
    SPECIMEN_HANG_R(new Vector2d(8.3, -41.26)),
    SAMPLE_GRAB_L(new Vector2d(-11.5, -4)),
    SAMPLE_GRAB_R(new Vector2d(24.5, -23)),
    START_POS_RR(new Vector2d(8.25, -63.85)), //This is the starting position in LocalizationTest
    START_POS_RL(new Vector2d(-7.4, -63.85)),
    START_POS_BR(new Vector2d(11.7, 40.7)),
    START_POS_BL(new Vector2d(26.5, 40.7)),
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


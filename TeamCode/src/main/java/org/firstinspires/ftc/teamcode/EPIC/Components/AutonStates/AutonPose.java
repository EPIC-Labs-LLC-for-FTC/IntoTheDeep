package org.firstinspires.ftc.teamcode.EPIC.Components.AutonStates;

import com.acmerobotics.roadrunner.Vector2d;

public enum AutonPose {
    BUCKET_B(new Vector2d(60, 60)),
    BUCKET_R(new Vector2d(-60, -60)),
    OBSERVATION_B(new Vector2d(-60, 60)),
    OBSERVATION_R(new Vector2d(60, -60)),
    SPECIMEN_HANG_B(new Vector2d(0, 32)),
    SPECIMEN_HANG_R(new Vector2d(0, -32)),
    SAMPLE_GRAB_L(new Vector2d(-24, 0)),
    SAMPLE_GRAB_R(new Vector2d(24, 0));
    //Current Positions are placeholders and must be finetuned through LocalizationTest

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


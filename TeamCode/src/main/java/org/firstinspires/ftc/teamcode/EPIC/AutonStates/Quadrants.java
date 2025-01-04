package org.firstinspires.ftc.teamcode.EPIC.AutonStates;

import com.acmerobotics.roadrunner.Vector2d;

public enum Quadrants {
    QUADRANT_I,
    QUADRANT_II,
    QUADRANT_III,
    QUADRANT_IV;

    public boolean inQuadrant(Vector2d vector, Quadrants quadrant) {
        if (quadrant == QUADRANT_I) {
            return vector.x > 0 && vector.y > 0;
        } else if (quadrant == QUADRANT_II) {
            return vector.x < 0 && vector.y > 0;
        } else if (quadrant == QUADRANT_III) {
            return vector.x < 0 && vector.y < 0;
        } else if (quadrant == QUADRANT_IV) {
            return vector.x > 0 && vector.y < 0;
        } else {
            return false;
        }
    }

    public Quadrants checkQuadrant(Vector2d vector) {
        if (vector.x > 0 && vector.y > 0) {
            return QUADRANT_I;
        } else if (vector.x < 0 && vector.y > 0) {
            return QUADRANT_II;
        } else if (vector.x < 0 && vector.y < 0) {
            return QUADRANT_III;
        } else if (vector.x > 0 && vector.y < 0) {
            return QUADRANT_IV;
        } else {
            return null;
        }
    }
}

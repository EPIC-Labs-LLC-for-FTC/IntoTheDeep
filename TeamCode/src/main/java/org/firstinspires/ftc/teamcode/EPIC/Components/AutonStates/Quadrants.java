package org.firstinspires.ftc.teamcode.EPIC.Components.AutonStates;

import com.acmerobotics.roadrunner.Vector2d;

public enum Quadrants {
    QUADRANT_I,
    QUADRANT_II,
    QUADRANT_III,
    QUADRANT_IV;

    public boolean inQuadrant(Vector2d vector, Quadrants quadrant) {
        if (quadrant == QUADRANT_I) {
            if (vector.x > 0 && vector.y > 0) {
                return true;
            } else {
                return false;
            }
        } else if (quadrant == QUADRANT_II) {
            if (vector.x < 0 && vector.y > 0) {
                return true;
            } else {
                return false;
            }
        } else if (quadrant == QUADRANT_III) {
            if (vector.x < 0 && vector.y < 0) {
                return true;
            } else {
                return false;
            }
        } else if (quadrant == QUADRANT_IV) {
            if (vector.x > 0 && vector.y < 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

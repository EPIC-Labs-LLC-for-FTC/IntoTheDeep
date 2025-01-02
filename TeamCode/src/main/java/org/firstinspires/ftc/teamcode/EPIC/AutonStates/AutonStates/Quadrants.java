package org.firstinspires.ftc.teamcode.EPIC.AutonStates.AutonStates;

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
}

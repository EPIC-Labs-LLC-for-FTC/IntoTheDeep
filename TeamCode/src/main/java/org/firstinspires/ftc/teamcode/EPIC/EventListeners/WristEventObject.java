package org.firstinspires.ftc.teamcode.EPIC.EventListeners;

import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;

    public class WristEventObject {
        private Wrist source;
        private WristStates newState;

        public WristEventObject(Wrist source, WristStates newState) {
            this.source = source;
            this.newState = newState;
        }

        public Wrist getSource() {
            return source;
        }

        public WristStates getNewState() {
            return newState;
        }
    }



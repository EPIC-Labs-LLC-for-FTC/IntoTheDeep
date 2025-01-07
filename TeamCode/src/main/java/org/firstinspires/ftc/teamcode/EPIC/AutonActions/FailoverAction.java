package org.firstinspires.ftc.teamcode.EPIC.AutonActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class FailoverAction implements Action {
    private final Action mainAction;
    private final Action failoverAction;
    private boolean failedOver = false;

    public FailoverAction(Action mainAction, Action failoverAction) {
        this.mainAction = mainAction;
        this.failoverAction = failoverAction;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if (failedOver) {
            return failoverAction.run(telemetryPacket);
        }

        mainAction.run(telemetryPacket);
        return true;
    }

    public void failOver() {
        failedOver = true;
    }
}

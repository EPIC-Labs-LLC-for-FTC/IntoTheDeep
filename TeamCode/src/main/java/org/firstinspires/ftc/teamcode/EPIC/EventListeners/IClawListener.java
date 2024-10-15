package org.firstinspires.ftc.teamcode.EPIC.EventListeners;
import java.util.EventListener;
public interface IClawListener extends EventListener {
    void openClaw(ClawEventObject event);
    void closeClaw(ClawEventObject event);
}
package org.firstinspires.ftc.teamcode.EPIC.EventListeners;
import java.util.EventObject;
public class TouchEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    Boolean buttonStatus = false;
    public TouchEventObject(Object source,Boolean buttonStatus) {
        super(source);
        this.buttonStatus = buttonStatus;
    }

    public Boolean getButtonStatus() {
        return buttonStatus;
    }
}

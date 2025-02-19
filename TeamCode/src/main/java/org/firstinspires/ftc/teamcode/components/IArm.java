package org.firstinspires.ftc.teamcode.components;

public interface IArm {
    void armStart();
    void armRest();

    void armPick();
    void armDrop();

    void specimenPick();
    void specimenDrop();

    void armBaseUp();
    void armBaseDown();
}

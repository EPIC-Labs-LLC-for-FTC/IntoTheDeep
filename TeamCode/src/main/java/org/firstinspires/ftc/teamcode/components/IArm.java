package org.firstinspires.ftc.teamcode.components;

public interface IArm {
    void armStart();
    void armRest();
    void armRest2();

    void armPick();
    void armDrop();

    void specimenPick();
    void specimenReadyDrop();
    void specimenDrop();
    void specimenAutoDrop();

    void armBaseUp();
    void armBaseDown();
}

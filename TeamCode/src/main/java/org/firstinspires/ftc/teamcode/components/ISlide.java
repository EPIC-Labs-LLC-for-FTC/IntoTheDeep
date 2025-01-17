package org.firstinspires.ftc.teamcode.components;

public interface ISlide {
    void start();

    void lowBar();
    void HighBar();

    void LowBucket();
    void HighBucket();

    void moveUp(int target);
    void moveDown(int target);

    void hang();
}

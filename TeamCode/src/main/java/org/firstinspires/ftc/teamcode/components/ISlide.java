package org.firstinspires.ftc.teamcode.components;

public interface ISlide {
    void start();

    void lowBar();
    void HighBar();

    void LowBucket();
    void HighBucket();

    void moveTo(int target);

    void hang();
}

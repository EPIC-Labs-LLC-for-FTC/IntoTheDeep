package org.firstinspires.ftc.teamcode.PedroPathing.Constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        OTOSConstants.useCorrectedOTOSClass = true;
        OTOSConstants.hardwareMapName = "SparkFunOTOS Corrected";
        OTOSConstants.linearUnit = DistanceUnit.INCH;
        OTOSConstants.angleUnit = AngleUnit.RADIANS;
        OTOSConstants.offset = new SparkFunOTOS.Pose2D(0.15, 0, Math.toRadians(-90));
        OTOSConstants.linearScalar = 1.04077685;
        OTOSConstants.angularScalar = 0.9815;
    }
}
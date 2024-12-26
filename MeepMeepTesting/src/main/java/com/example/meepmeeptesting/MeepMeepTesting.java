package com.example.meepmeeptesting;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .setDimensions(16.5, 16.3)// length
                .setStartPose(new Pose2d(8.25, -63.85, Math.toRadians(90))) // north facing
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(myBot.getPose())

                .lineToY(-32.15)
                .strafeTo(new Vector2d(15, -32.15))

               .splineToConstantHeading(new Vector2d(42, -9), Math.toRadians(90))
//
//
//                .lineToY(-52)
//
//                .splineTo(new Vector2d(51, -9), Math.toRadians(90))
//                .lineToY(-52)
//
//                .splineTo(new Vector2d(61, -9), Math.toRadians(90))
//
//
//                .lineToY(-52)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
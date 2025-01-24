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

public class MeepTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 15.75)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .setDimensions(15,18)
                //LEFT SIDE AUTO
                .setStartPose(new Pose2d(-14.3, -63, Math.toRadians(90)))
                //RIGHT SIDE AUTO
//                .setStartPose(new Pose2d(14.3, -63, Math.toRadians(90)))
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(myBot.getPose())

                //RIGHT SIDE AUTO

//                .setTangent(90)
//                .splineToConstantHeading(new Vector2d(9,-38), Math.PI/2)
//                .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                .strafeToConstantHeading(new Vector2d(36,-40))
//                .strafeToConstantHeading(new Vector2d(36,-10))
//
//                .strafeToConstantHeading(new Vector2d(48,-10))
//                .strafeToConstantHeading(new Vector2d(48,-65))
//
//                .strafeToConstantHeading(new Vector2d(48,-10))
//                .strafeToConstantHeading(new Vector2d(57,-10))
//                .strafeToConstantHeading(new Vector2d(57,-65))
//
//                .strafeToConstantHeading(new Vector2d(57,-10))
//                .strafeToConstantHeading(new Vector2d(68,-10))
//                .strafeToConstantHeading(new Vector2d(68,-65))
//
//                .strafeToConstantHeading(new Vector2d(60,-42))
//                .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                .strafeToConstantHeading(new Vector2d(9,-40))
//                .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                .strafeToConstantHeading(new Vector2d(9,-40))
//                .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                .strafeToConstantHeading(new Vector2d(48,-65))


                //LEFT SIDE AUTO

                .setTangent(90)
                .splineToConstantHeading(new Vector2d(-9,-38), Math.PI/2)
                .strafeToConstantHeading(new Vector2d(-28,-53))
                .strafeToLinearHeading(new Vector2d(-28,-33),Math.PI)
                .strafeToLinearHeading(new Vector2d(-41,-47),Math.PI*1.25)
                .strafeToConstantHeading(new Vector2d(-39,-45))
                .strafeToLinearHeading(new Vector2d(-33,-28.5),Math.PI)
                .strafeToLinearHeading(new Vector2d(-40,-45),Math.PI*1.15)
                .strafeToConstantHeading(new Vector2d(-34,-41))
                .strafeToLinearHeading(new Vector2d(-36,-28.5),Math.PI)
                .strafeToLinearHeading(new Vector2d(-40,-45),Math.PI*1.15)
                .strafeToConstantHeading(new Vector2d(-34,-41))
                .strafeToLinearHeading(new Vector2d(-36,-34),Math.PI)
                .splineToLinearHeading(new Pose2d(-20,-15,0), Math.PI/2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
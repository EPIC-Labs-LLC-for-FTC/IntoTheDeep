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
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .setDimensions(18,18)
                .setStartPose(new Pose2d(9, -63, Math.toRadians(90)))
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(myBot.getPose())
//                .lineToX(30)
//                .turn(Math.toRadians(90))
                .lineToY(-37.4)
                //.stopAndAdd() //specimen delivery action here
//                        //.turn(Math.toRadians(-90))
//                        //.lineToX(10)
                .strafeTo(new Vector2d(10,-37.4))
                //.turn(Math.toRadians(-60))
                .splineToConstantHeading(new Vector2d(42,-9),Math.toRadians(90))

                //.splineToSplineHeading(new Pose2d(42,-9,Math.toRadians(90)),Math.toRadians(90))
                //.splineToSplineHeading(new Pose2d(43,-9,Math.toRadians(60)),Math.toRadians(90))
//                .strafeTo(new Vector2d(42,-9))
//                                //.splineTo(new Vector2d(41,-9),Math.toRadians(-90))
//                .turn(Math.toRadians(1))
                .lineToY(-52)
//                        .lineToY(-20)
//                .strafeTo(new Vector2d(52,-9))
//                .turn(Math.toRadians(1))
//                .lineToY(-52)
//                .lineToY(-20)
//                .strafeTo(new Vector2d(61,-9))
//                .turn(Math.toRadians(-2))
////
                .splineTo(new Vector2d(51,-9),Math.toRadians(90))
////                        //.strafeTo(new Vector2d(55,-9))
//////                        .lineToX(55)
//////                //.splineTo(new Vector2d(55,-9),Math.toRadians(0))
                .lineToY(-52)
////
////                .lineToY(-25)
//                .splineTo(new Vector2d(40,-48),Math.toRadians(-270))
//                                .stopAndAdd(new Action() {
//                                    @Override
//                                    public boolean run(@NotNull TelemetryPacket telemetryPacket) {
//                                        try {
//                                            Thread.sleep(2000);
//                                        } catch (InterruptedException e) {
//                                            throw new RuntimeException(e);
//                                        }
//                                        return false;
//                                    }
//                                })
//                //action
//                .splineTo(new Vector2d(8,-37.4),Math.toRadians(-180))
//                //action
//                .splineTo(new Vector2d(40,-48),Math.toRadians(-180))
//                //action
//                .splineTo(new Vector2d(8,-37.4),Math.toRadians(180))
//                //action
//                .splineTo(new Vector2d(40,-48),Math.toRadians(180))
//                //action
//                .splineTo(new Vector2d(8,-37.4),Math.toRadians(180))
                .splineTo(new Vector2d(61,-9),Math.toRadians(90))
//////                .lineToY(-9)
//////                .lineToX(65)
//////
////////                .splineTo(new Vector2d(65,-9),Math.toRadians(0))
                .lineToY(-52)
//////                .lineToX(0)
//////                .turn(Math.toRadians(90))
//////                .lineToY(0)
//////                .turn(Math.toRadians(90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
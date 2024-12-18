package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14.058558292784566)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(4.1, -69.2, Math.toRadians(90)))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(4.1, -42.5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(4.1, -53), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(47, -47), Math.toRadians(270))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-5, -50), Math.toRadians(270))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-5, -39), Math.toRadians(270))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
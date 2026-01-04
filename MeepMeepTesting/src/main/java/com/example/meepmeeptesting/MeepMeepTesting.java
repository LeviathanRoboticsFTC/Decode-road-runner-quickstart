package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 70, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(62,-16, Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(50, -14), Math.toRadians(202))
                .splineToLinearHeading(new Pose2d(62,-44, Math.toRadians(270)), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(62,-62),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(62,-44),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(50, -14), Math.toRadians(202))
                .splineToLinearHeading(new Pose2d(62,-44, Math.toRadians(270)), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(62,-62),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(62,-44),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(62,-62),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(62,-44),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(50, -14), Math.toRadians(202))


                .build());

        Image img = null;
        try { img = ImageIO.read(new File("/Users/evanhuang/Downloads/field.png")); }
        catch(IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
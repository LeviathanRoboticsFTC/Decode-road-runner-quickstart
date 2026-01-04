package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;


@TeleOp
public class MecanumDriveTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        waitForStart();

        if(isStopRequested()) return;
        while (!isStopRequested() && opModeIsActive()) {
            Action turn90 = drive.actionBuilder(drive.localizer.getPose())
                    .turn(Math.toRadians(90))
                    .build();


            if (gamepad1.crossWasPressed()) {
                Actions.runBlocking(turn90);
            }
        }
    }
}

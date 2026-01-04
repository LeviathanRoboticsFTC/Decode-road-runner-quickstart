package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.ProgrammingBoardV1;

@Autonomous
public class AutoBlueBottom extends LinearOpMode {
    private ProgrammingBoardV1 board;


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(62,-16, Math.toRadians(270)));
        board = new ProgrammingBoardV1(hardwareMap);
        Action shotOne = drive.actionBuilder(new Pose2d(62,-16,Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(50, -14), Math.toRadians(202))
                .build();
        Action moveOut = drive.actionBuilder(new Pose2d(50, - 14, Math.toRadians(202)))
                .strafeToLinearHeading(new Vector2d(60,-35), Math.toRadians(180))
                .build();


        waitForStart();
        if (isStopRequested()) return;

        while(!isStopRequested() && opModeIsActive()) {
            board.flywheel.setVelocity(0.9*1600);
            board.runIntake(1);
            sleep(1000);
            Actions.runBlocking(shotOne);
            board.runSpindexer(1);
            board.runTransfer(1);
            sleep(1000);
            sleep(3000);
            Actions.runBlocking(moveOut);
        }
    }
}

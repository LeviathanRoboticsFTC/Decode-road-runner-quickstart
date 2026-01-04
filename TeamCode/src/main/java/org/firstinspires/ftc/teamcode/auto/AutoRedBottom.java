package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ProgrammingBoardV1;

@Autonomous
public class AutoRedBottom extends LinearOpMode {
    private ProgrammingBoardV1 board;


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(62,16, Math.toRadians(180)));
        board = new ProgrammingBoardV1(hardwareMap);
        Action shotOne = drive.actionBuilder(new Pose2d(62,16,Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(50, 14), Math.toRadians(158))
                .build();
        Action goToPickUpBalls = drive.actionBuilder(new Pose2d(50, 14,Math.toRadians(158)))
                .splineToLinearHeading(new Pose2d(62,44, Math.toRadians(90)), Math.toRadians(90))
                .build();
        Action pickUpBallsP1 = drive.actionBuilder(new Pose2d(62,44,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,62),Math.toRadians(90))
                .build();
        Action pickUpBallsP2 = drive.actionBuilder(new Pose2d(62,62,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,44),Math.toRadians(90))
                .build();
        Action goToPickUpBallsTwo = drive.actionBuilder(new Pose2d(50, 14,Math.toRadians(158)))
                .splineToLinearHeading(new Pose2d(62,44, Math.toRadians(90)), Math.toRadians(90))
                .build();
        Action pickUpBallsTwoP1 = drive.actionBuilder(new Pose2d(62,44,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,62),Math.toRadians(90))
                .build();
        Action pickUpBallsTwoP2 = drive.actionBuilder(new Pose2d(62,62,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,44),Math.toRadians(90))
                .build();
        Action pickUpBallsTwoP3 = drive.actionBuilder(new Pose2d(62,44,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,62),Math.toRadians(90))
                .build();
        Action pickUpBallsTwoP4 = drive.actionBuilder(new Pose2d(62,62,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(62,44),Math.toRadians(90))
                .build();
        Action shotTwo = drive.actionBuilder(new Pose2d(62,44,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(50, 14), Math.toRadians(158))
                .build();
        Action shotThree = drive.actionBuilder(new Pose2d(62,44,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(50, 14), Math.toRadians(158))
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
            board.runTransfer(0);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    goToPickUpBalls,
                                    pickUpBallsP1,
                                    pickUpBallsP2,
                                    shotTwo
                            )
                    )
            );
            board.runTransfer(1);
            sleep(1000);
            sleep(3000);
            board.runTransfer(0);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    goToPickUpBallsTwo,
                                    pickUpBallsTwoP1,
                                    pickUpBallsTwoP2,
                                    pickUpBallsTwoP3,
                                    pickUpBallsTwoP4,
                                    shotThree
                            )
                    )
            );

            board.runTransfer(1);
            sleep(1000);
            sleep(500000);
        }
    }
}

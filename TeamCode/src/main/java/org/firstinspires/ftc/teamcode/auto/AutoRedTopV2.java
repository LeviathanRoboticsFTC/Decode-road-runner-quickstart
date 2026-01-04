package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.ProgrammingBoardV1;

@Autonomous
public class AutoRedTopV2 extends LinearOpMode {
    private ProgrammingBoardV1 board;



    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-48,48, Math.toRadians(135)));
        board = new ProgrammingBoardV1(hardwareMap);
        Action shotOne = drive.actionBuilder(new Pose2d(-48,48,Math.toRadians(135)))
                .strafeToLinearHeading(new Vector2d(-10, 12), Math.toRadians(135))
                .build();
        Action takeBallsOneP1 = drive.actionBuilder(new Pose2d(-10, 12, Math.toRadians(135)))
                .splineToLinearHeading(new Pose2d(-10,28,Math.toRadians(90)), Math.toRadians(90))
                .build();
        Action takeBallsOneP2 = drive.actionBuilder(new Pose2d(-10, 28, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-10,54,Math.toRadians(90)), Math.toRadians(90))
                .build();
        Action shotTwo = drive.actionBuilder(new Pose2d(-10, 54, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-10,12,Math.toRadians(135)), Math.toRadians(0))
                .build();
        Action takeBallsTwoP1 = drive.actionBuilder(new Pose2d(-10, 12, Math.toRadians(135)))
                .splineToLinearHeading(new Pose2d(12,25,Math.toRadians(90)), Math.toRadians(0))
                .build();
        Action takeBallsTwoP2 = drive.actionBuilder(new Pose2d(8, 25, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(12,57),Math.toRadians(90))
                .build();
        Action shot3 = drive.actionBuilder(new Pose2d(14, 57, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-10,12),Math.toRadians(135))
                .build();
        Action takeBallsThreeP1 = drive.actionBuilder(new Pose2d(-10, 12, Math.toRadians(135)))
                .splineToLinearHeading(new Pose2d(38,25,Math.toRadians(90)), Math.toRadians(0))
                .build();
        Action takeBallsThreeP2 = drive.actionBuilder(new Pose2d(38, 25, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(38, 57), Math.toRadians(90))
                .build();
        Action shot4 = drive.actionBuilder(new Pose2d(36, 57, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(21,12), Math.toRadians(180))
                .build();
        board.flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        board.flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();
        if (isStopRequested()) return;

        while(!isStopRequested() && opModeIsActive()) {
            board.flywheel.setVelocity(0.79*1600);
            board.runSpindexer(1);
            board.runIntake(1);
            sleep(600);
            Actions.runBlocking(shotOne);
            board.runSpindexer(1);
            board.runTransfer(1);
            sleep(3100);
            board.runTransfer(0);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                takeBallsOneP1,
                                takeBallsOneP2,
                                shotTwo
                            )
                    )
            );
            board.runTransfer(1);
            sleep(950);
            sleep(1700);
            board.runTransfer(0);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    takeBallsTwoP1,
                                    takeBallsTwoP2,
                                    shot3
                            )
                    )
            );
            board.runTransfer(1);
            sleep(950);
            sleep(1700);
            board.runTransfer(0);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    takeBallsThreeP1,
                                    takeBallsThreeP2,
                                    shot4
                            )
                    )
            );

            sleep(30000);



        }
    }
}

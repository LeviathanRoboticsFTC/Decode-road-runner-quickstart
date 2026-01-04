package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ProgrammingBoards.ProgrammingBoardV1;

@TeleOp
public class TeleOpTuning extends LinearOpMode {
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        ProgrammingBoardV1 programmingBoard = new ProgrammingBoardV1(hardwareMap);
        double speed = 1;
        double xAngle = 0;


        //limelight



        waitForStart();
        if(isStopRequested()) return;
        while (!isStopRequested() && opModeIsActive()) {


            //get limelight values:






            programmingBoard.driveMotors(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
            if(gamepad2.triangleWasPressed()){
                speed = speed + 0.05;
            }else if(gamepad2.crossWasPressed()){
                speed = speed - 0.05;

            }
            programmingBoard.runFlywheel(speed);
            programmingBoard.runIntake(1);

            if(gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_stick_x == 0){
                programmingBoard.stopMotor();
            }
            if(gamepad2.right_bumper){
                programmingBoard.runTransfer(1);
            }else{
                programmingBoard.runTransfer(-1);
            }
            if(gamepad2.left_bumper){
                programmingBoard.runSpindexer(-1);
            }else{
                programmingBoard.runSpindexer(1);
            }
            telemetry.addData("speed", speed);
            telemetry.update();


        }
    }
}

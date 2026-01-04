package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Flywheel;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoardV1;
import org.firstinspires.ftc.teamcode.Spindexer;
import org.firstinspires.ftc.teamcode.Transfer;

@TeleOp
public class TeleOpTestV2 extends LinearOpMode {
    private Spindexer spindexer;
    private Intake intake;
    private Transfer transfer;

    private Flywheel flywheel;

    @Override
    public void runOpMode() throws InterruptedException {
        spindexer = new Spindexer(hardwareMap);
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        flywheel = new Flywheel(hardwareMap);


        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("current", spindexer.spindexer.getCurrentPosition());
            telemetry.addData("target", spindexer.spindexer.getTargetPosition());
            telemetry.addData("vel", flywheel.returnVel());
            telemetry.addData("red", spindexer.getRedResult());
            telemetry.addData("green", spindexer.getGreenResult());
            telemetry.addData("blue", spindexer.getBlueResult());
            telemetry.addData("touch", spindexer.touchSensor.isPressed());
            telemetry.addData("distance", spindexer.distanceSensor.getDistance(DistanceUnit.MM));
            telemetry.addData("Accepting Balls? : ", spindexer.returnShootingMode());
            telemetry.addData("balls", spindexer.ballCount);
            telemetry.update();



            if (gamepad1.leftBumperWasPressed()){
                spindexer.switchModes();
            }
            if (gamepad1.rightBumperWasPressed()){
                spindexer.rotateThird();
            }
            intake.runIntake(1);
            flywheel.runFlywheelVel(0.8);
            if(gamepad1.square){
                transfer.transferDown(1);
            }else{
                transfer.transferDown(-1);
            }


            spindexer.checkIfBall();
            if(gamepad1.circleWasPressed()){
                spindexer.resetBallCount();
            }

        }

    }
}

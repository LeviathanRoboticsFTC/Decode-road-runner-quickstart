package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ProgrammingBoardV1;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class TeleOpTestV1 extends LinearOpMode {
    private Limelight3A limelight;
    private MecanumDrive drive;
    private ProgrammingBoardV1 programmingBoard;


    @Override
    public void runOpMode() throws InterruptedException {
        programmingBoard = new ProgrammingBoardV1(hardwareMap);
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(0)));
        double distance = 0;
        double xAngle = 0;
        double speed = 0.85;


        //limelight
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        // pipeline 0 is a placeholder, put apriltag pipeline when configured
        //-v
        limelight.pipelineSwitch(0);

        limelight.start();


        waitForStart();
        if(isStopRequested()) return;
        while (!isStopRequested() && opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            //get limelight values:
            if(result != null){

                if(result.isValid()){
                    Pose3D botpose = result.getBotpose();
                    //^ is this how to get apriltag detection? Not quite sure might have to search later
                    distance = 19.5/Math.tan(Math.toRadians(result.getTy()+17.3));
                    //search up how to use with limelight
                    //double x = result.getTargetXDegrees;
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("distance", distance);
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("ta", result.getTa());
                    telemetry.addData("tag", result.getBotposeTagCount());
                    telemetry.addData("vel", programmingBoard.flywheel.getVelocity());
                    if(gamepad1.cross){
                        xAngle = result.getTx();

                        Action turnToAT = drive.actionBuilder(drive.localizer.getPose())
                                //here write the code to turn to the april tag
                                .turn(Math.toRadians(-xAngle))
                                .build();
                        Actions.runBlocking(turnToAT);
                    }else{
                        programmingBoard.driveMotors(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
                    }

                }
            }


            programmingBoard.driveMotors(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

            if(gamepad2.crossWasPressed()){
                speed = speed - 0.03;
            } else if (gamepad2.triangleWasPressed()) {
                speed = speed + 0.03;
            }
            if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_stick_x == 0){
                programmingBoard.runFlywheelVel(speed);
            }else{
                programmingBoard.runFlywheel(0.78);
            }
            programmingBoard.runIntake(1);

            if(gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_stick_x == 0){
                programmingBoard.stopMotor();
            }
            if(gamepad2.right_bumper){
                programmingBoard.runTransfer(1);
            }else{
                programmingBoard.runTransfer(0);
            }
            if(gamepad2.left_bumper){
                programmingBoard.runSpindexer(-1);
            }else{
                programmingBoard.runSpindexer(1);
            }
            telemetry.update();


        }
    }
}

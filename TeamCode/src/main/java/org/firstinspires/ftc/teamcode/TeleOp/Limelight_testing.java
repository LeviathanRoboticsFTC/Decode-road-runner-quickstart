package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp
public class Limelight_testing extends LinearOpMode {
    private Limelight3A limelight;
    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        // pipeline 0 is a placeholder, put apriltag pipeline when configured
        //-v
        limelight.pipelineSwitch(0);
        
        limelight.start();
        waitForStart();
        while (opModeIsActive()){
            LLResult result = limelight.getLatestResult();
            if(result != null){
                if(result.isValid()){
                    Pose3D botpose = result.getBotpose();
                    //^ is this how to get apriltag detection? Not quite sure might have to search later
                    double distance = 19.5/Math.tan(result.getTy());
                    double xAngle = result.getTx();
                    //search up how to use with limelight
                    //double x = result.getTargetXDegrees;


                }
            }
        }
    }
}

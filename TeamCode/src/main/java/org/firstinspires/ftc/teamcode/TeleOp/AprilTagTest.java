package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Turret;

@TeleOp
public class AprilTagTest extends LinearOpMode {
    private Turret turret;

    @Override
    public void runOpMode() throws InterruptedException {
        turret = new Turret(hardwareMap);

        waitForStart();
        if(isStopRequested()) return;
        while (!isStopRequested() && opModeIsActive()) {
            if(gamepad1.cross){
                    turret.TurnToAT();

            }else{
                turret.TurnTo(0);
            }
            telemetry.addData("angle", turret.getCurrAngle());
            Log.d("turret", ""+turret.getCurrAngle());
            Log.d("limelight", ""+turret.limelight.getLatestResult());
            LLResult result = turret.limelight.getLatestResult();

            //get limelight values:
            if (result != null) {
                if(result.isValid()) {
                    telemetry.addData("limelight angle", turret.limelight.getLatestResult().getTx());
                    if(!result.getFiducialResults().isEmpty()) {
                        telemetry.addData("tag id", result.getFiducialResults().get(0).getFiducialId());
                    }

                }
            }
            telemetry.update();
        }




    }
}

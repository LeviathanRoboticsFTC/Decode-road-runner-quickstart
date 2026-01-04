package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Log;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;


@TeleOp(name="Turret Test")
public class TurretTest extends LinearOpMode {
    private Limelight3A limelight;

    CRServo axon;
    AnalogInput analogPos;
    OverflowEncoder turretEncoder;


    static final double MAX_VOLTAGE = 3.3; // Axon encoder
    static final double TARGET_DEGREES = 400;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A .class, "Limelight");

        // pipeline 0 is a placeholder, put apriltag pipeline when configured
        limelight.pipelineSwitch(0);

        limelight.start();

        axon = hardwareMap.get(CRServo.class, "axon");
        analogPos = hardwareMap.get(AnalogInput.class, "analogPos");
        turretEncoder = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "transfer")));
        double distance = 0;
        double xAngle = 0;
        //full rotation is 10430
        while(!isStarted())
        {
            telemetry.addData("Angle", getAngle());
            telemetry.addData("Odom Pos", turretEncoder.getPositionAndVelocity().position);
            telemetry.addData("Raw Odom Pos", turretEncoder.getPositionAndVelocity().rawPosition);
            telemetry.update();
            Log.d("Angle", ""+getAngle());
            axon.setPower(0.01);
        }


        if(isStopRequested()) return;
        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Angle", getAngle());
            telemetry.update();
            Log.d("Angle", ""+getAngle());

            LLResult result = limelight.getLatestResult();

            //get limelight values:
            if (result != null) {

                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    //^ is this how to get apriltag detection? Not quite sure might have to search later

                    if (gamepad1.cross) {
                        xAngle = result.getTx();
                        if(xAngle <=2 && xAngle >= -2){
                            axon.setPower(xAngle/Math.abs(xAngle));
                        }else{
                            axon.setPower(0);
                        }
                        //turn axon to xAngle

                    } else {
                        //turn back to 0
                        if(getAngle()>180 && !(getAngle() >=352 || getAngle() <= 8)){
                            axon.setPower(0.1);

                        }else if(getAngle() < 180 && !(getAngle() >=352 || getAngle() <= 8)){
                            axon.setPower(-0.1);
                        }


                    }

                }
            }




        }
    }
    private double getAngle() {
        return (analogPos.getVoltage() / MAX_VOLTAGE) * 360.0;
    }


}

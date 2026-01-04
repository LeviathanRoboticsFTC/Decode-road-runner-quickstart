package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="axonTest")
public class axonTest extends LinearOpMode {
    private Limelight3A limelight;

    CRServo axon;
    AnalogInput analogPos;


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

        while(!isStarted())
        {
            telemetry.addData("Angle", getAngle());
            telemetry.update();
            Log.d("Angle", ""+getAngle());
            axon.setPower(0.01);
        }

        waitForStart();




        double lastAngle = getAngle();
        double totalRotation = 0;

        axon.setPower(0.1); // rotate forward

        while (opModeIsActive() && totalRotation < TARGET_DEGREES) {

            double currentAngle = getAngle();
            double delta = currentAngle - lastAngle;

            // Handle wraparound (360 → 0)
            if (delta < -180) delta += 360;
            if (delta > 180) delta -= 360;

            totalRotation += Math.abs(delta);
            lastAngle = currentAngle;

            telemetry.addData("Angle", currentAngle);
            telemetry.addData("Total Rotated", totalRotation);
            telemetry.update();

            Log.d("Angle", " "+ currentAngle);
            Log.d("Total Travelled", " "+totalRotation);

        }

        axon.setPower(0); // stop
    }

    private double getAngle() {
        return (analogPos.getVoltage() / MAX_VOLTAGE) * 360.0;
    }
}

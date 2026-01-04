package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ProgrammingBoardV1 {
    public DcMotorEx leftFront, leftBack, rightFront, rightBack, flywheel, intake, transfer;

    public CRServo spindexer;




    public ProgrammingBoardV1(HardwareMap hardwareMap) {



        leftFront = hardwareMap.get(DcMotorEx.class, "frontLeft");
        leftBack = hardwareMap.get(DcMotorEx.class, "backLeft");
        rightBack = hardwareMap.get(DcMotorEx.class, "backRight");
        rightFront = hardwareMap.get(DcMotorEx.class, "frontRight");
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
        spindexer = hardwareMap.get(CRServo.class, "spindexer");

        transfer.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    double kP = 1;
    double kD = 0;

    double setPoint = 0;

    double lastError = 0;

    public void runIntake(double speed){
        intake.setPower(speed);
    }
    public void runTransfer(double speed){
        transfer.setPower(speed);
    }
    public void runFlywheelVel(double speed){
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double kP = 0.93;
        double kI = 0.93;
        double kD = 1;
        double kF = 1;

        double integralSum = 0;
        double lastError = 0;

        ElapsedTime timer = new ElapsedTime();

        double targetVelocity = speed * 1600;
        double currentVelocity = flywheel.getVelocity();

        double error = targetVelocity - currentVelocity;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();

        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + (kF * targetVelocity);


        flywheel.setVelocity(output);


        lastError = error;
        timer.reset();
    }

    public void runFlywheel(double speed){
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setPower(speed);
    }
    public void runSpindexer(double speed){
        spindexer.setPower(speed);
    }

    public void stopMotor(){
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

    }
    public void driveMotors(double speed, double turn, double strafe){
        leftFront.setPower(-1 * (speed - (turn + strafe)));
        leftBack.setPower(-1 * (speed - (turn - strafe)));
        rightFront.setPower(-1 * (speed + turn + strafe));
        rightBack.setPower(-1 * (speed + (turn - strafe)));

    }



}
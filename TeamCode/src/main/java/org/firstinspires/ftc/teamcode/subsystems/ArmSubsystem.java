package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ArmSubsystem {
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    public final CRServo rightServo;
    public final CRServo leftServo;
    public final DistanceSensor distanceSensor;
    Telemetry tele;
    Constants constants = new Constants();
    boolean collect = false;
    boolean expel = false;
    public enum ServoState{
        COLLECT,
        EXPEL,
        HOLD
    }

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");
        rightServo = hardwareMap.get(CRServo.class, "right");
        leftServo = hardwareMap.get(CRServo.class, "left");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distance");

        worm.setDirection(DcMotorEx.Direction.REVERSE);

        //worm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //actuator.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        worm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        actuator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

//        worm.setPositionPIDFCoefficients(4);
//        actuator.setPositionPIDFCoefficients(4);

        tele = telemetry;

        ServoState curState = ServoState.HOLD;

        switch (curState){
            case HOLD:
                rightServo.setPower(0);
                leftServo.setPower(0);
                break;

            case EXPEL:
                while (distanceSensor.getDistance(DistanceUnit.MM) < constants.sampleDistance+10){
                    rightServo.setPower(-1);
                    leftServo.setPower(-1);
                }
                curState = ServoState.HOLD;
                break;

            case COLLECT:
                while (distanceSensor.getDistance(DistanceUnit.MM) > constants.sampleDistance){
                    rightServo.setPower(1);
                    leftServo.setPower(1);
                }
                curState = ServoState.HOLD;
                break;
        }
    }


    public void Specimen() {
        while (actuator.getCurrentPosition() < constants.fullExt || actuator.getCurrentPosition() > constants.fullExt+100){
            while (worm.getCurrentPosition() < constants.specimenAng-2 || worm.getCurrentPosition() > constants.specimenAng+2){
                goToPos(worm, constants.specimenAng);
                goToPos(actuator, constants.fullExt);
            }
        }
    }

    public void Tuck() {
        while (actuator.getCurrentPosition() < constants.tucked || actuator.getCurrentPosition() > constants.tucked+100){
            while (worm.getCurrentPosition() < constants.tucked-2 || worm.getCurrentPosition() > constants.tucked+2){
                goToPos(worm, constants.tucked);
                goToPos(actuator, constants.tucked);
            }
        }
    }

    public void Collect() {    }

    public void goToPos(DcMotorEx motor, int pos) {
        double velo = Math.abs(pos - motor.getCurrentPosition());
        motor.setTargetPosition(pos);
        motor.setVelocity(velo * 4);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
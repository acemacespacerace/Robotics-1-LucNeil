package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem{
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    public final CRServo rightServo;
    public final CRServo leftServo;
    Telemetry tele;
    Constants constants = new Constants();
    ElapsedTime expelTimer = new ElapsedTime();
    public enum ServoState {
        COLLECT,
        EXPEL,
        HOLD
    }

    public enum ArmState {
        TUCKED,
        SCORE
    }

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");
        rightServo = hardwareMap.get(CRServo.class, "right");
        leftServo = hardwareMap.get(CRServo.class, "left");

        worm.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(CRServo.Direction.REVERSE);

        //worm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //actuator.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        worm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        actuator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

//        worm.setPositionPIDFCoefficients(4);
//        actuator.setPositionPIDFCoefficients(4);

        tele = telemetry;
    }

    public void SetArmState(ArmState curArmState){
        switch (curArmState) {
            case TUCKED:
                Tuck();
                break;

            case SCORE:
                Specimen();
                SetServoState(ServoState.EXPEL);
                break;
        }
    }

    public void SetServoState(ServoState curServState) {
        expelTimer.reset();
        switch (curServState) {
            case EXPEL:
                while (expelTimer.milliseconds() < 2000) {
                    rightServo.setPower(-1);
                    leftServo.setPower(-1);
                }
                curServState = ServoState.HOLD;
                break;

            case COLLECT:
                while (actuator.getCurrentPosition() < constants.fullExt-200) {
                    rightServo.setPower(1);
                    leftServo.setPower(1);
                    goToPos(actuator, constants.fullExt);
                }
                goToPos(actuator, constants.tucked);
                curServState = ServoState.HOLD;
                break;

            case HOLD:
                rightServo.setPower(0);
                leftServo.setPower(0);
                break;
        }
    }

    public void goToPos(DcMotorEx motor, int pos) {
//        double velo = Math.abs(pos - motor.getCurrentPosition());
        motor.setTargetPosition(pos);
        motor.setVelocity(3000);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void Specimen() {
        while (actuator.getCurrentPosition() < constants.fullExt || actuator.getCurrentPosition() > constants.fullExt + 100) {
            while (worm.getCurrentPosition() < constants.specimenAng - 2 || worm.getCurrentPosition() > constants.specimenAng + 2) {
                goToPos(worm, constants.specimenAng);
                goToPos(actuator, constants.fullExt);
            }
        }
    }

    private void Tuck() {
        while (actuator.getCurrentPosition() < constants.tucked || actuator.getCurrentPosition() > constants.tucked + 100) {
            while (worm.getCurrentPosition() < constants.tucked - 2 || worm.getCurrentPosition() > constants.tucked + 2) {
                goToPos(worm, constants.tucked);
                goToPos(actuator, constants.tucked);
            }
        }
    }
}
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
        SCORE,
        INIT,
        RESET
    }

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");
        rightServo = hardwareMap.get(CRServo.class, "right");
        leftServo = hardwareMap.get(CRServo.class, "left");

        worm.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(CRServo.Direction.REVERSE);

        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        worm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        actuator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        worm.setPositionPIDFCoefficients(4);
        actuator.setPositionPIDFCoefficients(7);

        tele = telemetry;
    }

    public void SetArmState(ArmState curArmState){
        switch (curArmState) {
            case INIT:
                Init();
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;

            case TUCKED:
                SetServoState(ServoState.HOLD);
                Tuck();
                break;

            case SCORE:
                SetServoState(ServoState.HOLD);
                Score();
                SetServoState(ServoState.EXPEL);
                SetServoState(ServoState.HOLD);
                break;

            case RESET:
                SetServoState(ServoState.HOLD);
                Reset();
                break;
        }
    }

    public void SetServoState(ServoState curServState) {
        expelTimer.reset();
        switch (curServState) {
            case EXPEL:
                while (expelTimer.milliseconds() < 1000) {
                    rightServo.setPower(-1);
                    leftServo.setPower(-1);
                }
                break;

            case COLLECT:
                Tuck();
                while (actuator.getCurrentPosition() < constants.fullExt) {
                    rightServo.setPower(1);
                    leftServo.setPower(1);
                    goToPos(actuator, constants.fullExt);
                }
                Tuck();
                break;

            case HOLD:
                rightServo.setPower(0);
                leftServo.setPower(0);
                break;
        }
    }

    public void goToPos(DcMotorEx motor, int pos) {
        while (motor.getCurrentPosition() < pos-10 || motor.getCurrentPosition() > pos+10) {
            motor.setTargetPosition(pos);
            motor.setVelocity(9999);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    private void Score() {
        ActTuck();
        goToPos(worm, constants.scoreAng);
        goToPos(actuator, constants.fullExt);
    }

    private void Tuck() {
        ActTuck();
        goToPos(worm, constants.tucked);
    }

    private void Init() {
        goToPos(worm, constants.initAng);
    }

    private void Reset() {
        ActTuck();
        goToPos(worm, constants.resetAng);
    }

    private void ActTuck() {
        goToPos(actuator, constants.tucked);
    }
}
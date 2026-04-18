package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem {
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    Telemetry tele;
    Constants constants = new Constants();

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");

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
    }


    public void Specimen() {
        while (actuator.getCurrentPosition() < constants.specimenExt || actuator.getCurrentPosition() > constants.specimenExt+100){
            while (worm.getCurrentPosition() < constants.specimenAng-2 || worm.getCurrentPosition() > constants.specimenAng+2){
                goToPos(worm, constants.specimenAng);
                goToPos(actuator, constants.specimenExt);
            }
        }
    }

    public void Tucked() {
        while (actuator.getCurrentPosition() < constants.tucked || actuator.getCurrentPosition() > constants.tucked+100){
            while (worm.getCurrentPosition() < constants.tucked-2 || worm.getCurrentPosition() > constants.tucked+2){
                goToPos(worm, constants.tucked);
                goToPos(actuator, constants.tucked);
            }
        }
    }



    public void goToPos(DcMotorEx motor, int pos) {
        double velo = Math.abs(pos - motor.getCurrentPosition());
        motor.setTargetPosition(pos);
        motor.setVelocity(velo * 4);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
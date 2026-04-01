package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem {
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    Telemetry tele;
    Constants constants;

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");

//        worm.setDirection(DcMotorEx.Direction.REVERSE);
        worm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        actuator.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        worm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        actuator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        tele = telemetry;
    }

    public void Submerse(){
        goToPos(worm, constants.submerseAng);
        goToPos(actuator, constants.submerseExt);
    }

    public void Basket(){
        goToPos(worm, constants.basketAng);
        goToPos(actuator, constants.basketExt);
    }

    public void Specimen(){
        goToPos(worm, constants.specimenAng);
        goToPos(actuator, constants.specimenExt);
    }

    public void goToPos(DcMotorEx motor, int pos) {
        motor.setTargetPosition(pos);
        motor.setVelocity(9999);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

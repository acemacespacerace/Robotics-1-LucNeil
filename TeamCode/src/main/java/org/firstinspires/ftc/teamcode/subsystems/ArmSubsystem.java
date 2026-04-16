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

        tele = telemetry;
    }

    //    public void Submerse(){
//        goToPos(worm, constants.submerseAng);
//        goToPos(actuator, constants.submerseExt);
//        tele.addLine("SubPos");
//    }
//
//    public void Basket(){
//        goToPos(worm, constants.basketAng);
//        goToPos(actuator, constants.basketExt);
//        tele.addLine("BaskPos");
//    }
//
    public void Specimen() {
        while (worm.getCurrentPosition() != constants.specimenAng && actuator.getCurrentPosition() != constants.submerseExt) {
            goToPos(worm, constants.specimenAng);
            goToPos(actuator, constants.submerseExt);
        }
    }

    public void Tucked() {
        while (worm.getCurrentPosition() != constants.tucked && actuator.getCurrentPosition() != constants.tucked) {
            goToPos(worm, constants.tucked);
            goToPos(actuator, constants.tucked);
        }
    }

    public void goToPos(DcMotorEx motor, int pos) {
        double velo = Math.abs(pos - motor.getCurrentPosition());
        motor.setTargetPosition(pos);
        motor.setVelocity(velo * 3);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
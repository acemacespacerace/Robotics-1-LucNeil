package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem {
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    Telemetry tele;

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        Constants constants = new Constants();

        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");

        worm.setDirection(DcMotorEx.Direction.REVERSE);
        worm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        actuator.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

//        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
    public void Specimen(){
        goToPos(worm, 2000);
        goToPos(actuator, -2000);
    }

    public void Tucked() {
        goToPos(worm, 0);
        goToPos(actuator, 0);
    }

    public void goToPos(DcMotorEx motor, int pos) {
        double error = (pos - motor.getCurrentPosition());
        motor.setTargetPosition(pos);
        motor.setVelocity(Math.abs(error)*2);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
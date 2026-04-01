package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem {
    public final DcMotorEx worm;
    public final DcMotorEx actuator;
    final Telemetry tele;
    Constants constants;

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        worm = hardwareMap.get(DcMotorEx.class, "worm");
        actuator = hardwareMap.get(DcMotorEx.class, "actuator");

//        worm.setDirection(DcMotorEx.Direction.REVERSE);
        worm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        actuator.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        worm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        actuator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        worm.setTargetPositionTolerance(100);
        actuator.setTargetPositionTolerance(50);

        tele = telemetry;
    }

    public void goToPos(DcMotorEx motor, int pos) {
        while (motor.getCurrentPosition() != pos){
            int error = (pos - motor.getCurrentPosition());
            motor.setTargetPosition(pos);
            motor.setVelocity(3000);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
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

//    public void Inspection(){
//        goToPos();
//    }
}

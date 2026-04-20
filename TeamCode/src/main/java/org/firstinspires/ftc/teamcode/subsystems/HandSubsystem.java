package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HandSubsystem {
    CRServo rightServo;
    CRServo leftServo;
    DistanceSensor distanceSensor;
    Telemetry tele;
    Constants constants;
    public HandSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        rightServo = hardwareMap.get(CRServo.class, "right");
        leftServo = hardwareMap.get(CRServo.class, "left");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distance");

        leftServo.setDirection(DcMotorEx.Direction.REVERSE);

        constants = new Constants();

        tele = telemetry;
    }

    public void Collect(){
        while (distanceSensor.getDistance(DistanceUnit.MM) > constants.sampleDistance){
            leftServo.setPower(1);
            rightServo.setPower(1);
        }
        leftServo.setPower(0);
        rightServo.setPower(0);
    }
}

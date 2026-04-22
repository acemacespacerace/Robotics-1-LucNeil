package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@Autonomous(name = "LucNeilAuto",group = "Deep")
//@Disabled
public class DeepAuto extends LinearOpMode {

    public enum ServoState{
        COLLECT,
        EXPEL,
        HOLD
    }

    @Override
    public void runOpMode() throws InterruptedException {

        DriveSubsystem drive = new DriveSubsystem(hardwareMap, telemetry);
        ArmSubsystem arm = new ArmSubsystem(hardwareMap, telemetry);
        Constants constants = new Constants();

        ServoState curState = ServoState.HOLD;

        switch (curState){
            case EXPEL:
                arm.servoTimer.reset();
                while (arm.servoTimer.seconds() < 3000){
                    arm.rightServo.setPower(-1);
                    arm.leftServo.setPower(-1);
                }
                curState = ServoState.HOLD;
                break;

            case COLLECT:
                arm.servoTimer.reset();
                while (arm.servoTimer.seconds() < 3000){
                    arm.rightServo.setPower(1);
                    arm.leftServo.setPower(1);
                    arm.goToPos(arm.actuator, constants.fullExt);
                }
                curState = ServoState.HOLD;
                break;

            case HOLD:
                arm.rightServo.setPower(0);
                arm.leftServo.setPower(0);
                break;
        }

        waitForStart();

        if (opModeIsActive()) {
            //arm.Tucked();
            arm.Specimen();
            //sleep(10000);
            arm.Tuck();
            curState = ServoState.COLLECT;
        }
    }
}
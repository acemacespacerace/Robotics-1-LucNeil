package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@Autonomous(name = "LucNeilAuto",group = "Deep")
//@Disabled
public class DeepAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DriveSubsystem drive = new DriveSubsystem(hardwareMap, telemetry);
        ArmSubsystem arm = new ArmSubsystem(hardwareMap, telemetry);

        waitForStart();

        arm.Tucked();

        while (opModeIsActive()) {
//            drive.Forward(1,0.2);
//            drive.Backward(1,0.2);
            arm.Specimen();
            sleep(5000);
            arm.Tucked();
            sleep(5000);

            if (isStopRequested()){
                arm.Tucked();
            }
        }
    }
}
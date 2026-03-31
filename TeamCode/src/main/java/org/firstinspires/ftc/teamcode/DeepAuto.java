package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@Autonomous(name = "Auto",group = "Deep")
public class DeepAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DriveSubsystem drive = new DriveSubsystem(hardwareMap, telemetry);

        waitForStart();

        if (opModeIsActive()) {
            drive.Forward(1,0.2);
            drive.Backward(1,0.2);
        }
    }
}
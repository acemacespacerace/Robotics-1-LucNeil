package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@TeleOp(name = "TeleOp", group = "LucasNeilDeepBot")
@Disabled
public class DeepTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DriveSubsystem drive = new DriveSubsystem(hardwareMap, telemetry);
        ArmSubsystem arm = new ArmSubsystem(hardwareMap, telemetry);

        telemetry.addLine("Ready");

        waitForStart();

        telemetry.addLine("Running");

        while (opModeIsActive()) {
            if (gamepad1.x){
                arm.worm.setPower(0.2);
            } else if (gamepad1.a) {
                arm.worm.setPower(-0.2);
            }

            telemetry.addData("Ang", arm.worm.getCurrentPosition());
            telemetry.update();
        }
    }
}

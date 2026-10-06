package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@Config
@TeleOp
public class IntakePDTest extends LinearOpMode {

    public static double target = 0;

    boolean stateA = false;
    boolean stateB = false;
    boolean motorState = false;
    boolean mState = false;

    @Override
    public void runOpMode() {
        Intake in = new Intake(this);

        Intake.extensionController.start();
        waitForStart();

        while (opModeIsActive()) {
            in.setTarget(target);

            if (gamepad1.bWasPressed()) {
                target += 10;
            }
            telemetry.addData("target = ", target);
            telemetry.update();

        }
        Intake.extensionController.interrupt();
    }
}

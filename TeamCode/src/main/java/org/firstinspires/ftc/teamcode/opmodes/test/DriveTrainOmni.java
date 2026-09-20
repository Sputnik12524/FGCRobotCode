package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.modules.DriveTrain;

@TeleOp(name = "DriveTrainOmni", group = "Test")
public class DriveTrainOmni extends LinearOpMode {

    private static final double DEADBAND = 0.05;

    @Override
    public void runOpMode() {
        DriveTrain dt = new DriveTrain(this);

        waitForStart();

        dt.resetYaw();

        boolean previousOptions = false;

        while (opModeIsActive()) {
            double forward = applyDeadband(-gamepad1.left_stick_y);
            double right = applyDeadband(gamepad1.left_stick_x);
            double turn = applyDeadband(gamepad1.right_stick_x);

            if (gamepad1.options && !previousOptions) {
                dt.resetYaw();
            }
            previousOptions = gamepad1.options;

            dt.driveFieldCentric(forward, right, turn);
        }

        dt.stop();
    }

    private double applyDeadband(double value) {
        return Math.abs(value) < DEADBAND ? 0.0 : value;
    }
}

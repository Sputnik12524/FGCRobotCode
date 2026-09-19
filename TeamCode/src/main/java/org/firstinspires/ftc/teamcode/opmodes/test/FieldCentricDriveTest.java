package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.modules.DriveTrain;

@TeleOp
public class FieldCentricDriveTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        DriveTrain dt = new DriveTrain(this);

        waitForStart();

        while(opModeIsActive()) {

            double botHeading = dt.getYaw(AngleUnit.RADIANS);

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rotation = gamepad1.right_trigger - gamepad1.left_trigger;

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotation), 1);

            dt.setPowerFieldCentric(rotY, rotX, rotation, denominator);

            telemetry.addData("Bot Heading", botHeading);
            telemetry.addData("rotX", rotX);
            telemetry.addData("rotY", rotY);
            telemetry.addData("denominator", denominator);

            telemetry.update();
        }
    }
}

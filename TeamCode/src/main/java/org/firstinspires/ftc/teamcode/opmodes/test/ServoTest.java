package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@TeleOp
public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode(){
        Intake in = new Intake(this);

        waitForStart();

        double diff = 0.01;

        while(opModeIsActive()) {
            double currentL = in.getLeftExtensionPose();
            double currentR = in.getRightExtensionPose();
            if (gamepad1.a) {
                in.setExtensionLeftPosition(currentL+diff);
            } else if (gamepad1.b) {
                in.setExtensionLeftPosition(currentL-diff);
            } else if (gamepad1.x) {
                in.setExtensionRightPosition(currentR+diff);
            } else if (gamepad1.y) {
                in.setExtensionRightPosition(currentR-diff);
            }
        }
    }
}

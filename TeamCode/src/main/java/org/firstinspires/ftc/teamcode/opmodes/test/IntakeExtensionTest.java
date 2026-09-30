package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.intellij.lang.annotations.JdkConstants;

@Config
@TeleOp
public class IntakeExtensionTest extends LinearOpMode {

    public static double DIFF = 0.01;

    public boolean intakeState = false;
    public boolean aState = false;

    public boolean bState = false;
    public boolean inState = false;
    @Override
    public void runOpMode() {
        Intake in = new Intake(this);


        in.setExtensionLeftPosition(0);
        in.setExtensionRightPosition(0);

        waitForStart();

        while(opModeIsActive()){

            if (gamepad1.a && !aState && !intakeState) {
                in.rotateIn(1);
                intakeState = true;
            } else if (gamepad1.a && !aState && intakeState){
                in.rotateOut(0);
                intakeState = false;
            }
            if (gamepad1.b && !bState && !inState) {
                in.rotateOut(1);
                inState = true;
            } else if (gamepad1.b && !bState && inState) {
                in.rotateStop();
                inState = false;
            }

            double currentL = in.getLeftExtensionPose();
            double currentR = in.getRightExtensionPose();
            if (gamepad1.dpadUpWasPressed()) {
                in.setExtensionRightPosition(currentR+DIFF);

                in.setExtensionLeftPosition(currentL+DIFF);
            } else if (gamepad1.dpadDownWasPressed()) {

                in.setExtensionLeftPosition(currentL-DIFF);
                in.setExtensionRightPosition(currentR-DIFF);
            } else if (gamepad1.dpadRightWasPressed()) {
            } else if (gamepad1.dpadLeftWasPressed()) {
            }

            aState = gamepad1.a;
            bState = gamepad1.b;

            telemetry.addData("Left Pose", in.getLeftExtensionPose());
            telemetry.addData("Right Pose", in.getRightExtensionPose());
            telemetry.update();
        }
    }
}

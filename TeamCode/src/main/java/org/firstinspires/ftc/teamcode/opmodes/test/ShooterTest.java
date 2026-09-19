package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;


@TeleOp
public class ShooterTest extends LinearOpMode {

    public boolean intakeState = false;
    public boolean aState = false;
    @Override
    public void runOpMode() {
        Intake in = new Intake(this);

        waitForStart();

        while(opModeIsActive()){

            if (gamepad1.a && !aState && !intakeState) {
                in.rotateIn(1);
                intakeState = true;
            } else if (gamepad1.a && !aState && intakeState){
                in.rotateOut(1);
                intakeState = false;
            } else {
                in.rotateStop();
            }
            aState = gamepad1.a;
        }
    }
}

package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Suspension;


@TeleOp
public class SusTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Suspension sus = new Suspension(this);

        waitForStart();

        while(opModeIsActive()){

            if (gamepad1.a) {
                sus.setSusPower(0.5);
            } else if (gamepad1.b){
                sus.setSusPower(-0.5);
            } else {
                sus.setSusPower(0);
            }
        }
    }
}

package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;


@TeleOp
public class DriveTrainTest extends LinearOpMode {

    public boolean multiplierState = false;
    public boolean aState = false;
    @Override
    public void runOpMode() {
        DriveTrain dt = new DriveTrain(this);

        waitForStart();

        while(opModeIsActive()){
            double main = -gamepad1.left_stick_y;
            double side = gamepad1.left_stick_x;
            double rotate = gamepad1.right_trigger - gamepad1.left_trigger;

            dt.setPowerOmniSimple(main, side, rotate);
            if (gamepad1.a && !aState && !multiplierState) {
                DriveTrain.multiplier = 0.5;
                multiplierState = true;
            } else if (gamepad1.a && !aState && multiplierState){
                DriveTrain.multiplier = 1;
                multiplierState = false;
            }
            aState = gamepad1.a;
        }
    }
}

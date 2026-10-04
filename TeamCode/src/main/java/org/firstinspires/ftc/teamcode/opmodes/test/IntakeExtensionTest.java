package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@Config
@TeleOp
public class IntakeExtensionTest extends LinearOpMode {

    public static double POWER = 1;

    boolean stateA = false;
    boolean stateB = false;
    boolean motorState = false;
    boolean mState = false;
    @Override
    public void runOpMode() {
        Intake in = new Intake(this);


        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a && !stateA && !motorState) {
                in.setExtensionLeftPower(POWER);
                in.setExtensionRightPower(POWER);

                motorState = true;
            }  else if (gamepad1.a && !stateA && motorState) {
                in.setExtensionLeftPower(0);
                in.setExtensionRightPower(0);

                motorState = false;
            }

            if(gamepad1.b && !stateB && !mState) {
                in.setExtensionLeftPower(-POWER);
                in.setExtensionRightPower(-POWER);

                mState = true;
            } else if (gamepad1.b && !stateB && mState) {
                in.setExtensionLeftPower(0);
                in.setExtensionRightPower(0);

                mState = false;
            }
            telemetry.addData("power = ", POWER);
            telemetry.update();

        }
    }
}

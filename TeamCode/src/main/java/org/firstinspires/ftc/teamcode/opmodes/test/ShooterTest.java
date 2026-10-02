package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Shooter;

@TeleOp
public class ShooterTest extends LinearOpMode {

    public static double POWER_TRANSFER = 0.5;
    public static double POWER_SHOOTER = 1;

    boolean stateA = false;
    boolean stateB = false;
    boolean stateX = false;
    boolean stateY = false;
    boolean motorState = false;
    boolean mState = false;
    boolean state = false;
    boolean trState = false;

    @Override
    public void runOpMode() {
        Shooter sh = new Shooter(this);

        waitForStart();

        while (opModeIsActive()){

            if(gamepad1.a && !stateA && !motorState) {
                sh.setMotorsPower(POWER_SHOOTER);
                motorState = true;
            } else if (gamepad1.a && !stateA && motorState) {
                sh.setMotorsPower(0);
                motorState = false;
            }

            if(gamepad1.b && !stateB && !mState) {
                sh.setMotorsPower(-POWER_SHOOTER);
                mState = true;
            } else if (gamepad1.b && !stateB && mState) {
                sh.setMotorsPower(0);
                mState = false;
            }

            if(gamepad1.x && !stateX && !state) {
                sh.setMotorsPower(POWER_TRANSFER);
                state = true;
            } else if (gamepad1.b && !stateB && state) {
                sh.setMotorsPower(0);
                state = false;
            }
            if(gamepad1.y && !stateY && !trState) {
                sh.setMotorsPower(-POWER_TRANSFER);
                trState = true;
            } else if (gamepad1.y && !stateY && trState) {

            }
            stateA = gamepad1.a;
            stateB = gamepad1.b;
            stateX = gamepad1.x;
            telemetry.addData("power = ", POWER_TRANSFER);
            telemetry.update();

        }
    }
}

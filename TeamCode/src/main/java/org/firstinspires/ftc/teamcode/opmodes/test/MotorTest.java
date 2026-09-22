package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MotorTest extends LinearOpMode {

    public static double POWER = 0.9;

    boolean stateA = false;
    boolean stateB = false;
    boolean motorState = false;
    boolean mState = false;
    @Override
    public void runOpMode() {
        DcMotor myMotor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()){

            if(gamepad1.a && !stateA && !motorState) {
                myMotor.setPower(POWER);
                motorState = true;
            } else if (gamepad1.a && !stateA && motorState) {
                myMotor.setPower(0);
                motorState = false;
            }

            if(gamepad1.b && !stateB && !mState) {
                myMotor.setPower(-POWER);
                mState = true;
            } else if (gamepad1.b && !stateB && mState) {
                myMotor.setPower(0);
                mState = false;
            }
            stateA = gamepad1.a;
            stateB = gamepad1.b;
            telemetry.addData("power = ", POWER);
            telemetry.update();

        }
    }
}

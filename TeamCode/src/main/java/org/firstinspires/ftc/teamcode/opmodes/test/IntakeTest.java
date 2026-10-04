package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;


@TeleOp
public class IntakeTest extends LinearOpMode {

    public static double POWER_TRANSFER = 0.5;
    public static double POWER_INTAKE = 1;

    boolean state = false;
    boolean trState = false;
    boolean intakeState = false;
    boolean inState = false;


    public boolean aState = false;
    boolean stateX = false;
    boolean stateY = false;
    boolean bState = false;
    @Override
    public void runOpMode() {
        Intake in = new Intake(this);

        waitForStart();

        while(opModeIsActive()){

            if (gamepad1.a && !aState && !intakeState) {
                in.rotateIn(POWER_INTAKE);
                intakeState = true;
            } else if (gamepad1.a && !aState && intakeState) {
                in.rotateStop();
                intakeState = false;
            }
            if (gamepad1.b && !bState && !inState) {
                in.rotateOut(POWER_INTAKE);
                inState = true;
            } else if (gamepad1.a && !aState && inState) {
                in.rotateStop();
                inState = false;
            }
            if(gamepad1.x && !stateX && !state) {
                in.setPowerTransfer(POWER_TRANSFER);
                state = true;
            } else if (gamepad1.x && !stateX && state) {
                in.setPowerTransfer(0);
                state = false;
            }
            if(gamepad1.y && !stateY && !trState) {
                in.setPowerTransfer(-POWER_TRANSFER);
                trState = true;
            } else if (gamepad1.y && !stateY && trState) {
                in.setPowerTransfer(0);
                trState = false;
            }
            aState = gamepad1.a;
        }
    }
}

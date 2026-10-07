package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Shooter;

@TeleOp(name = "TeleOpRR", group = "TeleOp")
@Config
public class TeleOpRR extends LinearOpMode {

    public static double POWER_TRANSFER = 0.5;
    public static double POWER_INTAKE = 1;
    public static double POWER_SHOOTER = 1;
    public static double POWER_EXTENSION = 1;

    boolean inState = false;
    boolean outState = false;
    boolean state = false;
    boolean trState = false;
    boolean transState = false;
    boolean shState = false;


    public boolean aState = false;
    boolean stateX = false;
    boolean stateY = false;
    boolean bState = false;
    boolean bumpLState = false;
    boolean bumpRState = false;
    boolean stateX2 = false;

    @Override
    public void runOpMode() {
        DriveTrain dt = new DriveTrain(this);
        Intake in = new Intake(this);
        Shooter sh = new Shooter(this);
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {
            double time1 = timer.milliseconds();
            double main = gamepad1.left_stick_y;
            double rotate = gamepad1.left_trigger - gamepad1.right_trigger;
            dt.setMotorsPowerNonLinear(main, rotate);

            if (gamepad1.cross && !aState && !inState) {
                in.rotateIn(POWER_INTAKE);
                inState = true;
            } else if (gamepad1.cross && !aState && inState) {
                in.rotateStop();
                inState = false;
            }

            if (gamepad1.left_bumper && !bumpLState && !trState) {
                in.setPowerTransfer(POWER_TRANSFER); // Into the shooter
                trState = true;
            } else if (gamepad1.left_bumper && !bumpLState && trState) {
                in.setPowerTransfer(0);
                trState = false;
            }
            if (gamepad1.right_bumper && !bumpRState && !transState) {
                in.setPowerTransfer(-POWER_TRANSFER); // Out of the shooter
                transState = true;
            } else if (gamepad1.right_bumper && !bumpRState && transState) {
                in.setPowerTransfer(0);
                transState = false;
            }


            if (gamepad1.circle && !bState && !outState) {
                in.rotateOut(POWER_INTAKE);
                outState = true;
            } else if (gamepad1.circle && !bState && outState) {
                in.rotateStop();
                outState = false;
            }

            if (gamepad1.dpad_up) {
                in.setExtensionPower(POWER_EXTENSION);
            } else {
                in.setExtensionPower(0);
            }
            if (gamepad1.dpad_down) {
                in.setExtensionPower(-POWER_EXTENSION);
            } else {
                in.setExtensionPower(0);
            }

            if (gamepad1.square && !stateX && !shState) {
                sh.setMotorsPower(POWER_SHOOTER);
                shState = true;
            } else if (gamepad1.square && !stateX && shState) {
                sh.setMotorsPower(0);
                shState = false;
            }

            double shRun = gamepad2.left_stick_y;
            if (shRun < 0) {
                sh.setMotorsPower(POWER_SHOOTER);
            } else {
                sh.setMotorsPower(0);
            }

            double transferRun = gamepad2.right_stick_y;
            if (transferRun > 0) {
                in.setPowerTransfer(POWER_TRANSFER); // Into the shooter
            } else if (transferRun < -0){
                in.setPowerTransfer(-POWER_TRANSFER); // Out of the shooter
            } else {
                in.setPowerTransfer(0);
            }


            if (gamepad2.dpad_up) {
                in.setExtensionLeftPower(POWER_EXTENSION);
            } else {
                in.setExtensionLeftPower(0);
            }
            if(gamepad2.dpad_down) {
                in.setExtensionLeftPower(-POWER_EXTENSION);
            } else {
                in.setExtensionLeftPower(0);
            }

            stateX = gamepad1.square;
            stateX2 = gamepad2.square;
            stateY = gamepad1.triangle;
            aState = gamepad1.cross;
            bState = gamepad1.circle;

            bumpRState = gamepad1.right_bumper;
            bumpLState = gamepad1.left_bumper;

            double time2 = timer.milliseconds() - time1;
            telemetry.addData("ping = ", time2);
            telemetry.update();
        }
    }

}

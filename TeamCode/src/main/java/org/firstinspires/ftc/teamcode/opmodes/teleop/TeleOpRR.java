package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    boolean intakeState = false;
    boolean inState = false;
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

    @Override
    public void runOpMode(){
        DriveTrain dt = new DriveTrain(this);
        Intake in = new Intake(this);
        Shooter sh = new Shooter(this);

        waitForStart();

        while(opModeIsActive()) {
            double main = -gamepad1.left_stick_y;
            double rotate = gamepad1.right_trigger - gamepad1.left_trigger;
            dt.setMotorsPowerNonLinear(main, rotate);

            if (gamepad1.a && !aState && !intakeState) {
                in.rotateIn(POWER_INTAKE);
                intakeState = true;
            } else if (gamepad1.a && !aState && intakeState) {
                in.rotateStop();
                intakeState = false;
            }

            if (gamepad1.left_bumper && !bumpLState && !trState) {
                in.setPowerTransfer(POWER_TRANSFER);
                trState = true;
            } else if (gamepad1.left_bumper && !bumpLState && trState){
                in.setPowerTransfer(0);
                trState = false;
            }
            if(gamepad1.right_bumper && !bumpRState && !transState) {
                in.setPowerTransfer(-POWER_TRANSFER);
                transState = true;
            } else if (gamepad1.right_bumper && !bumpRState && transState){
                in.setPowerTransfer(0);
                transState = false;
            }



            if (gamepad1.b && !bState && !inState) {
                in.rotateOut(POWER_INTAKE);
                inState = true;
            } else if (gamepad1.a && !aState && inState) {
                in.rotateStop();
                inState = false;
            }

            if(gamepad1.dpad_up) {
                in.setExtensionPower(POWER_EXTENSION);
            } else {
                in.setExtensionPower(0);
            }
            if(gamepad1.dpad_down) {
                in.setExtensionPower(-POWER_EXTENSION);
            } else {
                in.setExtensionPower(0);
            }

            if(gamepad1.x && !stateX && !shState) {
                sh.setMotorsPower(POWER_SHOOTER);
                shState = true;
            } else if (gamepad1.x && !stateX && shState) {
                sh.setMotorsPower(0);
                shState = false;
            }

            stateX = gamepad1.x;
            stateY = gamepad1.y;
            aState = gamepad1.a;
            bState = gamepad1.b;

            bumpRState = gamepad1.right_bumper;
            bumpLState = gamepad1.left_bumper;

//            if(gamepad1.a && !stateA && !shState) {
//                sh.setVelocityTarget(Shooter.VELOCITY_FOR_LONG_THROW);
//                shState = true;
//            } else if(gamepad1.a && !stateA && shState) {
//                sh.setVelocityTarget(0);
//                shState = false;
//            } else if(gamepad1.b && !stateB)
//            stateA = gamepad1.a;

        }


    }

}

package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Shooter;

@TeleOp(name = "TeleOpRR", group = "TeleOp")
public class TeleOpRR extends LinearOpMode {

    boolean stateA = false;
    boolean stateB = false;
    boolean shState = false;

    @Override
    public void runOpMode(){
        DriveTrain dt = new DriveTrain(this);
        Intake in = new Intake(this);
        Shooter sh = new Shooter(this);

        waitForStart();

        while(opModeIsActive()) {
            double main = -gamepad1.left_stick_y;
            double side = gamepad1.left_stick_x;
            double rotate = gamepad1.right_trigger - gamepad1.left_trigger;
            dt.setMotorsPowerNonLinear(main, side, rotate);

            if(gamepad1.a && !stateA && !shState) {
                sh.setVelocityTarget(Shooter.VELOCITY_FOR_LONG_THROW);
                shState = true;
            } else if(gamepad1.a && !stateA && shState) {
                sh.setVelocityTarget(0);
                shState = false;
            } else if(gamepad1.b && !stateB)
            stateA = gamepad1.a;

        }


    }

}

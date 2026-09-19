package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Shooter;

public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode(){
        DriveTrain dt = new DriveTrain(this);
        Intake in = new Intake(this);
        Shooter sh = new Shooter(this);

        waitForStart();


    }

}

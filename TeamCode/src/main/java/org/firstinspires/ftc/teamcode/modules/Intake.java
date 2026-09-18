package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    public DcMotor intake;
    LinearOpMode linearOpMode;

    public Intake(LinearOpMode aggregate) {
        linearOpMode = aggregate;
        intake = linearOpMode.hardwareMap.get(DcMotor.class, "intake");

    }

    public void rotateIn(double power){
        intake.setPower(power);
    }
    public void rotateOut(double power){
        intake.setPower(power);
    }
}

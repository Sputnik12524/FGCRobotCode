package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Suspension {

    public DcMotor sus;
    LinearOpMode opMode;
    public Suspension (LinearOpMode opMode){
        this.opMode = opMode;

        sus = opMode.hardwareMap.get(DcMotor.class, "sus");

    }

    public void setSusPower(double susPower) {
        sus.setPower(susPower);
    }

}

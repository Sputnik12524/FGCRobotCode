package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Shooter {

    public DcMotor shooter;
    LinearOpMode linearOpMode;

    public Shooter(LinearOpMode aggregate) {
        linearOpMode = aggregate;
    }

}

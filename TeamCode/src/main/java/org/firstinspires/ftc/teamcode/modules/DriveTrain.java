package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveTrain {

    private DcMotor leftFront, leftBack, rightFront, rightBack;
    LinearOpMode linearOpMode;

    public DriveTrain(LinearOpMode aggregate) {
        linearOpMode = aggregate;
        leftFront = linearOpMode.hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = linearOpMode.hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = linearOpMode.hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = linearOpMode.hardwareMap.get(DcMotor.class, "rightBack");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPowerMecanum(double main, double side, double rotation) {
        leftFront.setPower(main+side+rotation);
        rightFront.setPower(main - side - rotation);
        leftBack.setPower(main - side + rotation);
        rightBack.setPower(main + side - rotation);
    }
}

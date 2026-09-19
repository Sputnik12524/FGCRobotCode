package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
public class DriveTrain {

    private DcMotor leftFront, leftBack, rightFront, rightBack;
    LinearOpMode linearOpMode;

    public static double multiplier;

    public static double p = 3;

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

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setSimplePower(double main, double rotate) {
        leftFront.setPower(main + rotate);
        leftBack.setPower(main + rotate);
        rightFront.setPower(main - rotate);
        rightBack.setPower(main - rotate);
    }

    public void setPowerOmniSimple(double main, double side, double rotation) {
        leftFront.setPower(multiplier * (main+side+rotation));
        rightFront.setPower(multiplier * (main - side - rotation));
        leftBack.setPower(multiplier * (main - side + rotation));
        rightBack.setPower(multiplier * (main + side - rotation));
    }

    public void setMotorsPowerNonLinear(double main, double side, double rotation) {
        double main_power = main + (1 - main) * main * Math.abs(Math.pow(main, p - 1));
        double side_power = side + (1 - side) * side * Math.abs(Math.pow(side, p - 1));
        double rotate_power = rotation + (1 - rotation) * rotation * Math.abs(Math.pow(rotation, p - 1));

        leftFront.setPower(multiplier * (main_power + side_power + rotate_power));
        leftBack.setPower(multiplier * (main_power - side_power + rotate_power));
        rightFront.setPower(multiplier * (main_power - side_power - rotate_power));
        rightBack.setPower(multiplier * (main_power + side_power - rotate_power));
    }

}
